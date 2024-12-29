package io.kdbrian.minipos.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.kdbrian.minipos.android.ui.composables.ProductsScreenBottomBar
import io.kdbrian.minipos.android.ui.composables.VerticallyStackedSearch
import io.kdbrian.minipos.android.ui.nav.AppScreens
import io.kdbrian.minipos.android.util.Resource
import kotlinx.coroutines.launch
import src.main.graphql.GetAllProductsQuery

@Composable
fun ProductsScreen(
    productsBanner: @Composable (Modifier) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    resourceState: Resource<GetAllProductsQuery.Data>,
    plugins: @Composable ColumnScope. () -> Unit
) {

    val scrollState = rememberScrollState()
    val moveToViewProduct: (GetAllProductsQuery.GetProduct) -> Unit = {
        navController.navigate(AppScreens.VIEW_SINGLE_PRODUCT.route + "/${it.productId}/${it}")
    }
    var searchQuery by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val productScreen = Modifier.padding(16.dp)

    Scaffold(
        bottomBar = {
            ProductsScreenBottomBar(
                navController = navController
            )
        },
        modifier = modifier.fillMaxSize()
    ) { padding ->

        Column(modifier = Modifier
            .padding(padding)
            .verticalScroll(scrollState)) {

            productsBanner(productScreen)

            AllProductsScreen(
                viewAllProductsOnclick = {

                },
                productsResource = resourceState,
                modifier = productScreen,
                productOnclick = moveToViewProduct,
                searchBar = { searchBarModifier, onSearch ->
                    VerticallyStackedSearch(
                        modifier = searchBarModifier,
                        searchQuery = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                        },
                        onSearch = onSearch,
                        onFilter = {
                            scope.launch {
                            }
                        }
                    )
                }
            )

            plugins()


        }
    }

}