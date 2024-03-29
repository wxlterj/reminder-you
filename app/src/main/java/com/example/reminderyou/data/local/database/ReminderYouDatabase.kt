package com.example.reminderyou.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminderyou.data.local.dao.CategoryDao
import com.example.reminderyou.data.local.dao.TaskDao
import com.example.reminderyou.data.local.entity.CategoryEntity
import com.example.reminderyou.data.local.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        CategoryEntity::class
    ],
    version = 4
)
abstract class ReminderYouDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getCategoryDao(): CategoryDao
}