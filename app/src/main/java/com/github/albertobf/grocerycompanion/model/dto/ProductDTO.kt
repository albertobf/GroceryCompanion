package com.github.albertobf.grocerycompanion.model.dto

import com.github.albertobf.grocerycompanion.model.Product
import com.github.albertobf.grocerycompanion.model.SizeType

data class ProductDTO (
    var name: String = "",
    var size: String = "",
    var sizeDesc: String = ""
) {
    fun toEntity(sizeType: SizeType): Product {
        return Product(name = name,
            size = size.toInt(),
            sizeType = sizeType)
    }
}