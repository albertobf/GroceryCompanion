package com.github.albertobf.grocerycompanion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.albertobf.grocerycompanion.database.converter.Converters
import com.github.albertobf.grocerycompanion.model.*

@Database(entities = [
    Currency::class, Product::class, SizeType::class, Supermarket::class, PriceSupermarket::class
    ],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GroceryCompanionDatabase : RoomDatabase(){
    abstract val currencyDao: CurrencyDao
    abstract val productDao: ProductDao
    abstract val sizeTypeDao: SizeTypeDao
    abstract val supermarketDao: SupermarketDao
    abstract val priceSupermarketDao: PriceSupermarketDao

    companion object {

        @Volatile
        private var INSTANCE: GroceryCompanionDatabase? = null

        fun getDatabase(context: Context): GroceryCompanionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryCompanionDatabase::class.java,
                    "grocery_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}