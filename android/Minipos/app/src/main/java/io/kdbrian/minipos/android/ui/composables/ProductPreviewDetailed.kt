package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.kdbrian.minipos.android.BuildConfig
import io.kdbrian.minipos.android.LocalFontFamily
import io.kdbrian.minipos.android.R
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import src.main.graphql.GetAllProductsQuery
import timber.log.Timber

@Composable
fun ProductPreviewDetailed(
    modifier: Modifier = Modifier,
    product: GetAllProductsQuery.GetProduct,
    onSelect: (GetAllProductsQuery.GetProduct) -> Unit
) {
    Card(
        onClick = { onSelect(product) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = product.productName,
                    modifier = modifier,
                    fontSize = 20.sp,
                    fontFamily = LocalFontFamily.current
                )
                Text(
                    text = "${product.stock}",
                    modifier = modifier,
                    fontSize = 24.sp,
                )
            }

            Text(
                text = "$${product.productPrice.toString()}",
                modifier = modifier,
                fontSize = 28.sp,
                color = Color.Yellow
            )
        }
    }

}

@Preview
@Composable
private fun ProductPreviewDetailedPrev() {
    MiniposTheme {
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: GetAllProductsQuery.GetProduct,
    onSelect: (GetAllProductsQuery.GetProduct) -> Unit
) {
    MiniposTheme {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { onSelect(product) },
        ) {

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(shape = RoundedCornerShape(6.dp), color = Color.Gray),
                contentAlignment = Alignment.Center
            ) {

                product.images?.let {
                    val randomImagePosition = (0..<product.images.size).random()
                    Timber.d("Url ${BuildConfig.ngrokHost}${product.images[0]}")
                    AsyncImage(
                        model = "${BuildConfig.ngrokHost}${product.images[randomImagePosition]}",
                        contentDescription = product.productName,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                } ?: kotlin.run {
                    Image(
                        painter = painterResource(R.drawable.product_preview),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp)
                    )
                }

            }

            Spacer(Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f)
            ) {

                Text(
                    text = product.productName,
                    fontFamily = LocalFontFamily.current,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "stock : ${product.stock}",
                    fontFamily = LocalFontFamily.current,
                    fontSize = 18.sp,
                )
            }

            Image(
                modifier = Modifier,
                imageVector = Icons.Default.Star,
                contentDescription = null
            )

        }


    }
}
