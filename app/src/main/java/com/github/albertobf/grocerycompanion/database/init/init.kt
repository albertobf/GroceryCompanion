package com.github.albertobf.grocerycompanion.database.init

import com.github.albertobf.grocerycompanion.model.Currency
import com.github.albertobf.grocerycompanion.model.SizeType


fun getInitCurrencies(): List<Currency> {
    val euro = Currency(name = "EUR")
    val gbp = Currency(name = "GBP")
    return listOf(euro, gbp)
}

fun getInitSizeType(): List<SizeType> {
    val g = SizeType(name = "g")
    val kg = SizeType(name = "kg")
    val l = SizeType(name = "l")
    val u = SizeType(name = "u")
    return listOf(g, kg, l, u)
}