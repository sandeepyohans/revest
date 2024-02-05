@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.sandeepyohans.shoppingapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.sandeepyohans.shoppingapp.ui.theme.ShoppingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : ComponentActivity() {

    private var productPrice: Int = 0
    private lateinit var productCategory: String
    private var productRating: Double = 0.0
    private lateinit var productDesc: String
    private lateinit var productImages: Array<String>
    private lateinit var productTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            productTitle = it.getStringExtra("product_title") ?: ""
            productImages = it.getStringArrayExtra("product_images") as Array<String>
            productDesc = it.getStringExtra("product_desc") ?: ""
            productRating = it.getDoubleExtra("product_rating", 5.0)
            productCategory = it.getStringExtra("product_cat") ?: ""
            productPrice = it.getIntExtra("product_price", 0)
        }

        setContent {
            ShoppingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductDetails(
                        productTitle,
                        productImages,
                        productDesc,
                        productRating,
                        productCategory,
                        productPrice
                    )
                }
            }
        }
    }
}


@Composable
fun ProductDetails(
    productTitle: String,
    productImages: Array<String>,
    productDesc: String,
    productRating: Double,
    productCategory: String,
    productPrice: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        ProductImageSlider(images = productImages)

        Text(
            text = productTitle,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Category: $productCategory",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Product description: $productDesc",
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 3
        )
        Text(
            text = "User ratings: $productRating",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "By now at: $ $productPrice",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImageSlider(images: Array<String>) {
    val pagerState = rememberPagerState(
        initialPage = 1,
        initialPageOffsetFraction = 0f
    ) {
        images.size
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier,
        pageSpacing = 5.dp,
        userScrollEnabled = true,
        reverseLayout = false,
        contentPadding = PaddingValues(horizontal = 10.dp),
        beyondBoundsPageCount = 0,
        pageSize = PageSize.Fill,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        key = null,
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
            Orientation.Horizontal
        ),
        pageContent = {

            AsyncImage(
                model = images[it],
                contentDescription = null
            )
        }
    )
}
