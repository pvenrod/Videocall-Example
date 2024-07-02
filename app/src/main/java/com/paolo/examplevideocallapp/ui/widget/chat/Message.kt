package com.paolo.examplevideocallapp.ui.widget.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paolo.examplevideocallapp.data.model.MessageType
import com.paolo.examplevideocallapp.ui.theme.AppColors
import com.paolo.examplevideocallapp.ui.widget.common.WrapTextContent

@Composable
fun Message(
    modifier: Modifier = Modifier,
    message: Pair<MessageType, String>,
) {
    val messageColor = if (message.first == MessageType.REAL_USER) {
        AppColors.MessageSent
    } else {
        Color.White
    }
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        if (message.isFromRealUser()) {
            Spacer(modifier = Modifier.weight(1f, true))
        }
        Spacer(modifier = Modifier.width(10.dp))
        if (!message.isFromRealUser()) {
            MessageCorner(message, messageColor)
        }
        WrapTextContent(
            modifier = Modifier
                .widthIn(0.dp, 250.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(messageColor)
                .padding(16.dp, 14.dp),
            text = message.second,
        )
        if (message.isFromRealUser()) {
            MessageCorner(message, messageColor)
        }
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun MessageCorner(
    message: Pair<MessageType, String>,
    messageColor: Color,
) {
    Box(
        modifier = Modifier
            .offset(((-10).takeIf { message.isFromRealUser() } ?: 10).dp, 4.dp)
            .width(13.dp)
            .height(13.dp)
            .rotate(45f)
            .background(messageColor)
    )
}

fun Pair<MessageType, String>.isFromRealUser() = first == MessageType.REAL_USER