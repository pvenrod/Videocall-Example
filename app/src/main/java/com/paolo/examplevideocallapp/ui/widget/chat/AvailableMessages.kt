package com.paolo.examplevideocallapp.ui.widget.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paolo.examplevideocallapp.data.model.MessageDTO
import com.paolo.examplevideocallapp.misc.bounceClick
import com.paolo.examplevideocallapp.ui.theme.AppColors
import com.paolo.examplevideocallapp.ui.widget.common.WrapTextContent
import com.paolo.examplevideocallapp.viewmodel.ChatUiState

@Composable
fun AvailableMessages(
    state: ChatUiState,
    onMessageSent: ((msg: MessageDTO) -> Unit)?
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(AppColors.MessagesBackground),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val messages = state.configuration?.character?.conversation
        items(messages?.size ?: 0) { index ->
            messages?.get(index)?.let { safeMessage ->
                if (index == 0) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .widthIn(0.dp, 240.dp)
                        .border(
                            2.dp,
                            AppColors.ChatButton,
                            RoundedCornerShape(6.dp),
                        )
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                        .padding(16.dp, 2.dp)
                        .bounceClick { onMessageSent?.invoke(safeMessage) },
                    contentAlignment = Alignment.Center,
                ) {
                    WrapTextContent(
                        text = safeMessage.question.orEmpty(),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}