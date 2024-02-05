package com.github.sandeepyohans.shoppingapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.github.sandeepyohans.shoppingapp.data.model.Product
import com.github.sandeepyohans.shoppingapp.data.paging.ProductDataSource
import com.github.sandeepyohans.shoppingapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    val products = Pager(
        PagingConfig(pageSize = 10)
    ) {
        ProductDataSource(productRepository)
    }.flow.cachedIn(viewModelScope)

}