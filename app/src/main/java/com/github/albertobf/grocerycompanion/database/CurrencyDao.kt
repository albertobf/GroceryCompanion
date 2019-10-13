package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.Currency

@Dao
interface CurrencyDao {
    @Insert
    fun insert(currency: Currency)

    @Query("SELECT * FROM Currency ORDER BY name ASC")
    fun getAll() : List<Currency>

    @Query("SELECT * FROM Currency WHERE id = :id")
    fun getById(id: Long) : Currency
}