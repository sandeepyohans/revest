package com.github.sandeepyohans.shoppingapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.github.sandeepyohans.shoppingapp.R
import com.github.sandeepyohans.shoppingapp.data.model.Product
import com.github.sandeepyohans.shoppingapp.ui.theme.ShoppingAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                window.statusBarColor = MaterialTheme.colorScheme.primary.toArgb()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductListScreen(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun ProductListScreen(mainViewModel: MainViewModel) {

    val productList = mainViewModel.products.collectAsLazyPagingItems()

    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            },
        contentPadding = PaddingValues(8.dp)
    ) {
        items(productList.itemCount) { index ->
            productList[index]?.let { product ->
                ProductItem(
                    product,
                    onClick = {
                        val intent = Intent(context, ProductDetailsActivity::class.java)
                        intent.putExtra("product_images", product.images.toTypedArray())
                        intent.putExtra("product_title", product.title)
                        intent.putExtra("product_desc", product.description)
                        intent.putExtra("product_rating", product.rating)
                        intent.putExtra("product_cat", product.category)
                        intent.putExtra("product_price", product.price)

                        context.startActivity(intent)
                    }
                )
            }
        }

//        when(productList.loadState.append) {
//            is LoadState.Error -> {
//                ShowError()
//            }
//            LoadState.Loading -> {
//                ShowLoading()
//            }
//            is LoadState.NotLoading -> Unit
//        }
    }
}

@Composable
fun ShowError(message: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp),
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun ShowLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .padding(8.dp),
            strokeWidth = 5.dp
        )
    }
}


@Composable
fun ProductItem(product: Product, onClick:(p:Product)->Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick(product)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(130.dp)
                    .padding(4.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                model = product.thumbnail,
                contentDescription = ContextCompat.getString(context, R.string.picture_of_product)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.description
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductPreview(){
//    ShowError(message = "Error message")
    ShowLoading()
}