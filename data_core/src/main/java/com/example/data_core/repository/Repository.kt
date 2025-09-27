package com.example.data_core.repository

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
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

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun registerWithEmailAndPasswordWorker(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        user: User,
        onResult: (Boolean) -> Unit
    ) {
        // Guarda el usuario localmente antes de lanzar el Worker
        userDao.insertUser(user)

        // El resultado real del registro llega en onResult
        firebaseService.registerWithEmailAndPassword(
            context = context,
            lifecycleOwner = lifecycleOwner,
            user = user,
            onResult = onResult
        )
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun registerWithGoogleAuthenticationWorker(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        idToken: String,
        onResult: (Boolean) -> Unit
    ) {
        firebaseService.registerWithGoogleAuthentication(
            context = context,
            lifecycleOwner = lifecycleOwner,
            idToken = idToken,
            onResult = onResult
        )
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun loginWithEmailAndPasswordWorker(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        user: User,
        onResult: (Boolean) -> Unit
    ) {
        firebaseService.loginWithEmailAndPassword(
            context = context,
            lifecycleOwner = lifecycleOwner,
            user = user,
            onResult = onResult
        )
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun loginWithGoogleAuthenticationWorker(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        idToken: String,
        onResult: (Boolean) -> Unit
    ) {
        firebaseService.loginWithGoogleAuthentication(
            context = context,
            lifecycleOwner = lifecycleOwner,
            idToken = idToken,
            onResult = onResult
        )
    }

    suspend fun getCurrentUser(): User? {
        return try {
            firebaseService.getCurrentUser()
        } catch (_: Exception) {
            null
        }
    }

    suspend fun changeName(user: User, newName: String) {
        try {
            firebaseService.changeName(user, newName)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun changePassword(user: User, newPassword: String) {
        try {
            firebaseService.changePassword(user, newPassword)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun logout() {
        firebaseService.logout()
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