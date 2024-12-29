package io.kdbrian.minipos.android.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.LocalFontFamily
import io.kdbrian.minipos.android.R


object TeaPorts {

    data class TeaPort(
        val name: String = LoremIpsum(2).values.joinToString(),
        val description: String = LoremIpsum(20).values.joinToString(),
        val image: Int = R.drawable.teapot
    )

    @Composable
    fun TeaPotsList(
        modifier: Modifier = Modifier,
        teaPorts: List<TeaPort>
    ) {
        LazyColumn(modifier = modifier) {
            itemsIndexed(teaPorts) { index, teaPort ->
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(teaPort.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(shape = RoundedCornerShape(6.dp))
                    )

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = teaPort.name,
                        fontSize = 18.sp,
                        fontFamily = LocalFontFamily.current
                    )
                }

                if (index != teaPorts.lastIndex)
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
            }
        }
    }

}


@Preview
@Composable
private fun TeaPotsListPrev() {
//    TeaPotsList()
}
