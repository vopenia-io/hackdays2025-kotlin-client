package io.vopenia.app.content.pages.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.drick.compose.hotpreview.HotPreview
import io.vopenia.app.AppModel
import io.vopenia.app.AppModelPreview
import io.vopenia.app.PreviewApp
import io.vopenia.app.theme.WindowType
import io.vopenia.app.window.LocalFrame

@Composable
fun MainScreen(
    modifier: Modifier,
    appModel: AppModel
) {
    println("MainScreen ${LocalFrame.current}")
    when (LocalFrame.current) {
        WindowType.SMARTPHONE_TINY -> MainScreenTiny(modifier, appModel)
        WindowType.SMARTPHONE -> MainScreenTiny(modifier, appModel)
        WindowType.PHABLET -> MainScreenBig(modifier, appModel)
        WindowType.TABLET -> MainScreenBig(modifier, appModel)
    }
}

@Composable
fun MainScreenBig(
    modifier: Modifier,
    appModel: AppModel
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(2.0f).widthIn(0.dp, 250.dp)) {
            Row {
                MainBlock(Modifier, appModel)
            }
        }

        Column(Modifier.weight(1.0f)) {
            // nothing
        }
    }
}

@Composable
fun MainScreenTiny(
    modifier: Modifier,
    appModel: AppModel
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            Modifier,
        ) {
            MainBlock(Modifier.fillMaxSize(), appModel)
        }
    }
}

@HotPreview(widthDp = 200, heightDp = 400, darkMode = true)
@HotPreview(widthDp = 200, heightDp = 400, darkMode = false)
@Composable
private fun MainScreenPreview() {
    PreviewApp(modifier = Modifier.fillMaxSize()) {
        MainScreen(modifier = Modifier.fillMaxSize(), AppModelPreview())
    }
}

@HotPreview(widthDp = 200, heightDp = 400, darkMode = true)
@HotPreview(widthDp = 200, heightDp = 400, darkMode = false)
@Composable
private fun MainScreenTinyPreview() {
    PreviewApp(modifier = Modifier.fillMaxSize()) {
        MainScreenTiny(modifier = Modifier.fillMaxSize(), AppModelPreview())
    }
}

@HotPreview(widthDp = 500, heightDp = 400, darkMode = true)
@HotPreview(widthDp = 500, heightDp = 400, darkMode = false)
@Composable
private fun MainScreenBigPreview() {
    PreviewApp(modifier = Modifier.fillMaxSize()) {
        MainScreenBig(modifier = Modifier.fillMaxSize(), AppModelPreview())
    }
}