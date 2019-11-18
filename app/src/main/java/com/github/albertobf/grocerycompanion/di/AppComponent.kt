package com.github.albertobf.grocerycompanion.di

import android.content.Context
import com.github.albertobf.grocerycompanion.ui.ProductsListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [RoomModule::class])
interface AppComponent {

    fun inject(target: ProductsListFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}