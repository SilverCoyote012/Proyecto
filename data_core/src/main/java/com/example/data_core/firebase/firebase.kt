package com.example.data_core.firebase

import com.example.data_core.database.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseService(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    private val collection = firestore.collection("user")


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