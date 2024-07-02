package com.paolo.examplevideocallapp.viewmodel

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.repository.AppRepository
import com.paolo.examplevideocallapp.misc.AdsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CallScreenViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val adsManager: AdsManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CallUiState())
    val uiState: StateFlow<CallUiState> = _uiState.asStateFlow()

    private var callMusic: MediaPlayer? = null
    private var voiceSound: MediaPlayer? = null
    private var hangoutSound: MediaPlayer? = null
    private var wasPlayingCallMusic = false
    private var wasPlayingVoiceSound = false
    private var isVideocall = false
    private var isInitialized = false
    private var callMinutes = 0
    private var callSeconds = 0

    fun initialize(context: Context, isVideocall: Boolean) {
        if (!isInitialized) {
            retrieveAppInfo()
            this.isVideocall = isVideocall
            callMusic = MediaPlayer.create(context, R.raw.callmusic).apply {
                isLooping = true
                startCallMusic()
            }
            voiceSound = MediaPlayer.create(context, R.raw.voicesound).apply {
                isLooping = true
            }
            hangoutSound = MediaPlayer.create(context, R.raw.hangoutsound)
            isInitialized = true
        }
    }

    fun onEndCallClick(activity: Activity, navController: NavController) {
        stopCallMusic()
        _uiState.value = _uiState.value.copy(
            callHasEnded = true,
        )
        viewModelScope.launch {
            hangoutSound?.apply {
                if (!isPlaying) {
                    start()
                }
            }
            withContext(Dispatchers.Main) {
                delay(500L)
            }
            adsManager.showInterstitial(activity) {
                navController.popBackStack()
            }
        }
    }

    fun onStartCallClick() {
        stopCallMusic()
        _uiState.value = _uiState.value.copy(
            callHasStarted = true,
            remainingTime = getRemainingTime(),
        )
        if (!isVideocall) {
            if (voiceSound?.isPlaying == false) {
                voiceSound?.start()
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(1000)
                if (callSeconds < 59) {
                    callSeconds++
                } else {
                    callSeconds = 0
                    callMinutes++
                }
                _uiState.value = _uiState.value.copy(
                    remainingTime = getRemainingTime(),
                )
            }
        }
    }

    fun onPause() {
        _uiState.value = _uiState.value.copy(
            pauseVideocall = true,
        )
        voiceSound?.apply {
            if (isPlaying) {
                pause()
                wasPlayingVoiceSound = true
            }
        }
        callMusic?.apply {
            if (isPlaying) {
                pause()
                wasPlayingCallMusic = true
            }
        }
    }

    fun onResume() {
        _uiState.value = _uiState.value.copy(
            pauseVideocall = false,
        )
        if (wasPlayingVoiceSound && voiceSound?.isPlaying == false) {
            voiceSound?.start()
        }
        if (wasPlayingCallMusic && callMusic?.isPlaying == false) {
            callMusic?.start()
        }
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

    private fun startCallMusic() {
        callMusic?.apply {
            if (!isPlaying) {
                start()
            }
        }
        wasPlayingCallMusic = true
    }

    private fun stopCallMusic() {
        callMusic?.apply {
            if (isPlaying) {
                stop()
            }
        }
        wasPlayingCallMusic = false
    }

    private fun getRemainingTime(): String {
        var minutes = callMinutes.toString()
        var seconds = callSeconds.toString()

        if (callMinutes < 10) {
            minutes = "0$minutes"
        }
        if (callSeconds < 10) {
            seconds = "0$seconds"
        }
        return "$minutes:$seconds"
    }
}

data class CallUiState(
    val configuration: AppConfigurationDTO? = AppConfigurationDTO.DEFAULT,
    val callHasStarted: Boolean = false,
    val callHasEnded: Boolean = false,
    val pauseVideocall: Boolean = false,
    val remainingTime: String = "",
)