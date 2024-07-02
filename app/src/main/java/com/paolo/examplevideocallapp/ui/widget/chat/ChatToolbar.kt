package com.paolo.examplevideocallapp.ui.widget.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.misc.bounceClick
import com.paolo.examplevideocallapp.viewmodel.ChatUiState

@Composable
fun ChatToolbar(
    state: ChatUiState,
    onBackClick: (() -> Unit)?,
    onVideocallClick: (() -> Unit)?,
    onCallClick: (() -> Unit)?,
) {
    Row(
        modifier = Modifier
            .shadow(10.dp)
            .fillMaxWidth()
            .height(66.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .width(40.dp)
                .padding(4.dp, 0.dp)
                .fillMaxHeight()
                .bounceClick { onBackClick?.invoke() },
            imageVector = Icons.Default.ChevronLeft,
            contentDescription = "Favorite",
            tint = Color.Black,
        )
        Image(
            modifier = Modifier
                .size(60.dp),
            painter = painterResource(R.drawable.character),
            contentDescription = "Profile picture",
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = state.configuration?.character?.name.orEmpty(),
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = stringResource(state.currentStatusText),
                fontSize = 11.sp,
            )
        }
        Spacer(modifier = Modifier.weight(1f, true))
        Icon(
            modifier = Modifier
                .width(40.dp)
                .padding(4.dp, 0.dp)
                .fillMaxHeight()
                .bounceClick { onVideocallClick?.invoke() },
            imageVector = Icons.Default.VideoCall,
            contentDescription = "Favorite",
            tint = Color.Black,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            modifier = Modifier
                .width(40.dp)
                .padding(4.dp, 0.dp)
                .fillMaxHeight()
                .bounceClick { onCallClick?.invoke() },
            imageVector = Icons.Default.Call,
            contentDescription = "Favorite",
            tint = Color.Black,
        )
        Spacer(modifier = Modifier.width(20.dp))
    }
}