package com.github.albertobf.grocerycompanion.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.albertobf.grocerycompanion.model.PricesProduct
import com.github.albertobf.grocerycompanion.model.parcelable.ProductParcelable
import com.github.albertobf.grocerycompanion.repository.GroceryCompanionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val repository: GroceryCompanionRepository)
    : ViewModel() {

    private val _pricesProduct = MutableLiveData<PricesProduct>()
    val pricesProduct: LiveData<PricesProduct>
        get() = _pricesProduct

    fun loadProductDetails(productId: Long) {
        viewModelScope.launch {
            _pricesProduct.value = repository.getPricesProduct(productId)
        }
    }

    fun getProductParcelable(): ProductParcelable {
        val product = _pricesProduct.value!!.product
        with(product) {
            return ProductParcelable(id, name, size)
        }
    }
}