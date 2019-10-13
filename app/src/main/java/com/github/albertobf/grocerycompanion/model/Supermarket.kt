package com.github.albertobf.grocerycompanion.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Supermarket(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String
)