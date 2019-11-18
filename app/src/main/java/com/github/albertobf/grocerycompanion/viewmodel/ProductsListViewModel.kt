package com.github.albertobf.grocerycompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.albertobf.grocerycompanion.database.init.getInitCurrencies
import com.github.albertobf.grocerycompanion.database.init.getInitSizeType
import com.github.albertobf.grocerycompanion.repository.GroceryCompanionRepository
import kotlinx.coroutines.launch

class ProductsListViewModel(private val groceryCompanionRepository: GroceryCompanionRepository) : ViewModel() {

    val products = groceryCompanionRepository.getAllProducts()

    init {
        //TODO: Change to Room onCallback
        viewModelScope.launch {
            if (groceryCompanionRepository.getCurrencies().isEmpty()) {
                getInitCurrencies().forEach { currency ->
                    groceryCompanionRepository.addCurrency(currency)
                }
            }
            if (groceryCompanionRepository.getSizeTypes().isEmpty()) {
                getInitSizeType().forEach { sizeType ->
                    groceryCompanionRepository.addSizeType(sizeType)
                }
            }
        }
    }

}