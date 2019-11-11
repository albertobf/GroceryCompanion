package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.albertobf.grocerycompanion.model.PriceSupermarket

@Dao
interface PriceSupermarketDao {
    @Insert
    suspend fun insert(priceSupermarket: PriceSupermarket)

    @Query("SELECT * FROM price_supermarket")
    suspend fun getAll() : List<PriceSupermarket>

    @Query("SELECT * FROM price_supermarket WHERE product_id = :productId")
    suspend fun getByProductId(productId: Long) : List<PriceSupermarket>

    @Query("SELECT * FROM price_supermarket WHERE product_id = :productId AND supermarket_id = :supermarketId")
    suspend fun getByProductAndSupermarket(productId: Long, supermarketId: Long) : PriceSupermarket

    @Update
    suspend fun update(priceSupermarket: PriceSupermarket)
}