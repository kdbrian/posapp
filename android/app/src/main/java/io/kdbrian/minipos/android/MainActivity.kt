package io.kdbrian.minipos.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import io.kdbrian.minipos.android.BuildConfig.ngrokHost
import io.kdbrian.minipos.android.BuildConfig.pcLocalhost
import io.kdbrian.minipos.android.presentation.viewmodel.ProductsViewModel
import io.kdbrian.minipos.android.presentation.viewmodel.TransactionsViewModel
import io.kdbrian.minipos.android.ui.nav.App
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.TextLocals.LocalDefaultTextStyle
import io.kdbrian.minipos.android.ui.theme.TextLocals.defaultTextStyle
import io.kdbrian.minipos.android.ui.theme.supreme
import io.kdbrian.minipos.android.util.NetworkObserver
import io.kdbrian.minipos.android.util.Resource
import okhttp3.OkHttpClient
import timber.log.Timber


val apolloClient = ApolloClient.Builder()
    .serverUrl("${pcLocalhost}graphql")
    .okHttpClient(
        OkHttpClient.Builder()
            .addInterceptor {
                Timber.d("for [${it.request().method}] : ${it.request().url}")
                //check for connection
                it.proceed(it.request())
            }
            .build()
    )
    .build()

val LocalFontFamily = staticCompositionLocalOf { supreme }
val LocalApolloClient = staticCompositionLocalOf { apolloClient }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkObserver1 = NetworkObserver(this)

        setContent {
            val emulatorLocalhost = BuildConfig.emulatorLocalhost
            val ngrokHost = ngrokHost
            val pcLocalhost = pcLocalhost

            Timber.d("Local $emulatorLocalhost $ngrokHost $pcLocalhost")
            val actuatorHealthUrl = "${ngrokHost}/actuator/health"
            val networkState by networkObserver1.observeAsState(initial = Resource.Loading())

            LocalViewModelStoreOwner.current?.let { viewModelStoreOwner ->

                CompositionLocalProvider(
                    LocalFontFamily provides supreme,
                    LocalApolloClient provides apolloClient,
                    LocalDefaultTextStyle provides defaultTextStyle
                ) {

                    val transactionsViewModel: TransactionsViewModel = viewModel(
                        viewModelStoreOwner = viewModelStoreOwner,
                        key = TransactionsViewModel::class.simpleName,
                        factory = TransactionsViewModel.Factory(LocalApolloClient.current),
                    )

                    val productsViewModel: ProductsViewModel = viewModel(
                        viewModelStoreOwner = viewModelStoreOwner,
                        key = TransactionsViewModel::class.simpleName,
                        factory = ProductsViewModel.Factory(LocalApolloClient.current),
                    )

                    MiniposTheme {

                        //continue from transactions -> design, implement, test
                        val allProductsResource by productsViewModel.allProducts.collectAsState(
                            initial = Resource.Nothing()
                        )
                        val allTransactionsResource by transactionsViewModel.allTransactions.collectAsState(initial = Resource.Nothing())

                        Scaffold(
                            bottomBar = {


                            }
                        ) { paddingValues ->
                            App(
                                modifier = Modifier.padding(paddingValues),
                                productResource = allProductsResource,
                                transactionResource = allTransactionsResource
                            )
                        }

                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiniposTheme {
    }
}