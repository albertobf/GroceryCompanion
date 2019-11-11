package com.github.albertobf.grocerycompanion.repository

import androidx.lifecycle.LiveData
import com.github.albertobf.grocerycompanion.database.PriceSupermarketDao
import com.github.albertobf.grocerycompanion.database.ProductDao
import com.github.albertobf.grocerycompanion.database.SizeTypeDao
import com.github.albertobf.grocerycompanion.database.SupermarketDao
import com.github.albertobf.grocerycompanion.model.PriceSupermarket
import com.github.albertobf.grocerycompanion.model.Product
import com.github.albertobf.grocerycompanion.model.SizeType
import com.github.albertobf.grocerycompanion.model.Supermarket

class GroceryCompanionRepository(
    private val productDao: ProductDao,
    private val sizeTypeDao: SizeTypeDao,
    private val priceSupermarketDao: PriceSupermarketDao,
    private val supermarketDao: SupermarketDao
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

}