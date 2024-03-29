package com.mate.carpool.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mate.carpool.ui.theme.black
import com.mate.carpool.ui.theme.primary50
import com.mate.carpool.ui.theme.red50
import com.mate.carpool.ui.util.tu
import com.mate.carpool.util.MatePreview

@Stable
data class DialogState(
    val header: String,
    val content: String,
    val positiveMessage: String,
    val negativeMessage: String,
    val onPositiveCallback: () -> Unit = {},
    val onNegativeCallback: () -> Unit,
) {
    companion object {
        fun getInitValue() = DialogState(
            header = "",
            content = "",
            positiveMessage = "",
            negativeMessage = "",
            onNegativeCallback = {}
        )
    }
}

@Composable
fun DialogCustom(
    dialogState: DialogState,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp)
        ) {
            DialogCustomContent(
                dialogState = dialogState,
                onPositiveCallback = dialogState.onPositiveCallback,
                onNegativeCallback = dialogState.onNegativeCallback
            )
        }
    }
}

@Composable
private fun DialogCustomContent(
    dialogState: DialogState,
    onPositiveCallback: () -> Unit = {},
    onNegativeCallback: () -> Unit,
) {
    Column() {
        Column(modifier = Modifier.padding(horizontal = 35.dp, vertical = 19.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dialogState.header,
                    fontSize = 16.tu,
                    fontWeight = FontWeight.W700,
                    color = black
                )
            }
            VerticalSpacer(height = 12.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dialogState.content,
                    fontSize = 12.tu,
                    fontWeight = FontWeight.W400,
                    color = black
                )
            }
        }
        HorizontalDivider()
        Row(
            modifier = Modifier.height(44.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 21.dp,
                        vertical = 9.dp
                    )
                    .weight(1f)
                    .clickable { onNegativeCallback() }
            ) {
                Text(
                    text = dialogState.negativeMessage,
                    fontSize = 16.tu,
                    fontWeight = FontWeight.W400,
                    color = primary50,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            if(dialogState.positiveMessage.isNotBlank()) {
                VerticalDivider()
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 21.dp,
                            vertical = 9.dp
                        )
                        .weight(1f)
                        .clickable { onPositiveCallback() }
                ) {
                    Text(
                        text = dialogState.positiveMessage,
                        fontSize = 16.tu,
                        fontWeight = FontWeight.W400,
                        color = red50,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewDialogCustomWithPositiveMessage() {
    MatePreview {
        DialogCustom(
            dialogState = DialogState(
                header = "헤더메세지는 이렇게 나옵니다.",
                content = "컨텐트메세지는 이렇게 나옵니다.",
                positiveMessage = "네, 퇴출할래요.",
                negativeMessage = "아뇨, 취소할게요.",
                onNegativeCallback = {}
            ),
            onDismissRequest = {}
        )
    }
}

@Composable
@Preview
private fun PreviewDialogCustomConfirm() {
    MatePreview {
        DialogCustom(
            dialogState = DialogState(
                header = "헤더메세지는 이렇게 나옵니다.",
                content = "컨텐트메세지는 이렇게 나옵니다.",
                positiveMessage = "",
                negativeMessage = "확인",
                onNegativeCallback = {}
            ),
            onDismissRequest = {}
        )
    }
}