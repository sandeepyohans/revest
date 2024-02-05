package com.github.sandeepyohans.shoppingapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.sandeepyohans.shoppingapp.data.model.Product
import com.github.sandeepyohans.shoppingapp.data.repository.ProductRepository

class ProductDataSource(
    private val productRepository: ProductRepository
): PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try{
            val skip = params.key ?: 10
            val response = productRepository.getProducts(limit = 10, skip = skip)

            LoadResult.Page(
                data = response.products,
                prevKey =  if(skip == 10) null else skip.plus(10),
                nextKey = if(response.products.isEmpty()) null else skip.plus(10)
            )
        } catch(ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}