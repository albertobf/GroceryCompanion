package com.github.albertobf.grocerycompanion.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.albertobf.grocerycompanion.database.*
import com.github.albertobf.grocerycompanion.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.collection.IsEmptyCollection
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class GroceryCompanionRepositoryTest {

    private lateinit var groceryCompanionRepository: GroceryCompanionRepository
    @Mock
    private lateinit var productDao: ProductDao
    @Mock
    private lateinit var sizeTypeDao: SizeTypeDao
    @Mock
    private lateinit var priceSupermarketDao: PriceSupermarketDao
    @Mock
    private lateinit var supermarketDao: SupermarketDao
    @Mock
    private lateinit var currencyDao: CurrencyDao

    private val sizeType = SizeType(1L, "g")
    private val productColacao = Product(name = "Colacao", size = 500f, sizeType = sizeType)
    private val productColacaoSF = Product(name = "Colacao Sugar Free", size = 500f, sizeType = sizeType)
    private val supermarket = Supermarket(name = "ASDA")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        groceryCompanionRepository = GroceryCompanionRepository(productDao, sizeTypeDao,
            priceSupermarketDao, supermarketDao, currencyDao)
    }

    @Test
    fun `should return an empty list when no products in database`() {
        val productsList = MutableLiveData<List<Product>>(emptyList())
        Mockito.`when`(productDao.getAll()).thenReturn(productsList)
        val products: LiveData<List<Product>> = groceryCompanionRepository.getAllProducts()
        assertThat(products.value, hasSize(0))
    }

    @Test
    fun `should return a list of products when database has records`() = runBlockingTest {
        mockListProducts()
        val products: LiveData<List<Product>> = groceryCompanionRepository.getAllProducts()
        assertThat(products.value, not(IsEmptyCollection.empty<Product>()))
    }

    @Test
    fun `should return a list of priceSupermarket when retrieving prices of a product`() = runBlockingTest {
        //Arrange
        mockListProducts()
        val priceSupermarket = PriceSupermarket(productColacao.id, supermarket.id, BigDecimal(3), 1L)
        val expected = listOf(priceSupermarket)
        Mockito.`when`(priceSupermarketDao.getByProductId(Mockito.anyLong())).thenReturn(expected)
        //Act
        val prices: List<PriceSupermarket> = groceryCompanionRepository.getPricesProduct(productColacao.id)
        //Assert
        assertThat(prices, `is`(expected))
    }

    @Test
    fun `should save changes when updating the price of a product`() = runBlockingTest {
        //Arrange
        mockListProducts()
        val priceSupermarket = PriceSupermarket(productColacao.id, supermarket.id, BigDecimal(3), 1L)
        Mockito.`when`(priceSupermarketDao.getByProductId(Mockito.anyLong())).thenReturn(listOf(priceSupermarket))
        //Act
        groceryCompanionRepository.updatePrice(priceSupermarket)
        //Assert
        Mockito.verify(priceSupermarketDao).update(priceSupermarket)
    }

    @Test
    fun `should save product on database when product is added`() = runBlockingTest {
        //Arrange
        val productColacao = Product(name = "Colacao", size = 500f, sizeType = sizeType)
        //Act
        groceryCompanionRepository.addProduct(productColacao)
        //Assert
        Mockito.verify(productDao).insert(productColacao)
    }

    @Test
    fun `should save supermarket on database when supermarket is added`() = runBlockingTest {
        //Arrange
        val supermarket = Supermarket(name = "ALDI")
        //Act
        groceryCompanionRepository.addSupermarket(supermarket)
        //Assert
        Mockito.verify(supermarketDao).insert(supermarket)
    }

    @Test
    fun `should save SizeType on database when sizeType is added`() = runBlockingTest {
        //Arrange
        val sizeType = SizeType(name = "kg")
        //Act
        groceryCompanionRepository.addSizeType(sizeType)
        //Assert
        Mockito.verify(sizeTypeDao).insert(sizeType)
    }

    @Test
    fun `should return a list of Supermarket when database has records`() = runBlockingTest {
        //Arrange
        val expected = listOf(supermarket)
        Mockito.`when`(supermarketDao.getAll()).thenReturn(expected)
        //Act
        val supermarkets: List<Supermarket> = groceryCompanionRepository.getSupermarkets()
        //Assert
        assertThat(supermarkets, `is`(expected))
    }

    @Test
    fun `should return an emptyList of Supermarket when database has no records`() = runBlockingTest {
        //Arrange
        Mockito.`when`(supermarketDao.getAll()).thenReturn(emptyList())
        //Act
        val supermarkets: List<Supermarket> = groceryCompanionRepository.getSupermarkets()
        //Assert
        assertThat(supermarkets, IsEmptyCollection.empty())
    }

    @Test
    fun `should return an emptyList of SizeType when database has no records`() = runBlockingTest {
        //Arrange
        Mockito.`when`(sizeTypeDao.getAll()).thenReturn(emptyList())
        //Act
        val sizeTypes: List<SizeType> = groceryCompanionRepository.getSizeTypes()
        //Assert
        assertThat(sizeTypes, IsEmptyCollection.empty())
    }

    @Test
    fun `should return a list of SizeType when database has records`() = runBlockingTest {
        //Arrange
        val sizeType = SizeType(1L, "g")
        val expected = listOf(sizeType)
        Mockito.`when`(sizeTypeDao.getAll()).thenReturn(expected)
        //Act
        val sizeTypes: List<SizeType> = groceryCompanionRepository.getSizeTypes()
        //Assert
        assertThat(sizeTypes, `is`(expected))
    }

    @Test
    fun `should return a list of Currency`() = runBlockingTest {
        //Arrange
        val currency = Currency(name = "EUR")
        val expected = listOf(currency)
        Mockito.`when`(currencyDao.getAll()).thenReturn(expected)
        //Act
        val currencies: List<Currency> = groceryCompanionRepository.getCurrencies()
        //Assert
        assertThat(currencies, `is`(expected))
    }

    @Test
    fun `should return the correct currency when fetching a currency`() = runBlockingTest {
        //Arrange
        val id = 1L
        val expected = Currency(id,"GBP")
        Mockito.`when`(currencyDao.getById(id)).thenReturn(expected)
        //Act
        val currency = groceryCompanionRepository.getCurrency(id)
        //Assert
        assertEquals(expected, currency)
    }

    @Test
    fun `should save Currency on the database`() = runBlockingTest {
        //Arrange
        val currency = Currency(name = "GBP")
        //Act
        groceryCompanionRepository.addCurrency(currency)
        //Assert
        Mockito.verify(currencyDao).insert(currency)
    }

    private fun mockListProducts() {
        val productsList = MutableLiveData<List<Product>>(listOf(productColacao,
            productColacaoSF))
        Mockito.`when`(productDao.getAll()).thenReturn(productsList)
    }
}