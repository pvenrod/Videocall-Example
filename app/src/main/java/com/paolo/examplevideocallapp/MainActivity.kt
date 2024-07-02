package com.paolo.examplevideocallapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paolo.examplevideocallapp.misc.AdsManager
import com.paolo.examplevideocallapp.ui.CallScreenContent
import com.paolo.examplevideocallapp.ui.ChatScreenContent
import com.paolo.examplevideocallapp.ui.HomeScreenContent
import com.paolo.examplevideocallapp.ui.dialog.NoNetworkDialog
import com.paolo.examplevideocallapp.ui.theme.VideollamadaLeoMessiTheme
import com.paolo.examplevideocallapp.ui.widget.StatusBarColor
import com.paolo.examplevideocallapp.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val ROUTE_HOME = "home"
const val ROUTE_CHAT = "chat"
const val ROUTE_VIDEOCALL = "videocall"
const val ROUTE_CALL = "call"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var adsManager: AdsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adsManager.run {
            initialize(this@MainActivity)
            loadInterstitial(this@MainActivity)
        }
        setContent {
            StatusBarColor(Color.White)
            VideollamadaLeoMessiTheme {
                MainActivityScreen()
            }
        }
    }
}

@Composable
fun MainActivityScreen(
    viewModel: BaseViewModel = hiltViewModel(),
) {
    MainActivityScreenContent()
    if (viewModel.uiState.collectAsState().value.showNoNetworkDialog) {
        NoNetworkDialog()
    }
}

@Composable
fun MainActivityScreenContent() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME,
    ) {
        composable(ROUTE_HOME) {
            HomeScreenContent(navController = navController)
        }
        composable(ROUTE_CHAT){
            BackHandler(true) { /* no-op */ }
            ChatScreenContent(navController = navController)
        }
        composable(ROUTE_VIDEOCALL){
            BackHandler(true) { /* no-op */ }
            CallScreenContent(navController = navController, isVideocall = true)
        }
        composable(ROUTE_CALL){
            BackHandler(true) { /* no-op */ }
            CallScreenContent(navController = navController, isVideocall = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityScreenPreview() {
    MainActivityScreenContent()
}