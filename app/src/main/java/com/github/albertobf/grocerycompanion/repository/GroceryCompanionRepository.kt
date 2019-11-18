package com.github.albertobf.grocerycompanion.repository

import androidx.lifecycle.LiveData
import com.github.albertobf.grocerycompanion.database.*
import com.github.albertobf.grocerycompanion.model.*
import javax.inject.Inject

class GroceryCompanionRepository @Inject constructor(
    private val productDao: ProductDao,
    private val sizeTypeDao: SizeTypeDao,
    private val priceSupermarketDao: PriceSupermarketDao,
    private val supermarketDao: SupermarketDao,
    private val currencyDao: CurrencyDao
) {

    fun getAllProducts() : LiveData<List<Product>> = productDao.getAll()

    suspend fun getPricesProduct(productId: Long) = priceSupermarketDao.getByProductId(productId)

    suspend fun updatePrice(priceSupermarket: PriceSupermarket) =
        priceSupermarketDao.update(priceSupermarket)

    suspend fun addProduct(product: Product) = productDao.insert(product)

    suspend fun addSupermarket(supermarket: Supermarket) = supermarketDao.insert(supermarket)

    suspend fun addSizeType(sizeType: SizeType) = sizeTypeDao.insert(sizeType)

    suspend fun getSupermarkets(): List<Supermarket> = supermarketDao.getAll()

    suspend fun getSizeTypes(): List<SizeType> = sizeTypeDao.getAll()

    suspend fun getCurrencies(): List<Currency> = currencyDao.getAll()

    suspend fun getCurrency(id: Long) = currencyDao.getById(id)

    suspend fun addCurrency(currency: Currency) = currencyDao.insert(currency)

}