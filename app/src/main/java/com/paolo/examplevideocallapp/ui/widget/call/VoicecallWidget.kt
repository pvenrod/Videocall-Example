package com.paolo.examplevideocallapp.ui.widget.call

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.ui.theme.AppColors
import com.paolo.examplevideocallapp.viewmodel.CallUiState

@Composable
fun VoicecallWidget(
    state: CallUiState,
    onStartCallClick: (() -> Unit)?,
    onEndCallClick: (() -> Unit)?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (!state.callHasStarted) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = stringResource(R.string.call_incoming),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(2f, 2f),
                        blurRadius = 3f,
                    ),
                ),
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = state.configuration?.character?.name.orEmpty(),
            style = TextStyle(
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 3f,
                ),
            ),
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = state.configuration?.character?.phoneNumber.orEmpty(),
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 3f,
                ),
            ),
        )
        if (state.callHasStarted && !state.callHasEnded) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = state.remainingTime,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(2f, 2f),
                        blurRadius = 3f,
                    ),
                ),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (state.callHasStarted && !state.callHasEnded) {
            VoiceCallFooter()
            Spacer(modifier = Modifier.height(30.dp))
            CommonCallFooterButton(
                color = AppColors.Hangout,
                icon = Icons.Filled.CallEnd,
                onClick = onEndCallClick,
            )
        }
        if (!state.callHasStarted && !state.callHasEnded) {
            CommonCallFooter(
                onStartCallClick = onStartCallClick,
                onEndCallClick = onEndCallClick,
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}