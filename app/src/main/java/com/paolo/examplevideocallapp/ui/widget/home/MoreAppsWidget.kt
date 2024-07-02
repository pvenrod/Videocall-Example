package com.paolo.examplevideocallapp.ui.widget.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.paolo.examplevideocallapp.R
import com.paolo.examplevideocallapp.data.model.PaoloAppDTO
import com.paolo.examplevideocallapp.misc.bounceClick

@Composable
fun MoreAppsWidget(
    appList: List<PaoloAppDTO>,
    onAppClick: ((app: PaoloAppDTO) -> Unit)?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                shadowElevation = 8.dp.toPx()
                shape = RoundedCornerShape(
                    200f,
                    200f,
                    0f,
                    0f,
                )
                clip = true
            }
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.other_apps).uppercase(),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Spacer(Modifier.height(20.dp))
        LazyVerticalGrid(
            modifier = Modifier.padding(20.dp, 0.dp),
            columns = GridCells.Fixed(count = 3)
        ) {
            items(appList.size) { index ->
                val app = appList[index]
                if (app.show == true) {
                    AppItem(app, onAppClick)
                }
            }
        }
    }
}

@Composable
fun AppItem(
    app: PaoloAppDTO,
    onAppClick: ((app: PaoloAppDTO) -> Unit)?,
) {
    Column(
        modifier = Modifier
            .bounceClick { onAppClick?.invoke(app) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(shape = RoundedCornerShape(12.dp)),
            painter = rememberAsyncImagePainter(app.storeImageUrl),
            contentDescription = app.name,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = app.name.orEmpty(),
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(25.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun MoreAppsWidgetPreview() {
    MoreAppsWidget(
        listOf(
            PaoloAppDTO(
                "App 1",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App 2",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App 3",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App 4",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App nombre más largo aún",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App 1",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App 2",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App 3",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App 4",
                "",
                "",
                true,
            ),
            PaoloAppDTO(
                "App nombre más largo aún",
                "",
                "",
                true,
            )
        ),
        {},
    )
}