package com.github.sandeepyohans.shoppingapp.data.repository

import com.github.sandeepyohans.shoppingapp.data.api.ApiService
import com.github.sandeepyohans.shoppingapp.data.model.ProductResponse
import javax.inject.Inject

class ProductRepositoryImpl (
    private val apiService: ApiService
): ProductRepository {

    override suspend fun getProducts(limit: Int, skip: Int): ProductResponse {
        return apiService.getProducts(limit = limit, skip = skip)
    }
}