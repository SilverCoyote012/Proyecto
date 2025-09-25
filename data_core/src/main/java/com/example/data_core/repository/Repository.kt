package com.example.data_core.repository

import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.EmprendimeintosDao
import com.example.data_core.database.Historial
import com.example.data_core.database.HistorialDao
import com.example.data_core.database.Producto
import com.example.data_core.database.ProductoDao
import com.example.data_core.firebase.FirebaseServiceEmprendimiento
import com.example.data_core.database.User
import com.example.data_core.database.UserDao
import com.example.data_core.firebase.FirebaseServiceHistorial
import com.example.data_core.firebase.FirebaseServiceProducto
import com.example.data_core.firebase.FirebaseServiceUser
import kotlinx.coroutines.flow.Flow

class UserRepository (
    private val userDao: UserDao,
    private val firebaseService: FirebaseServiceUser
) {
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insertUser(user)
        try {
            firebaseService.createUser(user)
        } catch (_: Exception) {

        }
    }

    suspend fun update(user: User) {
        userDao.updateUser(user)
        try {
            firebaseService.updateUser(user)
        } catch (_: Exception) {

        }
    }

    suspend fun delete(user: User) {
        userDao.deleteUser(user)
        try {
            firebaseService.deleteUser(user.id)
        } catch (_: Exception) {

        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return try {
            firebaseService.getAllUsers().find { it.email == email }
        } catch (_: Exception) {
            null
        }
    }
}

class EmprendimientoRepository (
    private val emprendimientoDao: EmprendimeintosDao,
    private val firebaseService: FirebaseServiceEmprendimiento
) {
    fun getAllEmprendimientos(): Flow<List<Emprendimiento>> = emprendimientoDao.getAllEmprendimientos()

    suspend fun insert(emprendimiento: Emprendimiento) {
        emprendimientoDao.insertEmprendimiento(emprendimiento)
        try {
            firebaseService.createEmprendimiento(emprendimiento)
        } catch (_: Exception) {

        }
    }

    suspend fun update(emprendimiento: Emprendimiento) {
        emprendimientoDao.updateEmprendimiento(emprendimiento)
        try {
            firebaseService.updateEmprendimiento(emprendimiento)
        } catch (_: Exception) {

        }
    }

    suspend fun delete(emprendimiento: Emprendimiento) {
        emprendimientoDao.deleteEmprendimiento(emprendimiento)
        try {
            firebaseService.deleteEmprendimiento(emprendimiento.id)
        } catch (_: Exception) {

        }
    }
}

class ProductoRepository (
    private val productoDao: ProductoDao,
    private val firebaseService: FirebaseServiceProducto
) {
    fun getAllProductos(): Flow<List<Producto>> = productoDao.getAllProductos()

    suspend fun insert(producto: Producto) {
        productoDao.insertProducto(producto)
        try {
            firebaseService.createProducto(producto)
        } catch (_: Exception) {

        }
    }

        suspend fun update(producto: Producto) {
        productoDao.updateProducto(producto)
        try {
            firebaseService.updateProducto(producto)
        } catch (_: Exception) {

        }
    }

    suspend fun delete(producto: Producto) {
        productoDao.deleteProducto(producto)
        try {
            firebaseService.deleteProducto(producto.id)
        } catch (_: Exception) {

        }
    }
}

class HistorialRepository (
    private val historialDao: HistorialDao,
    private val firebaseService: FirebaseServiceHistorial
) {
    fun getAllHistoriales(): Flow<List<Historial>> = historialDao.getAllHistoriales()

    suspend fun insert(historial: Historial) {
        historialDao.insertHistorial(historial)
        try {
            firebaseService.createHistorial(historial)
        } catch (_: Exception) {

        }
    }

    suspend fun update(historial: Historial) {
        historialDao.updateHistorial(historial)
        try {
            firebaseService.updateHistorial(historial)
        } catch (_: Exception) {

        }
    }

    suspend fun delete(historial: Historial) {
        historialDao.deleteHistorial(historial)
        try {
            firebaseService.deleteHistorial(historial.id)
        } catch (_: Exception) {

        }
    }
}