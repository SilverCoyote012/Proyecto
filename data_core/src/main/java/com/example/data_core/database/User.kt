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

@Entity
data class Emprendimientos(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val idUsuario: String = UUID.randomUUID().toString(),
    val imagen: String,
    val nombreEmprendimiento: String,
    val telefono: String,
    val descripcion: String,
    val categoria: String,
){
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "idUsuario" to idUsuario,
        "imagen" to imagen,
        "nombreEmprendimiento" to nombreEmprendimiento,
        "telefono" to telefono,
        "descripcion" to descripcion,
        "categoria" to categoria
    )

    companion object{
        fun fromMap(data: Map<String, Any>): Emprendimientos = Emprendimientos(
            id = data["id"] as String,
            idUsuario = data["idUsuario"] as String,
            imagen = data["imagen"] as String,
            nombreEmprendimiento = data["nombreEmprendimiento"] as String,
            telefono = data["telefono"] as String,
            descripcion = data["descripcion"] as String,
            categoria = data["categoria"] as String
        )
    }
}