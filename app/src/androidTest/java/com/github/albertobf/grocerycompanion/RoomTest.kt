package com.github.albertobf.grocerycompanion

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.albertobf.grocerycompanion.database.*
import com.github.albertobf.grocerycompanion.model.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is`
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class RoomTest {

    companion object {
        const val ID = 1L
    }

    private lateinit var currencyDao: CurrencyDao
    private lateinit var sizeTypeDao: SizeTypeDao
    private lateinit var supermarketDao: SupermarketDao
    private lateinit var productDao: ProductDao
    private lateinit var priceSupermarketDao: PriceSupermarketDao
    private lateinit var db: GroceryCompanionDatabase

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
    fun currencyInsertTest() {
        val currency = Currency(name = "EUR")
        currencyDao.insert(currency)
        Assert.assertEquals(currencyDao.getAll().size, 1)
    }

    @Test
    fun currencyGetByIdTest() {
        val currency = Currency(ID, "EUR")
        currencyDao.insert(currency)
        Assert.assertThat(currencyDao.getById(ID), `is`(currency))
    }

    @Test
    fun currencyGetAllTest() {
        val currency = Currency(name = "EUR")
        val currency2 = Currency(name = "GBP")
        currencyDao.insert(currency)
        currencyDao.insert(currency2)
        Assert.assertThat(currencyDao.getAll().size, `is`(2))
    }

    //SizeType tests
    @Test
    fun sizeTypeInsertTest() {
        val sizeType = SizeType(name = "kg")
        sizeTypeDao.insert(sizeType)
        Assert.assertEquals(sizeTypeDao.getAll().size, 1)
    }

    @Test
    fun sizeTypeGetByIdTest() {
        val sizeType = SizeType(ID,"kg")
        sizeTypeDao.insert(sizeType)
        Assert.assertThat(sizeTypeDao.getById(ID), `is`(sizeType))
    }

    @Test
    fun sizeTypeGetAllTest() {
        val sizeType = SizeType(name = "kg")
        val sizeType2 = SizeType(name = "g")
        sizeTypeDao.insert(sizeType)
        sizeTypeDao.insert(sizeType2)
        Assert.assertThat(sizeTypeDao.getAll().size, `is`(2))
    }

    //Supermarket tests
    @Test
    fun supermarketInsertTest() {
        val supermarket = Supermarket(name = "ASDA")
        supermarketDao.insert(supermarket)
        Assert.assertEquals(supermarketDao.getAll().size, 1)
    }

    @Test
    fun supermarketGetByIdTest() {
        val supermarket = Supermarket(ID,"ASDA")
        supermarketDao.insert(supermarket)
        Assert.assertThat(supermarketDao.getById(ID), `is`(supermarket))
    }

    @Test
    fun supermarketGetAllTest() {
        val supermarket = Supermarket(name = "ASDA")
        val supermarket2 = Supermarket(name = "ALDI")
        supermarketDao.insert(supermarket)
        supermarketDao.insert(supermarket2)
        Assert.assertThat(supermarketDao.getAll().size, `is`(2))
    }

    //Product tests
    @Test
    fun productInsertTest() {
        insertSizeType()
        val product = Product(name = "Colacao", size = 400f, sizeTypeId = 1L)
        productDao.insert(product)
        Assert.assertEquals(productDao.getAll().size, 1)
    }

    @Test
    fun productGetByIdTest() {
        insertSizeType()
        val product = Product(ID, "Colacao", 400f, 1L)
        productDao.insert(product)
        Assert.assertThat(productDao.getById(ID), `is`(product))
    }

    @Test
    fun productGetAllTest() {
        insertSizeType()
        val product = Product(name = "Colacao", size = 400f, sizeTypeId = 1L)
        val product2 = Product(name = "Colacao", size = 800f, sizeTypeId = 1L)
        productDao.insert(product)
        productDao.insert(product2)
        Assert.assertThat(productDao.getAll().size, `is`(2))
    }

    //PriceSupermarket tests
    @Test
    fun priceSupermarketInsertTest() {
        insertSizeType()
        insertProduct()
        insertSupermarket()
        insertCurrency()
        val priceSupermarket = PriceSupermarket(ID, ID, BigDecimal(3), ID)
        priceSupermarketDao.insert(priceSupermarket)
        Assert.assertEquals(priceSupermarketDao.getAll().size, 1)
    }

    @Test
    fun priceSupermarketGetByProductTest() {
        insertSizeType()
        insertSupermarket()
        insertCurrency()
        val product = Product(ID, "Colacao", 400f, 1L)
        productDao.insert(product)
        val priceSupermarket = PriceSupermarket(product.id, ID, BigDecimal(3), ID)
        priceSupermarketDao.insert(priceSupermarket)
        Assert.assertEquals(priceSupermarketDao.getByProductId(product.id), listOf(priceSupermarket))
    }

    private fun insertSizeType() {
        sizeTypeDao.insert(SizeType(ID, "kg"))
    }

    private fun insertProduct() {
        productDao.insert(Product(ID, "Colacao", 400f, 1L))
    }

    private fun insertSupermarket() {
        supermarketDao.insert(Supermarket(ID,"ASDA"))
    }

    private fun insertCurrency() {
        currencyDao.insert(Currency(ID, "EUR"))
    }
}