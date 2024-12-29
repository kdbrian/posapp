package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.R
import io.kdbrian.minipos.android.ui.theme.pally
import io.kdbrian.minipos.android.util.CategoryItem

@Composable
fun ProductCategoryCard(
    categoryItem: CategoryItem,
    onCategoryClick: (CategoryItem) -> Unit,
) {

    Card(modifier = Modifier.size(80.dp).padding(8.dp), onClick = { onCategoryClick(categoryItem) }) {

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.product_preview),
                contentDescription = categoryItem.categoryName,
                modifier = Modifier.blur(8.dp)
            )

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.6f)
                    .blur(8.dp),
                color = Color(0xFFF6BD60)
            ) {

            }

            Text(
                text = categoryItem.categoryName,
                fontSize = 12.sp,
                fontFamily = pally,
                color = Color.White
            )
        }


    }


}