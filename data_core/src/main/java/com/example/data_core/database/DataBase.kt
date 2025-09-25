package com.example.data_core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [User::class, Emprendimiento::class, Producto::class, Historial::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun emprendimientoDao(): EmprendimeintosDao
    abstract fun productoDao(): ProductoDao
    abstract fun historialDao(): HistorialDao
}