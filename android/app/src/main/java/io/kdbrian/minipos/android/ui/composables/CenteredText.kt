package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.ui.theme.TextLocals.LocalDefaultTextStyle


@Composable
fun CenteredText(
    modifier: Modifier = Modifier,
    message: String = "Nothing here",
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = LocalDefaultTextStyle.current.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray
            )
        )
    }
}