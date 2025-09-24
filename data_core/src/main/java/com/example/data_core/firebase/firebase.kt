package com.example.data_core.firebase

import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.User
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