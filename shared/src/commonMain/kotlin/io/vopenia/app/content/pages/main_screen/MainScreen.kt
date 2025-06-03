package io.vopenia.app.content.pages.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.drick.compose.hotpreview.HotPreview
import eu.codlab.compose.widgets.CustomOutlinedButton
import eu.codlab.compose.widgets.TextNormal
import io.vopenia.app.AppModel
import io.vopenia.app.AppModelPreview
import io.vopenia.app.LocalApp
import io.vopenia.app.PreviewApp
import io.vopenia.app.theme.WindowType
import io.vopenia.app.utils.localized
import io.vopenia.app.window.LocalFrame
import io.vopenia.meet.shared.res.Res
import io.vopenia.meet.shared.res.disconnect

@Composable
fun MainScreen(
    modifier: Modifier,
    appModel: AppModel
) {
    Column(modifier) {
        val subModifier = Modifier.fillMaxWidth().weight(1.0f)

        when (LocalFrame.current) {
            WindowType.SMARTPHONE_TINY -> MainScreenTiny(subModifier, appModel)
            WindowType.SMARTPHONE -> MainScreenTiny(subModifier, appModel)
            WindowType.PHABLET -> MainScreenBig(subModifier, appModel)
            WindowType.TABLET -> MainScreenBig(subModifier, appModel)
        }

        ShowLogout()
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
        Row {
            MainBlock(Modifier.fillMaxWidth(), appModel)
        }
    }
}

@Composable
private fun ShowLogout() {
    val state by LocalApp.current.states.collectAsState()
    val model = LocalApp.current

    if (null == state.session) return

    CustomOutlinedButton(
        onClick = {
            model.logout()
        }
    ) {
        TextNormal(Res.string.disconnect.localized())
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