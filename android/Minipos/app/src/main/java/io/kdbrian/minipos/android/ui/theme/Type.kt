package io.kdbrian.minipos.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.R

val supreme = FontFamily(
    Font(R.font.supreme_regular),
    Font(R.font.supreme_extrabold),
    Font(R.font.supreme_italic),
    Font(R.font.supreme_medium),
    Font(R.font.supreme_thin),
    Font(R.font.supreme_bolditalic),
    Font(R.font.supreme_extralight),
    Font(R.font.supreme_extrabolditalic),
    Font(R.font.supreme_thinitalic),
    Font(R.font.supreme_mediumitalic),
    Font(R.font.supreme_light),
    Font(R.font.supreme_lightitalic),
)

val pally = FontFamily(
    Font(R.font.pally_regular),
    Font(R.font.pally_bold),
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)