package io.kdbrian.minipos.android.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object TextLocals {

    val defaultTextStyle = TextStyle(
        fontFamily = supreme,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
    )

    val LocalDefaultTextStyle = staticCompositionLocalOf { defaultTextStyle }

}

