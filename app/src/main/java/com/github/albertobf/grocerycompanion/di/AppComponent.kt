package com.github.albertobf.grocerycompanion.di

import android.content.Context
import com.github.albertobf.grocerycompanion.ui.addproduct.AddProductFragment
import com.github.albertobf.grocerycompanion.ui.list.ProductsListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [RoomModule::class, ViewModelFactoryModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: ProductsListFragment)
    fun inject(target: AddProductFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}