package com.paolo.examplevideocallapp.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.ui.theme.AppColors

@Composable
fun NoNetworkDialog() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable(enabled = false) {}
            .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .width(340.dp)
                .shadow(10.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(PaddingValues(20.dp, 30.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp),
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = "Warning",
                        tint = AppColors.NoInternetConnectionTitle,
                    )
                    Text(
                        text = stringResource(R.string.enable_internet_connection_title),
                        color = AppColors.NoInternetConnectionTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = TextUnit(22f, TextUnitType.Sp),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = stringResource(R.string.enable_internet_connection_desc),
                        fontSize = TextUnit(13f, TextUnitType.Sp),
                        color = AppColors.DialogDesc,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NoNetworkDialogPreview() {
    NoNetworkDialog()
}