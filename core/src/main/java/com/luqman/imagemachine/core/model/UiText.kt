package com.luqman.imagemachine.core.model

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {

    data class DynamicText(
        val message: String
    ): UiText()

    data class StringResource(
        @StringRes val resId: Int
    ): UiText()

}

@Composable
fun UiText.asString(): String {
    return when(this) {
        is UiText.DynamicText -> message
        is UiText.StringResource -> stringResource(id = resId)
    }
}

fun UiText.asString(context: Context): String {
    return when(this) {
        is UiText.DynamicText -> message
        is UiText.StringResource -> context.getString(resId)
    }
}