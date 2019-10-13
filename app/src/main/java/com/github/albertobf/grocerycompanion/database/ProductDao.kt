package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.Product

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)

    @Query("SELECT * FROM Product ORDER BY name ASC")
    fun getAll() : List<Product>

    @Query("SELECT * FROM Product WHERE id = :id")
    fun getById(id: Long) : Product
}