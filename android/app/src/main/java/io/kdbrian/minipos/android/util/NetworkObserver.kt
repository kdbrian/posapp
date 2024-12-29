package io.kdbrian.minipos.android.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import java.net.URL

class NetworkObserver(context: Context) : LiveData<Resource<Boolean>>() {

    @SuppressLint("ServiceCast")
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Timber.d("Network is available ${network.networkHandle}")
            postValue(Resource.Success(true)) // Network is available
        }

        override fun onLost(network: Network) {
            postValue(Resource.Success(false)) // Network is lost
        }
    }

    override fun onActive() {
        super.onActive()
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        if (capabilities != null) {
            checkInternetAccess()
        } else {
            postValue(Resource.Success(false)) // No active network
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun checkInternetAccess() {
        CoroutineScope(Dispatchers.IO).launch {
            val isReachable = try {
                val socket = Socket()
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500) // Ping Google's DNS
                socket.close()
                true
            } catch (e: Exception) {
                when (e) {
                    is ConnectException -> {
                        postValue(Resource.Success(false))
                    }

                    is SocketTimeoutException -> {
                        postValue(Resource.Success(false))
                    }
                }
                false
            }
            postValue(Resource.Success(isReachable))
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}