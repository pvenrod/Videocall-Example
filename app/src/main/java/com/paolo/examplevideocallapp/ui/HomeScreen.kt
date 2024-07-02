package com.paolo.examplevideocallapp.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.VideoCall
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.data.model.AppConfigurationDTO
import com.paolo.examplevideocallapp.data.model.CharacterDTO
import com.paolo.examplevideocallapp.data.model.PaoloAppDTO
import com.paolo.examplevideocallapp.ui.theme.AppColors
import com.paolo.examplevideocallapp.ui.widget.common.CustomButton
import com.paolo.examplevideocallapp.ui.widget.common.GifImage
import com.paolo.examplevideocallapp.ui.widget.home.MoreAppsWidget
import com.paolo.examplevideocallapp.viewmodel.HomeScreenViewModel
import com.paolo.examplevideocallapp.viewmodel.HomeUiState

@Composable
fun HomeScreenContent(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    val activity = context as Activity
    HomeScreen(
        state,
        { viewModel.onChatClick(activity, navController) },
        { viewModel.onVideocallClick(activity, navController) },
        { viewModel.onCallClick(activity, navController) },
        { viewModel.onAppClick(activity, it) },
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onChatClick: (() -> Unit)? = null,
    onVideocallClick: (() -> Unit)? = null,
    onCallClick: (() -> Unit)? = null,
    onAppClick: ((app: PaoloAppDTO) -> Unit)? = null,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.background),
                        contentScale = ContentScale.FillBounds
                    )
                    .paint(
                        painterResource(id = R.drawable.backgroundoverlay),
                        contentScale = ContentScale.FillBounds
                    ),
            horizontalAlignment = CenterHorizontally,
        ) {
                Spacer(Modifier.height(10.dp))
                Image(
                    modifier = Modifier
                        .width(160.dp)
                        .height(160.dp),
                    painter = painterResource(id = R.drawable.character),
                    contentDescription = "Profile image",
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = state.configuration?.character?.name.orEmpty(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 3f,
                        ),
                    ),
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = state.configuration?.character?.phoneNumber.orEmpty(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 3f,
                        ),
                    )
                )
                Spacer(Modifier.height(20.dp))
                CustomButton(
                    onClick = onChatClick,
                    color = AppColors.ChatButton,
                    icon = Icons.Default.Send,
                    text = stringResource(id = R.string.chat_button),
                    roundEnd = true,
                )
                Spacer(Modifier.height(12.dp))
                CustomButton(
                    onClick = onVideocallClick,
                    color = AppColors.VideocallButton,
                    icon = Icons.Rounded.VideoCall,
                    text = stringResource(id = R.string.videocall_button),
                    roundStart = true,
                )
                Spacer(Modifier.height(12.dp))
                CustomButton(
                    onClick = onCallClick,
                    color = AppColors.CallButton,
                    icon = Icons.Default.Call,
                    text = stringResource(id = R.string.call_button),
                    roundEnd = true,
                )
                Spacer(Modifier.height(40.dp))
                if (state.configuration?.showMoreApps == true && state.moreAppsList.isNotEmpty()) {
                    MoreAppsWidget(
                        appList = state.moreAppsList,
                        onAppClick = onAppClick,
                    )
                }
        }
        if (state.showLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center,
            ) {
                GifImage(
                    imageId = R.drawable.loading,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HomeScreen(
        HomeUiState(
            AppConfigurationDTO(
                CharacterDTO.DUMMY,
                true,
                false,
                false,
                1000L,
            ),
            showLoading = true,
            showError = false,
            moreAppsList = listOf(PaoloAppDTO("", "", "", true))
        ),
    )
}