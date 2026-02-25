package com.example.inventorymanagement.di

import android.content.Context
import androidx.room.Room
import com.example.inventorymanagement.data.local.AppDatabase
import com.example.inventorymanagement.data.local.dao.CategoryDao
import com.example.inventorymanagement.data.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {

        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database"
        ).fallbackToDestructiveMigration().build()

    }

    @Provides
    @Singleton
    fun provideProductDao(db: AppDatabase): ProductDao{
        return db.productDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase): CategoryDao{
        return db.categoryDao()
    }

}