package com.github.albertobf.grocerycompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.albertobf.grocerycompanion.model.SizeType

@Dao
interface SizeTypeDao {
    @Insert
    suspend fun insert(sizeType: SizeType)

    @Query("SELECT * FROM SizeType ORDER BY name ASC")
    suspend fun getAll() : List<SizeType>

    @Query("SELECT * FROM SizeType WHERE id = :id")
    suspend fun getById(id: Long) : SizeType
}