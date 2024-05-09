package com.example.moviedb.network

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkHandler(private val context: Context) {
//    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
//
//    fun isNetworkAvailable(): Flow<Boolean> {
//        val networkInfo = connectivityManager.activeNetwork ?: return false
//        val networkCapabilities = connectivityManager.getNetworkCapabilities(networkInfo) ?: return false
//
//        return when {
//            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> true
//            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow(false)
    val isConnected: Flow<Boolean> = _isConnected

    init {
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                _isConnected.value = true
            }

            override fun onLost(network: android.net.Network) {
                _isConnected.value = false
            }
        })
    }

    fun isNetworkAvailable(): Boolean {
        val networkInfo = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(networkInfo) ?: return false

        return when {
            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}