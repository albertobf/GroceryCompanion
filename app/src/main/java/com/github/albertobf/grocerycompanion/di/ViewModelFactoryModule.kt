package com.github.albertobf.grocerycompanion.di

import androidx.lifecycle.ViewModelProvider
import com.github.albertobf.grocerycompanion.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}