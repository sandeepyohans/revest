package com.github.sandeepyohans.shoppingapp.data.repository

import com.github.sandeepyohans.shoppingapp.data.model.ProductResponse

interface ProductRepository {
    suspend fun getProducts(
        limit: Int,
        skip: Int
    ) :ProductResponse
}