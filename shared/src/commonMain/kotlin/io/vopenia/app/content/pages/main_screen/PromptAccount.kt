package io.vopenia.app.content.pages.main_screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import eu.codlab.compose.theme.LocalDarkTheme
import eu.codlab.compose.widgets.TextNormal
import io.vopenia.app.AppModel
import io.vopenia.app.LocalFontSizes
import io.vopenia.app.content.AppContent
import io.vopenia.app.theme.AppColor
import io.vopenia.app.theme.defaultCardBackground
import io.vopenia.app.utils.localized
import io.vopenia.meet.shared.res.Res
import io.vopenia.meet.shared.res.cancel
import io.vopenia.meet.shared.res.meet_connect_yourself
import io.vopenia.meet.shared.res.meet_password
import io.vopenia.meet.shared.res.meet_username
import io.vopenia.meet.shared.res.validate
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptAccount(
    appModel: AppModel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val state by appModel.states.collectAsState()

    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    val buttonColor = if (LocalDarkTheme.current) {
        AppColor.WhiteCream
    } else {
        AppColor.GrayExtraDark
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Card(
            modifier = Modifier
                .widthIn(100.dp, 200.dp)
                .heightIn(100.dp, 300.dp),
            backgroundColor = defaultCardBackground(),
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextNormal(
                    text = Res.string.meet_connect_yourself.localized(),
                    fontSize = LocalFontSizes.current.joinRoom.title,
                    fontWeight = FontWeight.Bold
                )

                TextNormal(
                    text = Res.string.meet_username.localized()
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it }
                )

                TextNormal(
                    text = Res.string.meet_password.localized()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    )
                )

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    OutlinedButton(
                        enabled = !state.authenticating,
                        elevation = ButtonDefaults.elevation(0.dp),
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        TextNormal(
                            text = stringResource(Res.string.cancel)
                        )
                    }

                    Column(Modifier.width(16.dp)) { /* */ }

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColor.Blue
                        ),
                        enabled = username.text.isNotBlank() && password.text.isNotBlank() &&
                                !state.authenticating,
                        onClick = {
                            appModel.login(username.text, password.text) {
                                onConfirm()
                            }
                        }
                    ) {
                        TextNormal(
                            color = Color.White,
                            text = stringResource(Res.string.validate)
                        )
                    }
                }
            }
        }
    }
}