package com.example.composeapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternetUtils @Inject constructor(context: Context) {

    var mContext: Context = context

    fun checkForInternet(): Boolean {
        println("InternetUtils>>>checkForInternet>>>Entry")
        // register activity with the connectivity manager service
        val connectivityManager = mContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        println("InternetUtils>>>checkForInternet>>>Connectivity manager instance created")
        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            println("InternetUtils>>>checkForInternet>>> Android M condition success")
            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false
            println("InternetUtils>>>checkForInternet>>>network")
            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            println("InternetUtils>>>checkForInternet>>>activeNetwork")
            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                //activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                //activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true

                // else return false
                else -> false
            }
        } else {
            println("InternetUtils>>>checkForInternet>>>android M condition fail")
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}