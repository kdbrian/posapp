package io.kdbrian.minipos.android.ui.composables.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.kdbrian.minipos.android.BuildConfig
import src.main.graphql.GetAllProductsQuery
import io.kdbrian.minipos.android.R

@Composable
fun SimpleGroupedProductDisplay(
    modifier: Modifier,
    productsQuery: GetAllProductsQuery.GetProduct
) {
    var currentImage by remember { mutableStateOf(productsQuery.images?.get(0).toString()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {

        AsyncImage(
            model = "${BuildConfig.ngrokHost}/$currentImage",
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .background(color = Color(0xFFE88873), shape = RoundedCornerShape(8.dp)),
            placeholder = painterResource(R.drawable.product_preview),
        )
    }


}