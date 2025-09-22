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