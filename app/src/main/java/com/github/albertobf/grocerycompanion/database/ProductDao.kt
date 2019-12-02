package com.github.albertobf.grocerycompanion.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.PricesProduct
import com.github.albertobf.grocerycompanion.model.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: Product)

    @Query("SELECT * FROM Product ORDER BY name ASC")
    fun getAll() : LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE id = :id")
    suspend fun getById(id: Long) : Product

    @Query("SELECT * FROM Product WHERE id = :id")
    suspend fun getPrices(id: Long) : PricesProduct
}