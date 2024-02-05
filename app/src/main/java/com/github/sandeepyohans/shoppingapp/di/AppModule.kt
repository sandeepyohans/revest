package com.github.sandeepyohans.shoppingapp.di

import com.github.sandeepyohans.shoppingapp.data.api.ApiService
import com.github.sandeepyohans.shoppingapp.data.api.RetrofitBuilder
import com.github.sandeepyohans.shoppingapp.data.repository.ProductRepository
import com.github.sandeepyohans.shoppingapp.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providesApiService() :ApiService = RetrofitBuilder.apiService

    @Provides
    fun providesProductRepository(apiService: ApiService): ProductRepository = ProductRepositoryImpl(apiService)
}