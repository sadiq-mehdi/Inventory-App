package com.example.inventorymanagement.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventorymanagement.data.local.dao.CategoryDao
import com.example.inventorymanagement.data.local.dao.ProductDao
import com.example.inventorymanagement.data.local.entity.Category
import com.example.inventorymanagement.data.local.entity.Product

@Database(entities = [Product::class, Category::class], version = 3)
abstract class AppDatabase: RoomDatabase(){

    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao

}