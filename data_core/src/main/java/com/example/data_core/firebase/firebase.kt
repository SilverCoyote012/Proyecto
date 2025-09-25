package com.example.data_core.firebase

import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.Historial
import com.example.data_core.database.Producto
import com.example.data_core.database.User
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

    suspend fun createUser(user: User){
        collection.document(user.id).set(user.toMap()).await()
    }

    suspend fun updateUser(user: User){
        collection.document(user.id).set(user.toMap()).await()
    }

    suspend fun deleteUser(id: String){
        collection.document(id).delete().await()
    }

//    val auth = Firebase.auth
//
//    suspend fun createAccount(email: String, password: String) {
//        // [START create_user_with_email]
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    updateUI(null)
//                }
//            }
//        // [END create_user_with_email]
//    }
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