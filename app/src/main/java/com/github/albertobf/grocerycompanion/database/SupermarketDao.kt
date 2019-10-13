package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.Supermarket

@Dao
interface SupermarketDao {
    @Insert
    fun insert(supermarket: Supermarket)

    @Query("SELECT * FROM Supermarket ORDER BY name ASC")
    fun getAll() : List<Supermarket>

    @Query("SELECT * FROM Supermarket WHERE id = :id")
    fun getById(id: Long) : Supermarket
}