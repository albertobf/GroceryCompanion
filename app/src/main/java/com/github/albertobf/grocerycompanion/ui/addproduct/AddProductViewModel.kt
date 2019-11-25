package com.github.albertobf.grocerycompanion.ui.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.albertobf.grocerycompanion.model.SizeType
import com.github.albertobf.grocerycompanion.repository.GroceryCompanionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repository: GroceryCompanionRepository) : ViewModel() {

    private val _sizeTypes = MutableLiveData<List<SizeType>>()
    val sizeTypes: LiveData<List<SizeType>>
        get() = _sizeTypes

    init {
        getSizeTypes()
    }

    private fun getSizeTypes() {
        viewModelScope.launch {
            _sizeTypes.value = repository.getSizeTypes()
        }
    }

}