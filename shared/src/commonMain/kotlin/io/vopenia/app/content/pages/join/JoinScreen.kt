package io.vopenia.app.content.pages.join

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.drick.compose.hotpreview.HotPreview
import eu.codlab.compose.widgets.TextNormal
import eu.codlab.viewmodel.rememberViewModel
import io.vopenia.app.LocalApp
import io.vopenia.app.PreviewApp
import io.vopenia.app.theme.modifier.defaultBackground

@Suppress("LongMethod")
@Composable
fun JoinScreen(
    modifier: Modifier = Modifier
) {
    val app = LocalApp.current
    val model = rememberViewModel("join_screen") { JoinScreenModel(app) }
    val state by model.states.collectAsState()

    Box(
        modifier = modifier.imePadding()
            .defaultBackground()
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = modifier.imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.widthIn(0.dp, 250.dp),
                contentAlignment = Alignment.Center
            ) {
                val internalModifier = Modifier.fillMaxWidth()
                Column {
                    OutlinedTextField(
                        modifier = internalModifier,
                        label = {
                            TextNormal(
                                text = "Participant's Name"
                            )
                        },
                        value = state.participant,
                        onValueChange = { model.participant = it }
                    )

                    Button(
                        modifier = internalModifier,
                        enabled = model.participant.text.isNotEmpty(),
                        onClick = { model.join() }
                    ) {
                        TextNormal(
                            text = "Join"
                        )
                    }
                }
            }
        }
    }
}

@HotPreview(widthDp = 400, heightDp = 600, darkMode = true)
@HotPreview(widthDp = 400, heightDp = 600, darkMode = false)
@Composable
private fun JoinScreenPreview() {
    PreviewApp {
        JoinScreen(Modifier.fillMaxSize())
    }
}
