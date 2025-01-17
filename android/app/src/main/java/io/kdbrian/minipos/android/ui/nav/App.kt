package io.kdbrian.minipos.android.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.kdbrian.minipos.android.features.pos.DashBoard
import io.kdbrian.minipos.android.features.products.ProductListing
import io.kdbrian.minipos.android.features.transactions.TransactionsListing
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetAllTransactionsQuery


@Composable
fun App(
    modifier: Modifier = Modifier,
    productResource: Resource<GetAllProductsQuery.Data>,
    transactionResource: Resource<GetAllTransactionsQuery.Data>,
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.DASHBOARD) {

        composable<Route.DASHBOARD> {
            DashBoard(
                modifier = modifier,
                productResource = productResource
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
                transactionResource = transactionResource
            )
        }

    }

}