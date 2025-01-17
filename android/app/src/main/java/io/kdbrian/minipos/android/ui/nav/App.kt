package io.kdbrian.minipos.android.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kdbrian.minipos.android.features.pos.DashBoard
import io.kdbrian.minipos.android.features.products.ProductListing
import io.kdbrian.minipos.android.features.transactions.TransactionsListing
import io.kdbrian.minipos.android.presentation.viewmodel.ProductsViewModel
import io.kdbrian.minipos.android.presentation.viewmodel.TransactionsViewModel
import io.kdbrian.minipos.android.util.Resource


@Composable
fun App(
    modifier: Modifier = Modifier.padding(12.dp),
    navController: NavHostController = rememberNavController(),
    productsViewModel: ProductsViewModel,
    transactionsViewModel: TransactionsViewModel,
) {
    val productResource by productsViewModel.allProducts.collectAsState(initial = Resource.Nothing())
    val transactionResource by transactionsViewModel.allTransactions.collectAsState(initial = Resource.Nothing())

    val onTransactionsRefresh : () -> Unit = {
        transactionsViewModel.getTransactions()
    }


    NavHost(navController = navController, startDestination = Route.DASHBOARD) {

        composable<Route.DASHBOARD> {
            DashBoard(
                modifier = modifier,
                productResource = productResource,
                transactionsResource = transactionResource,
            )
        }

        composable<Route.ALLPRODUCTS> {
            ProductListing(
                modifier = modifier,
                allProductsResource = productResource
            )
        }

        composable<Route.ALLTRANSACTIONS> {
            TransactionsListing(
                modifier = modifier,
                transactionResource = transactionResource,
                onRefresh = onTransactionsRefresh
            )
        }

    }

}