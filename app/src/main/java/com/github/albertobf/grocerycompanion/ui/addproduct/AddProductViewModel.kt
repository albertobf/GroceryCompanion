package com.github.albertobf.grocerycompanion.ui.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.albertobf.grocerycompanion.model.SizeType
import com.github.albertobf.grocerycompanion.model.dto.ProductDTO
import com.github.albertobf.grocerycompanion.repository.GroceryCompanionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repository: GroceryCompanionRepository) : ViewModel() {

    private val _sizeTypes = MutableLiveData<List<SizeType>>()
    val sizeTypes: LiveData<List<SizeType>>
        get() = _sizeTypes
    val product = MutableLiveData<ProductDTO>()
    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    init {
        getSizeTypes()
        product.value = ProductDTO()
    }

    private fun getSizeTypes() {
        viewModelScope.launch {
            _sizeTypes.value = repository.getSizeTypes()
        }
    }

    fun addProduct() {
        product.value?.let {product ->
            viewModelScope.launch {
                val sizeType = repository.getSizeTypeByName(product.sizeDesc)
                val productEntity = product.toEntity(sizeType)
                repository.addProduct(productEntity)
                _navigate.value = true
            }
        }
    }

}