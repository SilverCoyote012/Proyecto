package com.example.data_core.firebase

import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.Historial
import com.example.data_core.database.Producto
import com.example.data_core.database.User
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
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

    suspend fun registerWithEmailAndPassword(user: User): Boolean {
        return try {
            val authResul = Firebase.auth.createUserWithEmailAndPassword(user.email, user.password).await()

            val uid = authResul.user?.uid ?: ""
            val user = User(
                id = uid,
                name = user.name,
                email = user.email,
                password = user.password,
                authType = "email"
            )

            collection.document(uid).set(user.toMap()).await()
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun registerWithGoogleAuthentication(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResul = Firebase.auth.signInWithCredential(credential).await()

            val uid = authResul.user?.uid ?: ""
            val user = User(
                id = uid,
                name = authResul.user?.displayName ?: "",
                email = authResul.user?.email ?: "",
                password = "",
                authType = "google"
            )

            collection.document(uid).set(user.toMap()).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginWithEmailAndPassword(user: User): Boolean {
        return try {
            Firebase.auth.signOut()
            Firebase.auth.signInWithEmailAndPassword(user.email, user.password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun loginWithGoogleAuthentication(idToken: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.signInWithCredential(credential).await()

            val uid = Firebase.auth.currentUser?.uid ?: ""
            val user = User(
                id = uid,
                name = Firebase.auth.currentUser?.displayName ?: "",
                email = Firebase.auth.currentUser?.email ?: "",
                password = "",
                authType = "google"
            )

            val existingUser = collection.document(uid).get().await().toObject(User::class.java)
            if (existingUser != null) {
                collection.document(uid).set(user.toMap()).await()
            } else {
                collection.document(uid).set(user.toMap()).await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCurrentUser(): User? {
        val firebaseUser = Firebase.auth.currentUser
        val providers = firebaseUser?.providerData?.map { it.providerId }
        val isGoogle = providers?.contains("google.com") == true
        val isEmailPassword = providers?.contains("password") == true

        return if (firebaseUser != null) {
            User(
                id = firebaseUser.uid,
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: "",
                password = "",
                authType = when {
                    isGoogle -> "google"
                    isEmailPassword -> "email"
                    else -> "unknown"
                }
            )
        } else {
            null
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