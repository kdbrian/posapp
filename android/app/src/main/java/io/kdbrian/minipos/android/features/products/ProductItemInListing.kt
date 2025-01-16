package io.kdbrian.minipos.android.features.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.R
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.TextLocals.LocalDefaultTextStyle
import io.kdbrian.minipos.android.ui.theme.moneyOrange
import src.main.graphql.fragment.ProductBasicInfo
import java.util.Locale


@Composable
fun ProductItemInListing(
    modifier: Modifier = Modifier,
    shape : Shape = RoundedCornerShape(12.dp),
    productInfo : ProductBasicInfo
) {
    Surface(
        onClick = { },
        shape = shape,
        modifier = modifier.fillMaxWidth(),
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = productInfo.productName,
                    style = LocalDefaultTextStyle.current.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    //price + stock count

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.round_attach_money_24),
                            contentDescription = "price"
                        )

                        Text(
                            text = LoremIpsum(2).values.joinToString(),
                            style = LocalDefaultTextStyle.current.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )


                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.round_inventory_24),
                            contentDescription = "price"
                        )

                        //todo: format according to user settings
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = moneyOrange)){
                                    append(String.format(Locale.getDefault(), "%.2f",productInfo.productPrice))
                                }
                            },
                            style = LocalDefaultTextStyle.current.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        )


                    }

                }


            }


            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }


    }

}


@Preview
@Composable
private fun ProductItemInListingPrev() {
    MiniposTheme {
//        ProductItemInListing()
    }
}

