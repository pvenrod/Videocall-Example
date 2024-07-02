package com.paolo.examplevideocallapp.viewmodel

import android.app.Activity
import android.media.MediaPlayer
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.ROUTE_CALL
import com.paolo.examplevideocallapp.ROUTE_HOME
import com.paolo.examplevideocallapp.ROUTE_VIDEOCALL
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.model.MessageDTO
import com.paolo.examplevideocallapp.data.model.MessageType
import com.paolo.examplevideocallapp.data.repository.AppRepository
import com.paolo.examplevideocallapp.misc.AdsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val adsManager: AdsManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var messageSentSound: MediaPlayer? = null
    private var messageReceivedSound: MediaPlayer? = null
    private var sendingMessage = false

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
                )
            }.onFailure {
                delay(5000L)
                retrieveAppInfo()
            }
        }
    }

    fun onBackClick(
        activity: Activity,
        navController: NavController,
    ) {
        adsManager.showInterstitial(activity) {
            navController.popBackStack(ROUTE_HOME, false)
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

    fun onMessageSent(activity: Activity, msg: MessageDTO) {
        if (messageSentSound == null) {
            messageSentSound = MediaPlayer.create(activity, R.raw.messagesent)
            messageReceivedSound = MediaPlayer.create(activity, R.raw.messagereceived)
        }
        if (!sendingMessage) {
            sendingMessage = true
            if (messageSentSound?.isPlaying == false) {
                messageSentSound?.start()
            }
            sendMessage(MessageType.REAL_USER, msg.question.orEmpty())
            adsManager.showInterstitial(activity) {
                viewModelScope.launch(Dispatchers.IO) {
                    delay(1000)
                    withContext(Dispatchers.Main) {
                        _uiState.value = _uiState.value.copy(
                            currentStatusText = R.string.status_writting,
                        )
                    }
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        if (messageReceivedSound?.isPlaying == false) {
                            messageReceivedSound?.start()
                        }
                        sendMessage(MessageType.FAKE_USER, msg.answers?.random().orEmpty())
                        _uiState.value = _uiState.value.copy(
                            currentStatusText = R.string.status_online,
                        )
                        sendingMessage = false
                    }
                }
            }
        }
    }

    private fun sendMessage(messageType: MessageType, msg: String) {
        _uiState.value = _uiState.value.copy(
            conversation = mutableListOf<Pair<MessageType, String>>().apply {
                addAll(_uiState.value.conversation)
                add(messageType to msg)
            }
        )
    }
}

data class ChatUiState(
    val configuration: AppConfigurationDTO? = AppConfigurationDTO.DEFAULT,
    @StringRes val currentStatusText: Int = R.string.status_online,
    val conversation: MutableList<Pair<MessageType, String>> = mutableListOf(),
)