package com.example.data_core.model

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

    fun registerWithEmailAndPassword(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.registerWithEmailAndPassword(user)
            onResult(success)
        }
    }

    fun registerWithGoogleAuthentication(idToken: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.registerWithGoogleAuthentication(idToken)
            onResult(success)
        }
    }

    fun loginWithEmailAndPassword(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.loginWithEmailAndPassword(user)
            onResult(success)
        }
    }

    fun loginWithGoogleAuthentication(idToken: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.loginWithGoogleAuthentication(idToken)
            onResult(success)
        }
    }

    suspend fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }

    fun updateUser(user: User, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            val currentUser = getCurrentUser()
            if (currentUser != null) {
                repository.updateUser(user)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}

class EmprendimientoModel(private val repository: EmprendimientoRepository) : ViewModel() {
    private val _emprendimiento = MutableStateFlow<List<Emprendimiento>>(emptyList())

    val emprendimiento: StateFlow<List<Emprendimiento>> = _emprendimiento.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllEmprendimientos().collectLatest { emprendimientosFromDb ->
                _emprendimiento.value = emprendimientosFromDb
            }
        }
    }

    fun addUser(emprendimiento: Emprendimiento) {
        viewModelScope.launch {
            repository.insert(emprendimiento)
        }
    }

    fun updateUser(emprendimiento: Emprendimiento) {
        viewModelScope.launch {
            repository.update(emprendimiento)
        }
    }

    fun deleteUser(emprendimiento: Emprendimiento) {
        viewModelScope.launch {
            repository.delete(emprendimiento)
        }
    }

    fun getUserById(id: String): Emprendimiento? {
        return emprendimiento.value.find { it.id == id }
    }
}

class ProductoModel(private val repository: ProductoRepository) : ViewModel() {
    private val _producto = MutableStateFlow<List<Producto>>(emptyList())

    val producto: StateFlow<List<Producto>> = _producto.asStateFlow()

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
        }
    }

    fun updateProducto(producto: Producto) {
        viewModelScope.launch {
            repository.update(producto)
        }
    }

    fun deleteProducto(producto: Producto) {
        viewModelScope.launch {
            repository.delete(producto)
        }
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