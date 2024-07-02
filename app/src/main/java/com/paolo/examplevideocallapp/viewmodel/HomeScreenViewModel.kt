package com.paolo.examplevideocallapp.viewmodel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.paolo.examplevideocallapp.ROUTE_CALL
import com.paolo.examplevideocallapp.ROUTE_CHAT
import com.paolo.examplevideocallapp.ROUTE_VIDEOCALL
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.model.PaoloAppDTO
import com.paolo.examplevideocallapp.data.repository.AppRepository
import com.paolo.examplevideocallapp.misc.AdsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val adsManager: AdsManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        retrieveAppInfo()
    }

    private fun retrieveAppInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                appRepository.getAppConfiguration()
            }.onSuccess { appConfiguration ->
                _uiState.value = _uiState.value.copy(
                    configuration = appConfiguration,
                    showLoading = false,
                )
                if (appConfiguration.showMoreApps == true) {
                    retrieveMoreApps()
                }
            }.onFailure {
                it.printStackTrace()
                delay(5000L)
                retrieveAppInfo()
            }
        }
    }

    private fun retrieveMoreApps() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                appRepository.getMoreApps()
            }.onSuccess { moreApps ->
                _uiState.value = _uiState.value.copy(
                    moreAppsList = moreApps,
                )
            }
        }
    }

    fun onChatClick(
        activity: Activity,
        navController: NavController,
    ) {
        adsManager.showInterstitial(activity) {
            navController.navigate(ROUTE_CHAT)
        }
    }

    fun onVideocallClick(
        activity: Activity,
        navController: NavController,
    ) {
        adsManager.showInterstitial(activity) {
            navController.navigate(ROUTE_VIDEOCALL)
        }
    }

    fun onCallClick(
        activity: Activity,
        navController: NavController,
    ) {
        adsManager.showInterstitial(activity) {
            navController.navigate(ROUTE_CALL)
        }
    }

    fun onAppClick(activity: Activity, app: PaoloAppDTO) {
        adsManager.showInterstitial(activity) {
            try {
                startActivity(
                    activity,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(app.storeUrl)
                    ),
                    bundleOf(),
                )
            } catch (e: Exception) {
                Toast
                    .makeText(activity, "Error. Inténtalo de nuevo más tarde", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

data class HomeUiState(
    val configuration: AppConfigurationDTO? = AppConfigurationDTO.DEFAULT,
    val showLoading: Boolean = true,
    val showError: Boolean = false,
    val moreAppsList: List<PaoloAppDTO> = emptyList(),
)