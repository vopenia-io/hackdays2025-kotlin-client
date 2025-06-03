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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import eu.codlab.compose.theme.LocalDarkTheme
import eu.codlab.compose.widgets.TextNormal
import io.vopenia.app.AppModel
import io.vopenia.app.LocalFontSizes
import io.vopenia.app.theme.AppColor
import io.vopenia.app.theme.defaultCardBackground
import io.vopenia.app.utils.localized
import io.vopenia.meet.shared.res.Res
import io.vopenia.meet.shared.res.cancel
import io.vopenia.meet.shared.res.home_joinInputExample
import io.vopenia.meet.shared.res.home_joinInputLabel
import io.vopenia.meet.shared.res.home_joinInputSubmit
import io.vopenia.meet.shared.res.home_joinMeeting
import io.vopenia.meet.shared.res.home_joinMeetingTipContent
import io.vopenia.meet.shared.res.home_joinMeetingTipHeading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptRoom(
    appModel: AppModel,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val state by appModel.states.collectAsState()

    var roomCode by remember { mutableStateOf(TextFieldValue("")) }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Card(
            modifier = Modifier
                .widthIn(100.dp, 250.dp)
                .heightIn(100.dp, 400.dp),
            backgroundColor = defaultCardBackground(),
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextNormal(
                    text = Res.string.home_joinMeeting.localized(),
                    fontSize = LocalFontSizes.current.joinRoom.title,
                    fontWeight = FontWeight.Bold
                )

                TextNormal(
                    text = Res.string.home_joinInputExample.localized(),
                )

                TextNormal(
                    text = Res.string.home_joinInputLabel.localized(),
                )

                OutlinedTextField(
                    value = roomCode,
                    onValueChange = { roomCode = it },
                    colors = if (LocalDarkTheme.current) {
                        TextFieldDefaults.outlinedTextFieldColors(
                            textColor = AppColor.WhiteCream,
                            unfocusedBorderColor = AppColor.WhiteCream,
                            focusedBorderColor = AppColor.BlueLight
                        )
                    } else {
                        TextFieldDefaults.outlinedTextFieldColors()
                    }
                )


                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColor.WhiteCream
                        ),
                        elevation = ButtonDefaults.elevation(0.dp),
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        TextNormal(
                            text = Res.string.cancel.localized(),
                            color = AppColor.GrayExtraDark
                        )
                    }

                    Column(Modifier.width(16.dp)) { /* */ }

                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColor.Blue
                        ),
                        enabled = roomCode.text.isNotBlank() && !state.loadRoomInfo,
                        onClick = {
                            onConfirm(roomCode.text)
                        }
                    ) {
                        TextNormal(
                            color = Color.White,
                            text = Res.string.home_joinInputSubmit.localized()
                        )
                    }
                }

                TextNormal(
                    text = Res.string.home_joinMeetingTipHeading.localized(),
                    fontSize = LocalFontSizes.current.joinRoom.title,
                    fontWeight = FontWeight.Bold
                )
                TextNormal(
                    text = Res.string.home_joinMeetingTipContent.localized(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}