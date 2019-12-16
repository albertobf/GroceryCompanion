package com.github.albertobf.grocerycompanion.ui.addsupermarket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.albertobf.grocerycompanion.model.Supermarket
import com.github.albertobf.grocerycompanion.repository.GroceryCompanionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddSupermarketViewModel @Inject constructor(private val repository: GroceryCompanionRepository) : ViewModel() {

    private val _newSupermarketId = MutableLiveData<Long>()
    val newSupermarketId: LiveData<Long>
        get() = _newSupermarketId

    fun addSupermarket(supermarketName: String) {
        val supermarket = Supermarket(name = supermarketName)
        viewModelScope.launch {
            _newSupermarketId.value = repository.addSupermarket(supermarket)
        }
    }

}