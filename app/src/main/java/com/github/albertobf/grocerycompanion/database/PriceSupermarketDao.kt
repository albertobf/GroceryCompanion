package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.PriceSupermarket

@Dao
interface PriceSupermarketDao {
    @Insert
    fun insert(priceSupermarket: PriceSupermarket)

    @Query("SELECT * FROM price_supermarket")
    fun getAll() : List<PriceSupermarket>

    @Query("SELECT * FROM price_supermarket WHERE product_id = :productId")
    fun getByProductId(productId: Long) : List<PriceSupermarket>
}