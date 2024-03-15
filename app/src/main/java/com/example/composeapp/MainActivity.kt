package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.ui.navigation.SetupNavGraph
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.utils.AdmobUtils
import com.example.composeapp.utils.FireStoreDataHelper
import com.example.composeapp.utils.FirebaseAuthHelper
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController
    @Inject
    lateinit var fireStoreDataHelper: FireStoreDataHelper
    @Inject
    lateinit var firebaseAuthHelper: FirebaseAuthHelper
    @Inject
    lateinit var admobUtils: AdmobUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this, OnInitializationCompleteListener {

        })
        setContent {
            ComposeAppTheme {
                navHostController = rememberNavController()
                SetupNavGraph(navHostController = navHostController)
            }
        }
        admobUtils.loadInterstitial(this)
    }

    override fun onDestroy() {
        admobUtils.removeInterstitial()
        super.onDestroy()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        //val navHostController = rememberNavController()
        //HomeRoute(navHostController = navHostController)
    }
}