package com.example.data_core.repository

import com.example.data_core.database.Emprendimiento
import com.example.data_core.database.EmprendimeintosDao
import com.example.data_core.firebase.FirebaseServiceEmprendimiento
import com.example.data_core.database.User
import com.example.data_core.database.UserDao
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