package com.github.albertobf.grocerycompanion.model

import androidx.room.*

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
    @Embedded(prefix = "sizetype_")
    val sizeType: SizeType
)