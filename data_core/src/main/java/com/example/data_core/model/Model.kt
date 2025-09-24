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
import com.example.data_core.repository.EmprendimientoRepository

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

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.update(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.delete(user)
        }
    }

    fun getUserById(id: String): User? {
        return user.value.find { it.id == id }
    }

    fun getUserByEmail(email: String): User? {
        return user.value.find { it.email == email }
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