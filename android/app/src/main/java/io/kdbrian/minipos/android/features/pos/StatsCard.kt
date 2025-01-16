package io.kdbrian.minipos.android.features.pos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.TextLocals.LocalDefaultTextStyle

@Composable
fun StatsCard(
    modifier: Modifier = Modifier,
    title: String = LoremIpsum(3).values.joinToString(),
    stats: String = LoremIpsum(1).values.joinToString(),
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            style = LocalDefaultTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                color = Color.LightGray
            )
        )


        Text(
            text = stats,
            style = LocalDefaultTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp
            )
        )


        Row(
            modifier = Modifier
                .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = Color.LightGray)
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(25.dp)
                    .background(shape = RoundedCornerShape(6.dp), color = Color.LightGray)
            ) {
                Icon(imageVector = Icons.Rounded.KeyboardArrowUp, contentDescription = null)
            }

            Spacer(Modifier.width(4.dp))

            Text(text = "+ 8.00 %", style = LocalDefaultTextStyle.current.copy(fontSize = 12.sp))

        }

    }
}


@Preview
@Composable
private fun StatsCardPrev() {
    MiniposTheme {
        StatsCard()
    }
}