package com.example.data_core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


// Funciones para acceder a la base de datos utiliando Dao


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

@Dao
interface ProductoDao {
    @Query("SELECT * FROM Producto ORDER BY id ASC")
    fun getAllProductos(): Flow<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducto(producto: Producto)

    @Update
    suspend fun updateProducto(producto: Producto)

    @Delete
    suspend fun  deleteProducto(producto: Producto)
}

@Dao
interface HistorialDao {
    @Query("SELECT * FROM Historial ORDER BY id ASC")
    fun getAllHistoriales(): Flow<List<Historial>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorial(historial: Historial)

    @Update
    suspend fun updateHistorial(historial: Historial)

    @Delete
    suspend fun  deleteHistorial(historial: Historial)
}