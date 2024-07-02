package com.paolo.examplevideocallapp.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.model.CharacterDTO
import com.paolo.examplevideocallapp.data.model.MessageDTO
import com.paolo.examplevideocallapp.ui.widget.call.VideocallWidget
import com.paolo.examplevideocallapp.ui.widget.call.VoicecallWidget
import com.paolo.examplevideocallapp.viewmodel.CallScreenViewModel
import com.paolo.examplevideocallapp.viewmodel.CallUiState

@Composable
fun CallScreenContent(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: CallScreenViewModel = hiltViewModel(),
    navController: NavController,
    isVideocall: Boolean,
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val activity = context as Activity
    viewModel.initialize(context, isVideocall)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                viewModel.onPause()
            } else if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onResume()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    CallScreen(
        state,
        isVideocall,
        { viewModel.onEndCallClick(activity, navController) },
        { viewModel.onStartCallClick() }
    )
}

@Composable
fun CallScreen(
    state: CallUiState,
    isVideocall: Boolean,
    onEndCallClick: (() -> Unit)? = null,
    onStartCallClick: (() -> Unit)? = null,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.backgroundvideocall),
                contentScale = ContentScale.FillBounds
            ),
    ) {
        if (isVideocall) {
            VideocallWidget(
                state = state,
                onStartCallClick = onStartCallClick,
                onEndCallClick = onEndCallClick,
            )
        } else {
            VoicecallWidget(
                state = state,
                onStartCallClick = onStartCallClick,
                onEndCallClick = onEndCallClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallScreenPreview() {
    CallScreen(
        state = CallUiState(
            AppConfigurationDTO(
                CharacterDTO.DUMMY.copy(
                    name = "Character",
                    phoneNumber = "+34 65588844465",
                    conversation = listOf(
                        MessageDTO("answer 1", listOf()),
                        MessageDTO("answer 1 extremly large hey my frieeeend", listOf())
                    )
                ),
                true,
                false,
                false,
                1000L,
            ),
            callHasStarted = true,
        ),
        isVideocall = true,
    )
}