package io.kdbrian.minipos.android.features.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kdbrian.minipos.android.ui.composables.CenteredText
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.fragment.ProductBasicInfo

@Composable
fun ProductListing(
    modifier: Modifier = Modifier,
    allProductsResource: Resource<GetAllProductsQuery.Data> = Resource.Nothing(),
    onExpandProductBasicInfo: (ProductBasicInfo) -> Unit = { _ -> },
) {

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
                                ProductItemInListing(
                                    productInfo = info,
                                    onExpandProductBasicInfo = onExpandProductBasicInfo
                                )
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
                                    onExpandProductBasicInfo = onExpandProductBasicInfo,
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
