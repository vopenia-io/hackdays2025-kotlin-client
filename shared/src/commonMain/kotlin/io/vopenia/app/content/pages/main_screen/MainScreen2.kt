package io.vopenia.app.content.pages.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.drick.compose.hotpreview.HotPreview
import io.vopenia.app.AppModel
import io.vopenia.app.AppModelPreview
import io.vopenia.app.PreviewApp
import io.vopenia.app.theme.WindowType
import io.vopenia.app.window.LocalFrame

@Composable
fun MainScreen2(
    modifier: Modifier,
    appModel: AppModel
) {
    MainScreen(modifier, appModel)
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