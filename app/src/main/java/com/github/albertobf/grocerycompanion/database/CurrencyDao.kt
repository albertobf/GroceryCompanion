package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.Currency

@Dao
interface CurrencyDao {
    @Insert
    suspend fun insert(currency: Currency)

    @Query("SELECT * FROM Currency ORDER BY name ASC")
    suspend fun getAll() : List<Currency>

    @Query("SELECT * FROM Currency WHERE id = :id")
    suspend fun getById(id: Long) : Currency

    @Query("SELECT * FROM Currency WHERE name = :name")
    suspend fun getByName(name: String): Currency
}