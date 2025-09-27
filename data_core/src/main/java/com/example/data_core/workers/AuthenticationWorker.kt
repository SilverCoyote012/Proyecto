package com.example.data_core.workers

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.example.data_core.database.User
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ActionWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val ACTION_TYPE = "action_type"
        const val USER_EMAIL = "user_email"
        const val USER_PASSWORD = "user_password"
        const val USER_NAME = "user_name"
        const val ID_TOKEN = "id_token"

        const val REGISTER_EMAIL = "register_email"
        const val REGISTER_GOOGLE = "register_google"
        const val LOGIN_EMAIL = "login_email"
        const val LOGIN_GOOGLE = "login_google"
    }

    override suspend fun doWork(): Result {
        val actionType = inputData.getString(ACTION_TYPE) ?: return Result.failure()

        return try {
            when (actionType) {
                REGISTER_EMAIL -> {
                    val email = inputData.getString(USER_EMAIL) ?: return Result.failure()
                    val password = inputData.getString(USER_PASSWORD) ?: return Result.failure()
                    val name = inputData.getString(USER_NAME) ?: ""
                    val user = User("", name, email, password, "email")
                    val authResult = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                    val uid = authResult.user?.uid ?: ""
                    val userWithUid = user.copy(id = uid)
                    Firebase.firestore.collection("user").document(uid).set(userWithUid.toMap()).await()
                }

                REGISTER_GOOGLE -> {
                    val idToken = inputData.getString(ID_TOKEN) ?: return Result.failure()
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    val uid = authResult.user?.uid ?: ""
                    val user = User(
                        id = uid,
                        name = authResult.user?.displayName ?: "",
                        email = authResult.user?.email ?: "",
                        password = "",
                        authType = "google"
                    )
                    Firebase.firestore.collection("user").document(uid).set(user.toMap()).await()
                }

                LOGIN_EMAIL -> {
                    val email = inputData.getString(USER_EMAIL) ?: return Result.failure()
                    val password = inputData.getString(USER_PASSWORD) ?: return Result.failure()
                    Firebase.auth.signOut()
                    Firebase.auth.signInWithEmailAndPassword(email, password).await()
                }

                LOGIN_GOOGLE -> {
                    val idToken = inputData.getString(ID_TOKEN) ?: return Result.failure()
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    Firebase.auth.signInWithCredential(credential).await()
                }
            }

            Result.success(workDataOf("status" to "success"))

        } catch (e: Exception) {
            Result.failure(workDataOf("status" to "error", "message" to e.message))
        }
    }
}

class ActionManager(private val context: Context) {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun executeAction(
        lifecycleOwner: LifecycleOwner,
        actionType: String,
        userEmail: String? = null,
        userPassword: String? = null,
        userName: String? = null,
        idToken: String? = null,
        onResult: (Boolean) -> Unit
    ) {
        if (!isInternetAvailable()) {
            Toast.makeText(context, "No hay conexión a internet", Toast.LENGTH_SHORT).show()
            onResult(false)
            return
        }

        val data = workDataOf(
            ActionWorker.ACTION_TYPE to actionType,
            ActionWorker.USER_EMAIL to userEmail,
            ActionWorker.USER_PASSWORD to userPassword,
            ActionWorker.USER_NAME to userName,
            ActionWorker.ID_TOKEN to idToken
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<ActionWorker>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(request)

        workManager.getWorkInfoByIdLiveData(request.id)
            .observe(lifecycleOwner) { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    val success = workInfo.state == WorkInfo.State.SUCCEEDED
                    onResult(success)
                }
            }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
