package com.example.data_core.firebase

import com.google.firebase.firestore.FirebaseFirestore

class Firebase(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    private val collection = firestore.collection("users")

    /*
     suspend fun uploadCita(cita: Cita){
     collection.document(cita.id).set(cita.toMap()).await
     }
     */
}