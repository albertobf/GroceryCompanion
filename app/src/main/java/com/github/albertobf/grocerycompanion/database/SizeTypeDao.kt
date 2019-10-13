package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.SizeType

@Dao
interface SizeTypeDao {
    @Insert
    fun insert(sizeType: SizeType)

    @Query("SELECT * FROM SizeType ORDER BY name ASC")
    fun getAll() : List<SizeType>

    @Query("SELECT * FROM SizeType WHERE id = :id")
    fun getById(id: Long) : SizeType
}