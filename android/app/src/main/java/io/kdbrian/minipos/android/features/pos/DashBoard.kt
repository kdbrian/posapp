package io.kdbrian.minipos.android.features.pos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.R
import io.kdbrian.minipos.android.features.products.ProductListing
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.TextLocals.LocalDefaultTextStyle
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllProductsQuery

@Composable
fun DashBoard(
    modifier: Modifier = Modifier,
    productResource: Resource<GetAllProductsQuery.Data>,
) {

    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            StatsCard(
                modifier = Modifier.weight(1f),
                title = "Products",
                stats = if (productResource is Resource.Success) {
                    "${productResource.data?.getProducts?.size}"
                } else
                    "3,000"
            )

            StatsCard(
                modifier = Modifier.weight(1f),
                title = "Transactions",
                stats = "20,000"
            )

        }

        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "Products",
                style = LocalDefaultTextStyle.current.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.weight(1f))

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.round_web_stories_24),
                    contentDescription = null
                )
            }

        }


        ProductListing(
            modifier = Modifier.weight(1f),
            allProductsResource = productResource
        )
    }

}


@Preview
@Composable
private fun DashBoardPrev() {
    MiniposTheme {
        DashBoard(
            Modifier.padding(12.dp),
            productResource = Resource.Loading(),
        )
    }
}

