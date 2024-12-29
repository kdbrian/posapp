package io.kdbrian.minipos.android.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.kdbrian.minipos.android.presentation.viewmodel.PosViewModel
import io.kdbrian.minipos.android.presentation.viewmodel.TransactionsViewModel
import io.kdbrian.minipos.android.ui.composables.transactions.TopTransactionsList
import io.kdbrian.minipos.android.ui.plugins.products.ProductsStatsPlugins
import io.kdbrian.minipos.android.ui.screens.Portfolio
import io.kdbrian.minipos.android.ui.screens.ProductsScreen
import io.kdbrian.minipos.android.ui.screens.ViewSingleProduct
import io.kdbrian.minipos.android.util.Resource
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import src.main.graphql.GetAllProductsQuery
import timber.log.Timber

@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    posViewModel: PosViewModel,
    transactionsViewModel: TransactionsViewModel
) {

    val allProductsResource by posViewModel.products.collectAsState(initial = Resource.Loading())
    val allTransactionsResource by transactionsViewModel.allTransactions.collectAsState(initial = Resource.Loading())


    NavHost(navController = navController, startDestination = AppScreens.POS_HOME_SCREEN.route) {

        composable(route = AppScreens.POS_HOME_SCREEN.route) {
            //TODo:Fetch settings and load composables as per user settings e.g
            // for the search screen a use can set a defined they want then we display it and provide a way to change
            ProductsScreen(
                productsBanner = {
                    ProductsStatsPlugins.ProductsStatsBannerWithProductsTransactionsPieChart(
                        modifier = it,
                        allProductsResource = allProductsResource,
                        allTransactionsResource = allTransactionsResource
                    )
                },
                navController,
                modifier,
                resourceState = allProductsResource,
                plugins = {

                    when (allTransactionsResource) {
                        is Resource.Error -> {
                            Text(
                                text = allTransactionsResource.message.toString(),
                                fontSize = 24.sp,
                                modifier = modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                        is Resource.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }
                        is Resource.Success -> {
                            val allTransactions = allTransactionsResource.data?.getAllTransactions
                            if (allTransactions != null) {
                                if (allTransactions.isEmpty()) {
                                    Text(
                                        text = "No Transactions",
                                        fontSize = 24.sp,
                                        modifier = modifier.align(Alignment.CenterHorizontally)
                                    )
                                }else{

                                    TopTransactionsList(
                                        modifier = modifier.padding(12.dp),
                                        data = allTransactions
                                    )
                                }
                            }
                        }
                    }

                }
            )
        }

        composable(route = AppScreens.VIEW_ALL_PRODUCT.route) {
            Portfolio(modifier = modifier, allProductsResource, allTransactionsResource)
        }

        composable(route = "${AppScreens.VIEW_SEARCH_PRODUCT_RESULTS.route}/{searchQuery}") {
            Portfolio(modifier = modifier, allProductsResource, allTransactionsResource)
        }

        composable(
            route = "${AppScreens.VIEW_SINGLE_PRODUCT.route}/{productId}/{productData}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                },
                navArgument("productData") {
                    type = NavType.StringType
                }
            )
        ) {
            val productId = it.arguments?.getString("productId")
            val productData = it.arguments?.getString("productData").toString()
            Timber.d("viewing $productData")
//            val decodedProduct = Json{ignoreUnknownKeys = true}.decodeFromString<GetAllProductsQuery.GetProduct>(productData)
//            Timber.d("viewing ${decodedProduct.productId == productId} $decodedProduct")

            LaunchedEffect(Unit) {
                Timber.d("Product $productId")
                posViewModel.getProductWithId(productId.toString())
                Timber.d("Product $productId")
            }

            val product by posViewModel.selectedProductWithId.collectAsState(initial = Resource.Loading())

            Box(modifier = modifier.fillMaxSize()) {

                when (product) {
                    is Resource.Error -> {
                        Text(
                            text = product.message.toString(),
                            fontSize = 24.sp,
                            modifier = modifier.align(Alignment.Center)
                        )
                    }

                    is Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is Resource.Success -> {
                        ViewSingleProduct(
                            product = product.data,
                            onNavigateBack = { navController.popBackStack() },
                            modifier = modifier,
                            productId = productId.toString()
                        )
                    }
                }
            }

        }




        composable(route = AppScreens.PORTFOLIO.route) {
            Portfolio(modifier = modifier, allProductsResource, allTransactionsResource)
        }

    }

}