package com.github.albertobf.grocerycompanion.di

import android.content.Context
import androidx.room.Room
import com.github.albertobf.grocerycompanion.database.GroceryCompanionDatabase
import dagger.Module
import dagger.Provides

@Module
object RoomModule {

    @Provides
    fun provideRoomDatabase(applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            GroceryCompanionDatabase::class.java, "grocery_companion"
        ).build()

    @Provides
    fun provideCurrencyDAO(database: GroceryCompanionDatabase) = database.currencyDao

    @Provides
    fun providePriceSupermarketDAO(database: GroceryCompanionDatabase) = database.priceSupermarketDao

    @Provides
    fun provideProductDAO(database: GroceryCompanionDatabase) = database.productDao

    @Provides
    fun provideSizeTypeDAO(database: GroceryCompanionDatabase) = database.sizeTypeDao

    @Provides
    fun provideSupermarketDAO(database: GroceryCompanionDatabase) = database.supermarketDao

}