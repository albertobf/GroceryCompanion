package com.github.albertobf.grocerycompanion.model

data class Product(
    val id: Long,
    val name: String,
    val price: Float,
    val Size: Float,
    val currency: Currency,
    val sizeType: SizeType
)