package com.paolo.examplevideocallapp.ui.widget.call

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.misc.bounceClick

@Composable
fun VoiceCallFooter(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(330.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .padding(20.dp),
    ) {
        val iconSize = 40.dp
        Row(
            modifier = Modifier
                .height(iconSize * 1.8f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            VoiceCallFooterButton(
                icon = Icons.Filled.PlusOne,
                text = stringResource(id = R.string.add_call)
            )
            Spacer(modifier = Modifier.weight(1f))
            VoiceCallFooterButton(
                icon = Icons.Filled.Pause,
                text = stringResource(id = R.string.wait)
            )
            Spacer(modifier = Modifier.weight(1f))
            VoiceCallFooterButton(
                icon = Icons.Filled.Bluetooth,
                text = stringResource(id = R.string.bluetooth)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            VoiceCallFooterButton(
                icon = Icons.Filled.VolumeUp,
                text = stringResource(id = R.string.speaker)
            )
            Spacer(modifier = Modifier.weight(1f))
            VoiceCallFooterButton(
                icon = Icons.Filled.Mic,
                text = stringResource(id = R.string.mute)
            )
            Spacer(modifier = Modifier.weight(1f))
            VoiceCallFooterButton(
                icon = Icons.Filled.Dialpad,
                text = stringResource(id = R.string.keypad)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }

}

@Composable
fun VoiceCallFooterButton(
    icon: ImageVector,
    text: String,
) {
    Column(
        modifier = Modifier
            .width(60.dp)
            .bounceClick { /* no-io */ },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = (-1).dp, y = 1.dp)
                    .blur(2.dp),
                imageVector = icon,
                tint = Color.Black,
                contentDescription = null,
            )
            Icon(
                modifier = Modifier
                    .size(40.dp),
                imageVector = icon,
                tint = Color.White,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .widthIn(0.dp, 80.dp),
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 3f,
                ),
                textAlign = TextAlign.Center,
            ),
            text = text,
        )
    }
}

@Preview
@Composable
fun VoiceCallFooterPreview() {
    VoiceCallFooter()
}