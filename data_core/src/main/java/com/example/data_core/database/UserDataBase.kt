package com.example.data_core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data_core.firebase.FirebaseService

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}