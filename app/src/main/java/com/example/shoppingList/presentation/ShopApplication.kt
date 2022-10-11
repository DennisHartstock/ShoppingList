package com.example.shoppingList.presentation

import android.app.Application
import com.example.shoppingList.di.DaggerApplicationComponent

class ShopApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}