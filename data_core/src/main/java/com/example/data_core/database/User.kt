package com.example.data_core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class User(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val password: String
){
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "name" to name,
        "email" to email,
        "password" to password
    )

    companion object {
        fun fromMap(data: Map<String, Any>): User = User(
            id = data["id"] as String,
            name = data["name"] as String,
            email = data["email"] as String,
            password = data["password"] as String
        )
    }
}