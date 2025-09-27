package com.example.data_core.firebase

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.Historial
import com.example.data_core.database.Producto
import com.example.data_core.database.User
import com.example.data_core.workers.ActionManager
import com.example.data_core.workers.ActionWorker
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseServiceUser(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    private val collection = firestore.collection("user")

    suspend fun getAllUsers(): List<User> {
        return try {
            collection.get().await().documents.mapNotNull {
                it.toObject(User::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }



    suspend fun updateUser(user: User){
        collection.document(user.id).set(user.toMap()).await()
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun registerWithEmailAndPassword(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        user: User,
        onResult: (Boolean) -> Unit
    ): Boolean {
        return try {
            Firebase.auth.signOut()

            val actionManager = ActionManager(context)
            actionManager.executeAction(
                lifecycleOwner = lifecycleOwner,
                actionType = ActionWorker.REGISTER_EMAIL,
                userEmail = user.email,
                userPassword = user.password,
                userName  = user.name,
                onResult  = onResult
            )
            true
        } catch (e: Exception) {
            onResult(false)
            false
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun registerWithGoogleAuthentication(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        idToken: String,
        onResult: (Boolean) -> Unit
    ): Boolean {
        return try {

            Firebase.auth.signOut()

            val actionManager = ActionManager(context)
            actionManager.executeAction(
                lifecycleOwner = lifecycleOwner,
                actionType = ActionWorker.REGISTER_GOOGLE,
                idToken    = idToken,
                onResult   = onResult
            )
            true
        } catch (e: Exception) {
            onResult(false)
            false
        }
    }


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun loginWithEmailAndPassword(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        user: User,
        onResult: (Boolean) -> Unit
    ): Boolean {
        return try {
            Firebase.auth.signOut()
            val actionManager = ActionManager(context)
            actionManager.executeAction(
                lifecycleOwner = lifecycleOwner,
                actionType = ActionWorker.LOGIN_EMAIL,
                userEmail = user.email,
                userPassword = user.password,
                onResult = onResult
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun loginWithGoogleAuthentication(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        idToken: String,
        onResult: (Boolean) -> Unit
    ): Boolean {
        return try {
            Firebase.auth.signOut()

            val actionManager = ActionManager(context)
            actionManager.executeAction(
                lifecycleOwner = lifecycleOwner,
                actionType = ActionWorker.LOGIN_GOOGLE,
                idToken = idToken,
                onResult = onResult
            )
            true
        } catch (e: Exception) {
            onResult(false)
            false
        }
    }

    suspend fun getCurrentUser(): User? {
        val firebaseUser = Firebase.auth.currentUser
        val providers = firebaseUser?.providerData?.map { it.providerId }
        val isGoogle = providers?.contains("google.com") == true
        val isEmailPassword = providers?.contains("password") == true

        return if (isGoogle || isEmailPassword) {
            val uid = firebaseUser?.uid ?: ""
            val user = collection.document(uid).get().await().toObject(User::class.java)
            user
        } else {
            null
        }
    }

    suspend fun changePassword(user: User, newPassword: String) {
        val userRef = collection.document(user.id)
        try {
            userRef.update("password", newPassword).await()

            val authUser = Firebase.auth.currentUser
            authUser?.updatePassword(newPassword)?.await()

            user.password = newPassword
            userRef.set(user.toMap()).await()
        } catch (e: Exception) {
            throw e
    }
}

    suspend fun logout() {
        Firebase.auth.signOut()
    }
}

class FirebaseServiceEmprendimiento(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    private val collection = firestore.collection("emprendimiento")

    suspend fun getAllEmprendimientos(): List<Emprendimiento> {
        return try {
            collection.get().await().documents.mapNotNull {
                it.toObject(Emprendimiento::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun createEmprendimiento(emprendimiento: Emprendimiento){
        collection.document(emprendimiento.id).set(emprendimiento.toMap()).await()
    }

    suspend fun updateEmprendimiento(emprendimiento: Emprendimiento){
        collection.document(emprendimiento.id).set(emprendimiento.toMap()).await()
    }

    suspend fun deleteEmprendimiento(id: String){
        collection.document(id).delete().await()
    }
}

class FirebaseServiceProducto(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    private val collection = firestore.collection("producto")

    suspend fun getAllProductos(): List<Producto> {
        return try {
            collection.get().await().documents.mapNotNull {
                it.toObject(Producto::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun createProducto(producto: Producto){
        collection.document(producto.id).set(producto.toMap()).await()
    }

    suspend fun updateProducto(producto: Producto){
        collection.document(producto.id).set(producto.toMap()).await()
    }

    suspend fun deleteProducto(id: String){
        collection.document(id).delete().await()
    }
}

class FirebaseServiceHistorial(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    private val collection = firestore.collection("historial")

    suspend fun getAllHistorial(): List<Historial> {
        return try {
            collection.get().await().documents.mapNotNull {
                it.toObject(Historial::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun createHistorial(historial: Historial) {
        collection.document(historial.id).set(historial.toMap()).await()
    }

    suspend fun updateHistorial(historial: Historial){
        collection.document(historial.id).set(historial.toMap()).await()
    }

    suspend fun deleteHistorial(id: String){
        collection.document(id).delete().await()
    }
}