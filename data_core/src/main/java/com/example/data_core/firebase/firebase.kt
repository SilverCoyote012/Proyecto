package com.example.data_core.firebase

import android.Manifest
import android.content.Context
import android.util.Log
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


    suspend fun getUserByUid(uid: String): User? {
        //Log.d("DEBUG", "getUserByUid1: $uid")
        val querySnapshot = collection.whereEqualTo("id", uid).get().await()
        //Log.d("DEBUG", "getUserByUid2: ${querySnapshot.documents}")

        if (!querySnapshot.isEmpty) {
            val doc = querySnapshot.documents.first()
            //Log.d("DEBUG", "getUserByUid3: $doc")
            val user = User(
                id = doc.getString("id") ?: "",
                name = doc.getString("name") ?: "",
                email = doc.getString("email") ?: "",
                password = doc.getString("password") ?: "",
                authType = doc.getString("authType") ?: ""
            )
            //Log.d("DEBUG", "getUserByUid4: $user")
            return user
        }
        return null
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun registerWithEmailAndPassword(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        user: User,
        onResult: (Boolean) -> Unit
    ): Boolean {
        return try {
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
        //Log.d("DEBUG", "${firebaseUser?.uid}")
        val user = getUserByUid(firebaseUser?.uid ?: "")
        //Log.d("DEBUG", "$user")
        return user
    }

    suspend fun changeName(user: User, newName: String) {
        Log.d("DEBUG", "Changing name for user: $user")
        val currentUser = Firebase.auth.currentUser
        Log.d("DEBUG", "User: ${currentUser?.displayName}")
        if (currentUser != null) {
            Log.d("DEBUG", "Changing name for user: $user")
            collection.document(currentUser.uid).update("name", newName).await()
            Log.d("DEBUG", "Name changed successfully")
        }


    }

    suspend fun changePassword(user: User, newPassword: String) {
        Log.d("DEBUG", "Changing password for user: $user")
        val currentUser = Firebase.auth.currentUser
        Log.d("DEBUG", "User: ${currentUser?.displayName}")
        if (currentUser != null) {
            currentUser.updatePassword(newPassword)
            Log.d("DEBUG", "Password changed successfully in Firebase Auth")
            Log.d("DEBUG", "${currentUser.uid}, ${currentUser.displayName}, ${currentUser.email}, ${newPassword}")

            val updatedUser = User(
                id = currentUser.uid,
                name = user.name,
                email = user.email,
                password = newPassword,
                authType = "email"
            )

            collection.document(currentUser.uid).set(updatedUser.toMap()).await()
            Log.d("DEBUG", "Password changed successfully")
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
            val snapshot = collection.get().await()
            snapshot.documents.mapNotNull { doc ->
                val id = doc.getString("id") ?: return@mapNotNull null
                val idUsuario = doc.getString("idUsuario") ?: return@mapNotNull null
                val imagen = doc.getString("imagen") ?: return@mapNotNull null
                val nombre = doc.getString("nombreEmprendimiento") ?: return@mapNotNull null
                val telefono = doc.getString("telefono") ?: return@mapNotNull null
                val descripcion = doc.getString("descripcion") ?: return@mapNotNull null
                val categoria = doc.getString("categoria") ?: return@mapNotNull null


                Emprendimiento(
                    id = id,
                    idUsuario = idUsuario,
                    imagen = imagen,
                    nombreEmprendimiento = nombre,
                    telefono = telefono,
                    descripcion = descripcion,
                    categoria = categoria
                )
            }
        } catch (e: Exception) {
            Log.e("Firebase", "Error fetching emprendimientos", e)
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

    suspend fun getEmprendimientoById(id: String): Emprendimiento? {
        val snapshot = collection.document(id).get().await()
        // mapear la respuesta a un objeto Emprendimiento manualmente
        val id = snapshot.getString("id") ?: return null
        val idUsuario = snapshot.getString("idUsuario") ?: return null
        val imagen = snapshot.getString("imagen") ?: return null
        val nombre = snapshot.getString("nombreEmprendimiento") ?: return null
        val telefono = snapshot.getString("telefono") ?: return null
        val descripcion = snapshot.getString("descripcion") ?: return null
        val categoria = snapshot.getString("categoria") ?: return null

        Log.d("DEBUG", "getEmprendimientoById: $id")

        return Emprendimiento(
            id = id,
            idUsuario = idUsuario,
            imagen = imagen,
            nombreEmprendimiento = nombre,
            telefono = telefono,
            descripcion = descripcion,
            categoria = categoria
        )
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