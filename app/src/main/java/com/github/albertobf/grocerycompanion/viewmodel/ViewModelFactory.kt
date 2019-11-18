package com.github.albertobf.grocerycompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.albertobf.grocerycompanion.repository.GroceryCompanionRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: GroceryCompanionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductsListViewModel::class.java)) {
            return ProductsListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}