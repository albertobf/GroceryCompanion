package com.github.albertobf.grocerycompanion.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.albertobf.grocerycompanion.model.PriceSupermarket
import com.github.albertobf.grocerycompanion.model.PricesProduct
import com.github.albertobf.grocerycompanion.model.parcelable.ProductParcelable
import com.github.albertobf.grocerycompanion.repository.GroceryCompanionRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class AddPriceViewModel @Inject constructor(private val repository: GroceryCompanionRepository) : ViewModel() {

    val product = MutableLiveData<ProductParcelable>()
    private val _supermarkets = MutableLiveData<List<String>>()
    val supermarkets: LiveData<List<String>>
        get() = _supermarkets
    val supermarketName = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    private val _currencies = MutableLiveData<List<String>>()
    val currencies: LiveData<List<String>>
        get() = _currencies
    val currencyName = MutableLiveData<String>()
    val priceSaved = MutableLiveData<Boolean>(false)

    init {
        loadSupermarkets()
        loadCurrencies()
    }

    fun loadSupermarkets() {
        viewModelScope.launch {
            _supermarkets.value = repository.getSupermarketsName()
        }
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            _currencies.value = repository.getCurrencies().map { it.name }
        }
    }

    fun savePrice() {
        viewModelScope.launch {
            val productId = product.value!!.id
            val supermarket = repository.getSupermarketByName(supermarketName.value!!)
            val priceBD = BigDecimal(price.value)
            val currency = repository.getCurrencyByName(currencyName.value!!)
            val priceSupermarket = PriceSupermarket(productId, supermarket, priceBD, currency.id)
            repository.savePriceSupermarket(priceSupermarket)
            priceSaved.value = true
        }
    }

}