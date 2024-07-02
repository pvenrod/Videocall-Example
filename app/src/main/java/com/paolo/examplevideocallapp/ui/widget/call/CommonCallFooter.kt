package com.paolo.examplevideocallapp.ui.widget.call

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paolo.examplevideocallapp.misc.bounceClick
import com.paolo.examplevideocallapp.ui.theme.AppColors

@Composable
fun CommonCallFooter(
    onStartCallClick: (() -> Unit)?,
    onEndCallClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        CommonCallFooterButton(
            color = AppColors.Hangout,
            icon = Icons.Filled.CallEnd,
            onClick = onEndCallClick,
        )
        Spacer(modifier = Modifier.weight(1f))
        CommonCallFooterButton(
            color = AppColors.Hangup,
            icon = Icons.Filled.Call,
            onClick = onStartCallClick,
        )
        Spacer(modifier = Modifier.width(50.dp))
    }
}

@Composable
fun CommonCallFooterButton(
    color: Color,
    icon: ImageVector,
    onClick: (() -> Unit)?,
) {
    val size = 80.dp
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(size / 2))
            .background(color)
            .bounceClick { onClick?.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .size(size / 1.5f),
            imageVector = icon,
            tint = Color.White,
            contentDescription = "Call button",
        )
    }
}

@Preview
@Composable
fun CommonCallFooterPreview() {
    CommonCallFooter(
        onStartCallClick = null,
        onEndCallClick = null,
    )
}