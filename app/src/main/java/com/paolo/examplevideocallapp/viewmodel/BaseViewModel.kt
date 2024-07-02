package com.paolo.examplevideocallapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paolo.examplevideocallapp.data.api.NetworkCheckApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIME_BETWEEN_INTERNET_CHECKS = 5000L

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val networkCheckApi: NetworkCheckApi,
): ViewModel() {

    private val _uiState = MutableStateFlow(BaseUiState())
    val uiState: StateFlow<BaseUiState> = _uiState.asStateFlow()

    init {
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                kotlin.runCatching {
                    networkCheckApi.pingGoogle()
                }.onFailure {
                    setInternetConnection(false)
                }.onSuccess {
                    setInternetConnection(true)
                }
                delay(TIME_BETWEEN_INTERNET_CHECKS)
            }
        }
    }

    private fun setInternetConnection(isAvailable: Boolean) {
        _uiState.value = _uiState.value.copy(
            showNoNetworkDialog = !isAvailable,
        )
    }
}

data class BaseUiState(
    val showNoNetworkDialog: Boolean = false,
)