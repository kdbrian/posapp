package io.kdbrian.minipos.android.features.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.ui.composables.CenteredText
import io.kdbrian.minipos.android.ui.theme.TextLocals.LocalDefaultTextStyle
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllProductsQuery

@Composable
fun ProductListing(
    modifier: Modifier = Modifier,
    allProductsResource: Resource<GetAllProductsQuery.Data> = Resource.Nothing(),
){

    when (allProductsResource) {
        is Resource.Error -> {
            CenteredText(message = allProductsResource.message.toString())
        }

        is Resource.Loading -> {
            //todo : implement loading screen
            CenteredText(message = "Fetching products...")
        }

        is Resource.Nothing -> CenteredText(message = "Nothing here.")

        is Resource.Success -> {
            allProductsResource.data?.getProducts?.let { getProducts ->

                if (getProducts.isEmpty()) {
                    CenteredText(message = "Add a product first")
                } else {
                    if (getProducts.size > 150) {
                        val productInfos = getProducts.map { it!!.productBasicInfo }
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(productInfos) { info ->
                                ProductItemInListing(productInfo = info)
                            }
                        }
                    } else {
                        val verticalScrollState = rememberScrollState()
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .verticalScroll(verticalScrollState)
                        ) {
                            getProducts.map { it!!.productBasicInfo }.forEach { product ->
                                ProductItemInListing(
                                    productInfo = product,
                                    modifier = Modifier
                                        .padding(4.dp)
                                )
                            }
                        }
                    }

                }

            }
        }
    }


}
