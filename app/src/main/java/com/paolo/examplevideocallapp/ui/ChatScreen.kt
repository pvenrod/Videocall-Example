package com.paolo.examplevideocallapp.ui

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.model.CharacterDTO
import com.paolo.examplevideocallapp.data.model.MessageDTO
import com.paolo.examplevideocallapp.ui.widget.chat.AvailableMessages
import com.paolo.examplevideocallapp.ui.widget.chat.ChatToolbar
import com.paolo.examplevideocallapp.ui.widget.chat.Message
import com.paolo.examplevideocallapp.viewmodel.ChatScreenViewModel
import com.paolo.examplevideocallapp.viewmodel.ChatUiState
import kotlinx.coroutines.launch

@Composable
fun ChatScreenContent(
    viewModel: ChatScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val activity = context as Activity
    ChatScreen(
        state,
        { viewModel.onBackClick(activity, navController) },
        { viewModel.onVideocallClick(activity, navController) },
        { viewModel.onCallClick(activity, navController) },
        { viewModel.onMessageSent(activity, it) },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    state: ChatUiState,
    onBackClick: (() -> Unit)? = null,
    onVideocallClick: (() -> Unit)? = null,
    onCallClick: (() -> Unit)? = null,
    onMessageSent: ((msg: MessageDTO) -> Unit)? = null,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.backgroundchat),
                    contentScale = ContentScale.FillBounds
                ),
            horizontalAlignment = CenterHorizontally,
        ) {
            ChatToolbar(
                state = state,
                onBackClick = onBackClick,
                onVideocallClick = onVideocallClick,
                onCallClick = onCallClick,
            )
            Spacer(modifier = Modifier.height(14.dp))

            val lazyColumnListState = rememberLazyListState()
            val corroutineScope = rememberCoroutineScope()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .weight(1f, true),
                state = lazyColumnListState
            ) {
                val messages = state.conversation

                corroutineScope.launch {
                    lazyColumnListState.scrollToItem(messages.size)
                }
                items(messages.size) { index ->
                    Message(
                        modifier = Modifier.animateItemPlacement(),
                        messages[index],
                    )
                    if (index < messages.size - 1) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            AvailableMessages(
                state = state,
                onMessageSent = onMessageSent,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(
        ChatUiState(
            AppConfigurationDTO(
                CharacterDTO.DUMMY.copy(
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
        ),
    )
}