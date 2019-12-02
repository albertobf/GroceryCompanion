package com.github.albertobf.grocerycompanion

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.albertobf.grocerycompanion.database.*
import com.github.albertobf.grocerycompanion.model.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class RoomTest {

    companion object {
        const val ID = 1L
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var currencyDao: CurrencyDao
    private lateinit var sizeTypeDao: SizeTypeDao
    private lateinit var supermarketDao: SupermarketDao
    private lateinit var productDao: ProductDao
    private lateinit var priceSupermarketDao: PriceSupermarketDao
    private lateinit var db: GroceryCompanionDatabase
    private val supermarket = Supermarket(ID,"ASDA")

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, GroceryCompanionDatabase::class.java).build()
        currencyDao = db.currencyDao
        sizeTypeDao = db.sizeTypeDao
        supermarketDao = db.supermarketDao
        productDao = db.productDao
        priceSupermarketDao = db.priceSupermarketDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    //Currency tests
    @Test
    fun currencyInsertTest() = runBlocking {
        val currency = Currency(name = "EUR")
        currencyDao.insert(currency)
        assertEquals(currencyDao.getAll().size, 1)
    }

    @Test
    fun currencyGetByIdTest() = runBlocking {
        val currency = Currency(ID, "EUR")
        currencyDao.insert(currency)
        assertThat(currencyDao.getById(ID), `is`(currency))
    }

    @Test
    fun currencyGetAllTest() = runBlocking {
        val currency = Currency(name = "EUR")
        val currency2 = Currency(name = "GBP")
        currencyDao.insert(currency)
        currencyDao.insert(currency2)
        assertThat(currencyDao.getAll().size, `is`(2))
    }

    //SizeType tests
    @Test
    fun sizeTypeInsertTest() = runBlocking {
        val sizeType = SizeType(name = "kg")
        sizeTypeDao.insert(sizeType)
        assertEquals(sizeTypeDao.getAll().size, 1)
    }

    @Test
    fun sizeTypeGetByIdTest() = runBlocking {
        val sizeType = SizeType(ID,"kg")
        sizeTypeDao.insert(sizeType)
        assertThat(sizeTypeDao.getById(ID), `is`(sizeType))
    }

    @Test
    fun sizeTypeGetAllTest() = runBlocking {
        val sizeType = SizeType(name = "kg")
        val sizeType2 = SizeType(name = "g")
        sizeTypeDao.insert(sizeType)
        sizeTypeDao.insert(sizeType2)
        assertThat(sizeTypeDao.getAll().size, `is`(2))
    }

    //Supermarket tests
    @Test
    fun supermarketInsertTest() = runBlocking {
        val supermarket = Supermarket(name = "ASDA")
        supermarketDao.insert(supermarket)
        assertEquals(supermarketDao.getAll().size, 1)
    }

    @Test
    fun supermarketGetByIdTest() = runBlocking {
        val supermarket = Supermarket(ID,"ASDA")
        supermarketDao.insert(supermarket)
        assertThat(supermarketDao.getById(ID), `is`(supermarket))
    }

    @Test
    fun supermarketGetAllTest() = runBlocking {
        val supermarket = Supermarket(name = "ASDA")
        val supermarket2 = Supermarket(name = "ALDI")
        supermarketDao.insert(supermarket)
        supermarketDao.insert(supermarket2)
        assertThat(supermarketDao.getAll().size, `is`(2))
    }

    //Product tests
    @Test
    fun productInsertTest() = runBlocking {
        insertSizeType()
        val product = Product(name = "Colacao", size = 400, sizeType = SizeType(ID, "kg"))
        productDao.insert(product)
        assertEquals(productDao.getAll().getOrAwaitValue().size, 1)
    }

    @Test
    fun productGetByIdTest() = runBlocking {
        insertSizeType()
        val product = Product(ID, "Colacao", 400, SizeType(ID, "kg"))
        productDao.insert(product)
        assertThat(productDao.getById(ID), `is`(product))
    }

    @Test
    fun productGetAllTest() = runBlocking {
        insertSizeType()
        val product = Product(name = "Colacao", size = 400, sizeType = SizeType(ID, "kg"))
        val product2 = Product(name = "Colacao", size = 800, sizeType = SizeType(ID, "kg"))
        productDao.insert(product)
        productDao.insert(product2)
        assertThat(productDao.getAll().getOrAwaitValue().size, `is`(2))
    }

    //PriceSupermarket tests
    @Test
    fun priceSupermarketInsertTest() = runBlocking {
        insertSizeType()
        insertProduct()
        insertSupermarket()
        insertCurrency()
        val priceSupermarket = PriceSupermarket(ID, supermarket, BigDecimal(3), ID)
        priceSupermarketDao.insert(priceSupermarket)
        assertEquals(priceSupermarketDao.getAll().size, 1)
    }

    @Test
    fun priceSupermarketGetByProductTest() = runBlocking {
        insertSizeType()
        insertSupermarket()
        insertCurrency()
        val product = Product(ID, "Colacao", 400, SizeType(ID, "kg"))
        productDao.insert(product)
        val priceSupermarket = PriceSupermarket(product.id, supermarket, BigDecimal(3), ID)
        priceSupermarketDao.insert(priceSupermarket)
        assertEquals(priceSupermarketDao.getByProductId(product.id), listOf(priceSupermarket))
    }

    @Test
    fun priceSupermarketUpdate() = runBlocking {
        insertSizeType()
        insertSupermarket()
        insertCurrency()
        val product = Product(ID, "Colacao", 400, SizeType(ID, "kg"))
        productDao.insert(product)
        val priceSupermarket = PriceSupermarket(product.id, supermarket, BigDecimal(3), ID)
        priceSupermarketDao.insert(priceSupermarket)

        val expected = BigDecimal(10)
        priceSupermarket.price = expected
        priceSupermarketDao.update(priceSupermarket)
        val updatedPriceSupermarket = priceSupermarketDao.getByProductAndSupermarket(
            priceSupermarket.productId, priceSupermarket.supermarket.id)
        assertThat(updatedPriceSupermarket.price, `is`(expected))
    }

    @Test
    fun pricesProduct() = runBlocking {
        insertSizeType()
        insertSupermarket()
        insertCurrency()
        val product = Product(ID, "Colacao", 400, SizeType(ID, "kg"))
        productDao.insert(product)
        val priceSupermarket = PriceSupermarket(product.id, supermarket, BigDecimal(3), ID)
        priceSupermarketDao.insert(priceSupermarket)
        val supermarket2 = Supermarket(name = "TESCO")
        supermarketDao.insert(supermarket2)
        val priceSupermarket2 = PriceSupermarket(product.id, supermarket2, BigDecimal(5), ID)
        priceSupermarketDao.insert(priceSupermarket2)
        val pricesProduct = productDao.getPrices(ID)
        assertThat(pricesProduct.priceSupermarket.size, `is`(2))
    }

    private suspend fun insertSizeType() {
        sizeTypeDao.insert(SizeType(ID, "g"))
    }

    private suspend fun insertProduct() {
        productDao.insert(Product(ID, "Colacao", 400, SizeType(ID, "kg")))
    }

    private suspend fun insertSupermarket() {
        supermarketDao.insert(supermarket)
    }

    private suspend fun insertCurrency() {
        currencyDao.insert(Currency(ID, "EUR"))
    }
}