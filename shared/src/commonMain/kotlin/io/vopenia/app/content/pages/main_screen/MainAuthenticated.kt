package io.vopenia.app.content.pages.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.drick.compose.hotpreview.HotPreview
import eu.codlab.compose.theme.LocalDarkTheme
import eu.codlab.compose.widgets.TextNormal
import io.vopenia.app.PreviewApp
import io.vopenia.app.theme.AppColor
import io.vopenia.app.theme.WindowType
import io.vopenia.app.utils.localized
import io.vopenia.app.window.LocalFrame
import io.vopenia.meet.shared.res.Res
import io.vopenia.meet.shared.res.home_createMeeting
import io.vopenia.meet.shared.res.home_joinMeeting
import io.vopenia.meet.shared.res.home_loginToCreateMeeting

@Composable
fun MainUnauthenticated(
    modifier: Modifier,
    onLog: () -> Unit = { /* */ },
    onJoin: () -> Unit = { /* */ }
) {
    val colors =
        if (LocalDarkTheme.current) {
            listOf(
                AppColor.Blue to AppColor.GrayExtraLight,
                AppColor.GrayExtraLight to AppColor.Blue,
            )
        } else {
            listOf(
                AppColor.GrayExtraLight to AppColor.Blue,
                AppColor.Blue to AppColor.GrayExtraLight,
            )
        }

    Column {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colors.first().first
            ),
            onClick = onLog
        ) {
            TextNormal(
                text = Res.string.home_loginToCreateMeeting.localized(),
                color = colors.first().second
            )
        }

        OutlinedButton(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colors.last().first
            ),
            onClick = onJoin
        ) {
            TextNormal(
                text = Res.string.home_joinMeeting.localized(),
                color = colors.last().second
            )
        }
    }
}

@Composable
fun MainAuthenticated(
    modifier: Modifier,
    onCreate: () -> Unit = { /* */ },
    onJoin: () -> Unit = { /* */ }
) {
    val colors =
        if (LocalDarkTheme.current) {
            listOf(
                AppColor.Blue to AppColor.GrayExtraLight,
                AppColor.GrayExtraLight to AppColor.Blue,
            )
        } else {
            listOf(
                AppColor.GrayExtraLight to AppColor.Blue,
                AppColor.Blue to AppColor.GrayExtraLight,
            )
        }

    Column {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colors.first().first
            ),
            onClick = onCreate
        ) {
            TextNormal(
                text = Res.string.home_createMeeting.localized(),
                color = colors.first().second
            )
        }

        OutlinedButton(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colors.last().first
            ),
            onClick = onJoin
        ) {
            TextNormal(
                text = Res.string.home_joinMeeting.localized(),
                color = colors.last().second
            )
        }
    }
}

@Composable
private fun BoxOrColumn(modifier: Modifier, content: @Composable () -> Unit) {
    if (LocalFrame.current == WindowType.SMARTPHONE || LocalFrame.current == WindowType.SMARTPHONE_TINY) {
        Column(
            modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
        }
    } else {
        Row(
            modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
        }
    }
}

@HotPreview(widthDp = 500, heightDp = 400, darkMode = true)
@HotPreview(widthDp = 500, heightDp = 400, darkMode = false)
@Composable
fun MainAuthenticatedPreview() {
    PreviewApp(modifier = Modifier.fillMaxSize()) {
        MainAuthenticated(modifier = Modifier.fillMaxSize())
    }
}

@HotPreview(widthDp = 500, heightDp = 400, darkMode = true)
@HotPreview(widthDp = 500, heightDp = 400, darkMode = false)
@Composable
fun MainUnAuthenticatedPreview() {
    PreviewApp(modifier = Modifier.fillMaxSize()) {
        MainUnauthenticated(modifier = Modifier.fillMaxSize())
    }
}