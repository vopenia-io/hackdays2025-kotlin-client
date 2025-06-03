package io.vopenia.app.content.pages.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.drick.compose.hotpreview.HotPreview
import eu.codlab.compose.widgets.TextNormal
import io.vopenia.app.AppModel
import io.vopenia.app.AppModelPreview
import io.vopenia.app.LocalFontSizes
import io.vopenia.app.PreviewApp
import io.vopenia.app.utils.localized
import io.vopenia.meet.shared.res.Res
import io.vopenia.meet.shared.res.home_heading
import io.vopenia.meet.shared.res.home_intro

@Composable
fun MainBlock(
    modifier: Modifier,
    appModel: AppModel
) {
    val state by appModel.states.collectAsState()
    var showLogin by remember { mutableStateOf(false) }
    val showJoin by remember { mutableStateOf(false) }

    if (showLogin) {
        PromptAccount(
            appModel,
            onDismiss = {
                showLogin = false
            },
            onConfirm = {
                showLogin = false
            }
        )
    }

    Column(
        modifier = modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TextNormal(
            text = Res.string.home_heading.localized(),
            fontWeight = FontWeight.Bold,
            fontSize = LocalFontSizes.current.home.title
        )

        TextNormal(
            text = Res.string.home_intro.localized(),
            fontSize = LocalFontSizes.current.home.subTitle
        )

        if (null != state.session?.userName) {
            MainAuthenticated(
                modifier, onCreate = {
                    // todo
                },
                onJoin = {
                    // todo
                }
            )
        } else {
            MainUnauthenticated(
                modifier, onLog = {
                    showLogin = true
                },
                onJoin = {
                    // TODO
                })
        }
    }
}

@HotPreview(widthDp = 500, heightDp = 400, darkMode = true)
@HotPreview(widthDp = 500, heightDp = 400, darkMode = false)
@Composable
fun MainBlockPreview() {
    PreviewApp(modifier = Modifier.fillMaxSize()) {
        MainBlock(modifier = Modifier.fillMaxSize(), AppModelPreview())
    }
}