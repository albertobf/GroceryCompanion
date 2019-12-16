package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.Supermarket

@Dao
interface SupermarketDao {
    @Insert
    suspend fun insert(supermarket: Supermarket): Long

    @Query("SELECT * FROM Supermarket ORDER BY name ASC")
    suspend fun getAll() : List<Supermarket>

    @Query("SELECT name FROM Supermarket ORDER BY name ASC")
    suspend fun getNames() : List<String>

    @Query("SELECT * FROM Supermarket WHERE id = :id")
    suspend fun getById(id: Long) : Supermarket

    @Query("SELECT * FROM Supermarket WHERE name = :name")
    suspend fun getByName(name: String): Supermarket
}