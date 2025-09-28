package com.example.data_core.model

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.data_core.database.User
import com.example.data_core.repository.UserRepository
import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.Historial
import com.example.data_core.database.Producto
import com.example.data_core.repository.EmprendimientoRepository
import com.example.data_core.repository.HistorialRepository
import com.example.data_core.repository.ProductoRepository

class UserModel(private val repository: UserRepository) : ViewModel() {
    private val _user = MutableStateFlow<List<User>>(emptyList())

    val user: StateFlow<List<User>> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllUsers().collectLatest { usersFromDb ->
                _user.value = usersFromDb
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun registerWithEmailAndPassword(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        user: User,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            repository.registerWithEmailAndPasswordWorker(
                context = context,
                lifecycleOwner = lifecycleOwner,
                user = user,
                onResult = onResult
            )
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun registerWithGoogleAuthentication(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        idToken: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            repository.registerWithGoogleAuthenticationWorker(
                context = context,
                lifecycleOwner = lifecycleOwner,
                idToken = idToken,
                onResult = onResult
            )
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun loginWithEmailAndPassword(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        user: User,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            repository.loginWithEmailAndPasswordWorker(
                context = context,
                lifecycleOwner = lifecycleOwner,
                user = user,
                onResult = onResult
            )
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun loginWithGoogleAuthentication(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        idToken: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            repository.loginWithGoogleAuthenticationWorker(
                context = context,
                lifecycleOwner = lifecycleOwner,
                idToken = idToken,
                onResult = onResult
            )
        }
    }

    fun changeName(user: User, newName: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.changeName(user, newName)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun changePassword(user: User, newPassword: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.changePassword(user, newPassword)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }


    suspend fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}

class EmprendimientoModel(private val repository: EmprendimientoRepository) : ViewModel() {
    private val _emprendimiento = MutableStateFlow<List<Emprendimiento>>(emptyList())

    open val emprendimiento: StateFlow<List<Emprendimiento>> = _emprendimiento.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllEmprendimientos().collectLatest { emprendimientosFromDb ->
                _emprendimiento.value = emprendimientosFromDb
            }
        }
    }

    private val _emprendimientosFirebase = MutableStateFlow<List<Emprendimiento>>(emptyList())
    val emprendimientosFirebase: StateFlow<List<Emprendimiento>> = _emprendimientosFirebase.asStateFlow()

    fun getEmprendimientosFromFirebase() {
        viewModelScope.launch {
            val data = repository.getAllEmprendimientosFromFirebase()
            //Log.d("DEBUG", "Firebase data: $data")
            _emprendimientosFirebase.value = data
        }
    }

    fun addEmprendimiento(emprendimiento: Emprendimiento) {
        viewModelScope.launch {
            repository.insert(emprendimiento)
        }
    }

    fun updateEmprendimiento(emprendimiento: Emprendimiento) {
        viewModelScope.launch {
            repository.update(emprendimiento)
        }
    }

    fun deleteEmprendimiento(emprendimiento: Emprendimiento) {
        viewModelScope.launch {
            repository.delete(emprendimiento)
        }
    }

    fun getEmprendimientosByUserId(userId: String): List<Emprendimiento> {
        return emprendimiento.value.filter { it.idUsuario == userId }
    }

    private val _emprendimientoSelect = MutableStateFlow<Emprendimiento?>(null)
    val emprendimientoSelect: StateFlow<Emprendimiento?> = _emprendimientoSelect.asStateFlow()

    fun getEmprendimientoById(emprenId: String) {
        val empr = _emprendimiento.value.find { it.id == emprenId }
        _emprendimientoSelect.value = empr
    }

    fun getEmprendimientoByIdScreen(id: String): Emprendimiento? {
        return emprendimiento.value.find { it.id == id }
    }
}

class ProductoModel(private val repository: ProductoRepository) : ViewModel() {
    private val _producto = MutableStateFlow<List<Producto>>(emptyList())

    val producto: StateFlow<List<Producto>> = _producto.asStateFlow()

    var productEdit = MutableStateFlow<Producto?>(null)

    init {
        viewModelScope.launch {
            repository.getAllProductos().collectLatest { productosFromDb ->
                _producto.value = productosFromDb
            }
        }
    }

    fun addProducto(producto: Producto) {
        viewModelScope.launch {
            repository.insert(producto)
            _producto.value = _producto.value.map {
                if (it.id == producto.id) producto else it
            }
        }
    }

    fun updateProducto(producto: Producto) {
        viewModelScope.launch {
            repository.update(producto)
            _producto.value = _producto.value.map {
                if (it.id == producto.id) producto else it
            }
        }
    }

    fun deleteProducto(producto: Producto) {
        viewModelScope.launch {
            repository.delete(producto)
        }
    }

    fun getProductoByEmprendimientoId(emprendimientoId: String): List<Producto> {
        return producto.value.filter { it.idEmprendimiento == emprendimientoId }
    }
}

class HistorialModel(private val repository: HistorialRepository) : ViewModel() {
    private val _historial = MutableStateFlow<List<Historial>>(emptyList())

    val historial: StateFlow<List<Historial>> = _historial.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllHistoriales().collectLatest { historialFromDb ->
                _historial.value = historialFromDb
            }
        }
    }

    fun addHistorial(historial: Historial) {
        viewModelScope.launch {
            repository.insert(historial)
        }
    }

    fun updateHistorial(historial: Historial) {
        viewModelScope.launch {
            repository.update(historial)
        }
    }

    fun deleteHistorial(historial: Historial) {
        viewModelScope.launch {
            repository.delete(historial)
        }
    }
}