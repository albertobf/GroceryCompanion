package com.github.albertobf.grocerycompanion.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import java.math.BigDecimal

@Entity(tableName = "price_supermarket",
    primaryKeys = ["product_id", "supermarket_id"],
    foreignKeys = [
        ForeignKey(entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = CASCADE),
        ForeignKey(entity = Currency::class,
            parentColumns = ["id"],
            childColumns = ["currency_id"],
            onDelete = CASCADE)
])
data class PriceSupermarket(
    @ColumnInfo(name = "product_id")
    val productId: Long,
    @Embedded(prefix = "supermarket_")
    val supermarket: Supermarket,
    var price: BigDecimal,
    @ColumnInfo(name = "currency_id")
    val currencyId: Long
)