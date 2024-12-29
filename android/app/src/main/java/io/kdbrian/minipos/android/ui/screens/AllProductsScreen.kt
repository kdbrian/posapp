package io.kdbrian.minipos.android.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.LocalFontFamily
import io.kdbrian.minipos.android.ui.composables.ProductCard
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.pally
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllProductsQuery
import timber.log.Timber


@Composable
fun AllProductsScreen(
    limit: Int = 1,
    productCategories: @Composable () -> Unit = {},
    searchBar: @Composable (Modifier, onSearch: (String) -> Unit) -> Unit,
    productsResource: Resource<GetAllProductsQuery.Data>,
    modifier: Modifier,
    viewAllProductsOnclick: () -> Unit,
    productOnclick: (GetAllProductsQuery.GetProduct) -> Unit,
    onsearch: (String) -> Unit = {}
) {

    var showBanner by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        searchBar(Modifier, onsearch)

        when (productsResource) {
            is Resource.Error -> {
                Text(
                    text = productsResource.message.toString(),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.error,
                    ),
                )
            }

            is Resource.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Fetching products...")
                }
            }

            is Resource.Success -> {


                //injecting a composable flow
                productCategories()

                productsResource.data?.getProducts?.let { products ->
                    if (products.isEmpty()) {
                        Text(
                            text = "No products found",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Start)
                        ) {

                            AnimatedVisibility(
                                showBanner,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Surface(
                                    color = Color(0xFF08A045),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Fetched ${products.size} items",
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontFamily = pally
                                        )

                                        IconButton(
                                            onClick = {
                                                showBanner = false
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            }

                            (0..limit).forEach { index ->
                                val product = products.getOrNull(index)
                                ProductCard(product = product!!, onSelect = {
                                    productOnclick(it)
                                })
                            }
                        }
                    }
                }
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Products",
                fontSize = 24.sp,
                fontFamily = LocalFontFamily.current
            )

            TextButton(onClick = viewAllProductsOnclick) {
                Text(text = "View All")
            }
        }

    }

}


@Preview
@Composable
private fun AllProductsScreenPrev() {
    var searchQuery by remember {
        mutableStateOf("")
    }
    MiniposTheme {
//        AllProductsScreen(
//            productsResource = Resource.Loading(),
//            modifier = Modifier,
//            productOnclick = {},
//            onsearch =
//        )
    }
}