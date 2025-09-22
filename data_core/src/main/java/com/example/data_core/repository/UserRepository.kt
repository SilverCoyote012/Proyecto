package com.example.data_core.repository

import com.example.data_core.database.User
import com.example.data_core.database.UserDao
import kotlinx.coroutines.flow.Flow

import com.example.data_core.firebase.FirebaseService

class UserRepository (
    private val userDao: UserDao,
    private val firebaseService: FirebaseService
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