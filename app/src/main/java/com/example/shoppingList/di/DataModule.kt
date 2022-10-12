package com.example.shoppingList.di

import android.app.Application
import com.example.shoppingList.data.AppDatabase
import com.example.shoppingList.data.ShopListDao
import com.example.shoppingList.data.ShopListRepositoryImpl
import com.example.shoppingList.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}