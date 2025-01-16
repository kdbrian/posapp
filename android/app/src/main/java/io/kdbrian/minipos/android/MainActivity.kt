package io.kdbrian.minipos.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import io.kdbrian.minipos.android.BuildConfig.ngrokHost
import io.kdbrian.minipos.android.presentation.viewmodel.PosViewModel
import io.kdbrian.minipos.android.presentation.viewmodel.PosViewModelProvider
import io.kdbrian.minipos.android.presentation.viewmodel.TransactionsViewModel
import io.kdbrian.minipos.android.presentation.viewmodel.TransactionsViewModelProvider
import io.kdbrian.minipos.android.ui.nav.App
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.supreme
import io.kdbrian.minipos.android.util.NetworkObserver
import io.kdbrian.minipos.android.util.Resource
import io.kdbrian.minipos.android.util.ServerStatus
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import timber.log.Timber


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkObserver1 = NetworkObserver(this)
        setContent {
            val emulatorLocalhost = BuildConfig.emulatorLocalhost
            val ngrokHost = BuildConfig.ngrokHost
            val pcLocalhost = BuildConfig.pcLocalhost

            Timber.d("Local $emulatorLocalhost $ngrokHost $pcLocalhost")
            val actuatorHealthUrl = "${ngrokHost}/actuator/health"
            val networkState by networkObserver1.observeAsState(initial = Resource.Loading())

            MiniposTheme {
                MainScreen(
                    actuatorHealthUrl,
                    networkState = networkState
                )

            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    MiniposTheme {
        MainScreen(
            actuatorHealthUrl = ""
        )
    }
}

val LocalFontFamily = staticCompositionLocalOf { supreme }
val LocalApolloClient = staticCompositionLocalOf {
    ApolloClient.Builder()
        .serverUrl("$ngrokHost/graphql")
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor {
                    //check for connection
                    it.proceed(it.request())
                }
                .build()
        )
        .build()
}


@Composable
private fun MainScreen(
    actuatorHealthUrl: String,
    networkState: Resource<Boolean> = Resource.Loading(),
) {

    var checkingConnection by remember { mutableStateOf(false) }
    var actuatorRes by remember { mutableStateOf("") }
    var checkingConnection1 = checkingConnection
    var actuatorRes1 = actuatorRes

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LocalViewModelStoreOwner.current?.let { storeOwner ->

        val posViewModel: PosViewModel = viewModel<PosViewModel>(
            viewModelStoreOwner = storeOwner,
            factory = PosViewModelProvider(LocalApolloClient.current),
            key = "PosViewModel"
        )

        val transactionsViewModel: TransactionsViewModel = viewModel<TransactionsViewModel>(
            viewModelStoreOwner = storeOwner,
            factory = TransactionsViewModelProvider(LocalApolloClient.current),
            key = "TransactionsViewModel"
        )

        CompositionLocalProvider(
            LocalApolloClient provides LocalApolloClient.current,
            LocalFontFamily provides LocalFontFamily.current
        ) {

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                LaunchedEffect(checkingConnection1) {
                    if (!checkingConnection1) {
                        val health = Json.decodeFromString(
                            ServerStatus.serializer(),
                            HttpClient().get(actuatorHealthUrl).bodyAsText()
                        )
                        actuatorRes1 = health.status
                    }

                }

                when (networkState) {
                    is Resource.Error -> {
                        actuatorRes1 = "No internet connection"
                    }

                    is Resource.Loading -> {
                        actuatorRes1 = "Checking internet connection"
                        checkingConnection1 = true
                    }

                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            delay(200)
                            checkingConnection1 = false
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .systemBarsPadding(),
                ) {

                    App(
                        modifier = Modifier
                            .fillMaxSize(),
                        navController = navController,
                        posViewModel = posViewModel,
                        transactionsViewModel = transactionsViewModel
                    )

                }

            }


        }
    }
}

@Composable
fun NetworkTab(
    name: String,
    modifier: Modifier = Modifier,
    networkState: Resource<Boolean> = Resource.Loading()
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        if (networkState is Resource.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp)
            )
        } else
            Text(
                text = "$name!",
                modifier = modifier
                    .padding(8.dp)
            )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MiniposTheme {
        NetworkTab("Android")
    }
}