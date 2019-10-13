package com.github.albertobf.grocerycompanion.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = SizeType::class,
        parentColumns = ["id"],
        childColumns = ["sizetype_id"]
    )
])
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String,
    val size: Float,
    @ColumnInfo(name = "sizetype_id")
    val sizeTypeId: Long
)