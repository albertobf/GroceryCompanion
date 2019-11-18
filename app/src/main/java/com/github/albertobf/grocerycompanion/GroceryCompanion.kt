package com.github.albertobf.grocerycompanion

import android.app.Application
import com.github.albertobf.grocerycompanion.di.DaggerAppComponent

class GroceryCompanion : Application() {

    val component by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

}