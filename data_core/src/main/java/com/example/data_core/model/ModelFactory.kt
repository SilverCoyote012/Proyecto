package com.example.data_core.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_core.repository.EmprendimientoRepository
import com.example.data_core.repository.UserRepository

class UserModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserModel(repository) as T
        }
        throw IllegalArgumentException("Uknown ViewModel Class")
    }
}

class EmprendimientoModelFactory(
    private val repository: EmprendimientoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmprendimientoModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmprendimientoModel(repository) as T
        }
        throw IllegalArgumentException("Uknown ViewModel Class")
    }
}