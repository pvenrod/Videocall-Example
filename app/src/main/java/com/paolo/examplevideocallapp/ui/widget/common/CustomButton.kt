package com.paolo.examplevideocallapp.ui.widget.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paolo.examplevideocallapp.misc.bounceClick

@Composable
fun CustomButton(
    onClick: (() -> Unit)?,
    color: Color,
    icon: ImageVector,
    text: String,
    roundStart: Boolean = false,
    roundEnd: Boolean = false,
) {
    Row(
        modifier = Modifier
            .width(260.dp)
            .height(50.dp)
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = RoundedCornerShape(
                    100f.takeIf { roundStart } ?: 10f,
                    100f.takeIf { roundEnd } ?: 10f,
                    100f.takeIf { roundStart } ?: 10f,
                    100f.takeIf { roundEnd } ?: 10f,
                )
                clip = true
            }
            .background(color = Color.White)
            .bounceClick { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Favorite",
            modifier = Modifier.size(20.dp),
            tint = color,
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = text.uppercase(),
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    CustomButton(
        onClick = null,
        color = Color.Red,
        icon = Icons.Default.Call,
        text = "Videollamada",
        roundStart = true,
        roundEnd = false,
    )
}