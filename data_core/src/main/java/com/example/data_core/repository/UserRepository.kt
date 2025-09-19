package com.example.data_core.repository

import com.example.data_core.database.User
import com.example.data_core.database.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDao: UserDao) {
    fun getAllContacts(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun insert(user: User) {
        userDao.insertUser(user)
    }

    suspend fun update(user: User) {
        userDao.updateUser(user)
    }

    suspend fun delete(user: User) {
        userDao.deleteUser(user)
    }
}