package com.example.data_core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


// Clase para definir la base de datos y las entidades que tendra


@Database(
    entities = [User::class, Emprendimiento::class, Producto::class, Historial::class],
    version = 6, // Numero de version de la base de datos en la que vamos, debe ser cambiado cada vez que se modifique la base de datos para que se actualice la version y no se utilice una que no estara actualizada
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun emprendimientoDao(): EmprendimeintosDao
    abstract fun productoDao(): ProductoDao
    abstract fun historialDao(): HistorialDao
}