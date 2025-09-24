package com.example.data_core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User ORDER BY id ASC")
    fun getAllUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun  deleteUser(user: User)
}

@Dao
interface EmprendimeintosDao {
    @Query("SELECT * FROM Emprendimiento ORDER BY id ASC")
    fun getAllEmprendimientos(): Flow<List<Emprendimiento>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmprendimiento(emprendimiento: Emprendimiento)

    @Update
    suspend fun updateEmprendimiento(emprendimiento: Emprendimiento)

    @Delete
    suspend fun  deleteEmprendimiento(emprendimiento: Emprendimiento)
}