package com.example.data_core.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val password: String,
    val authType: String
){
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "name" to name,
        "email" to email,
        "password" to password,
        "authType" to authType
    )

    companion object {
        fun fromMap(data: Map<String, Any>): User = User(
            id = data["id"] as String,
            name = data["name"] as String,
            email = data["email"] as String,
            password = data["password"] as String,
            authType = data["authType"] as String
        )
    }
}

@Entity
data class Emprendimiento(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val idUsuario: String,
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
        fun fromMap(data: Map<String, Any>): Emprendimiento = Emprendimiento(
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

@Entity
data class Producto(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val idEmprendimiento: String,
    val imagen: String,
    val nombreProducto: String,
    val descripcion: String,
    val precio: String
){
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "idEmprendimiento" to idEmprendimiento,
        "imagen" to imagen,
        "nombreProducto" to nombreProducto,
        "descripcion" to descripcion,
        "precio" to precio
    )

    companion object{
        fun fromMap(data: Map<String, Any>): Producto = Producto(
            id = data["id"] as String,
            idEmprendimiento = data["idEmprendimiento"] as String,
            imagen = data["imagen"] as String,
            nombreProducto = data["nombreProducto"] as String,
            descripcion = data["descripcion"] as String,
            precio = data["precio"] as String
        )
    }
}

@Entity
data class Historial(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val idUsuario: String,
    val accion: String,
    val fecha: String
){
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "idUsuario" to id,
        "accion" to accion,
        "fecha" to fecha
    )

    companion object {
        fun fromMap(data: Map<String, Any>): Historial = Historial(
            id = data["id"] as String,
            idUsuario = data["idUsuario"] as String,
            accion = data["accion"] as String,
            fecha = data["fecha"] as String
        )
    }
}