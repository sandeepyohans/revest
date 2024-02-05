package com.github.sandeepyohans.shoppingapp.data.api

import com.github.sandeepyohans.shoppingapp.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int, @Query("skip") skip: Int): ProductResponse

}