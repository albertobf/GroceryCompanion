package com.github.albertobf.grocerycompanion.model

import androidx.room.Embedded
import androidx.room.Relation

data class PricesProduct(
    @Embedded
    val product: Product,
    @Relation(parentColumn = "id", entityColumn = "product_id")
    val priceSupermarket: List<PriceSupermarket>
)