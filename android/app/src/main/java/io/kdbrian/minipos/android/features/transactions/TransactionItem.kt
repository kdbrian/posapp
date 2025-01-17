package io.kdbrian.minipos.android.features.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.TextLocals.LocalDefaultTextStyle
import src.main.graphql.fragment.TransactionInfo
import src.main.graphql.type.StockTransactionType
import java.util.Date


@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transactionItem: TransactionInfo,
    onSelect: (TransactionInfo) -> Unit = {},
) {
    //transactionId, dateAdded, dateUpdated, transactionType, product(productId, productName, stock)


    //todo : evaluate the icon, color  and +/- according to transaction type
    val iconColorPair = when (transactionItem.transactionType) {
        StockTransactionType.STOCK_IN -> (Icons.Default.KeyboardArrowUp to "+") to Color.Green
        StockTransactionType.STOCK_OUT -> (Icons.Default.KeyboardArrowDown to "") to Color.Red
        StockTransactionType.UNKNOWN__ -> (Icons.Default.Done to ".") to Color.DarkGray
    }


    Surface(
        onClick = { onSelect(transactionItem) },
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transactionItem.transactionId,
                    style = LocalDefaultTextStyle.current.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                )

                Spacer(Modifier.height(6.dp))

                //pname
                Text(
                    text = transactionItem.product?.productName.toString(),
                    style = LocalDefaultTextStyle.current.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(Modifier.height(6.dp))

                //date
                Text(
                    text = "on ${Date(transactionItem.dateAdded?.toLong() ?: System.currentTimeMillis())}",
                    style = LocalDefaultTextStyle.current.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.LightGray
                    )
                )

            }

            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(12.dp),
                        color = iconColorPair.second
                    )
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(shape = RoundedCornerShape(6.dp), color = iconColorPair.second)
                ) {
                    Icon(
                        imageVector = iconColorPair.first.first,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(Modifier.width(4.dp))

                Text(
                    text = "${iconColorPair.first.second} ${transactionItem.product?.stock}",
                    style = LocalDefaultTextStyle.current.copy(fontSize = 12.sp),
                    color = iconColorPair.second
                )

            }

        }

    }
}


@Preview
@Composable
private fun TransactionItemPrev() {
    MiniposTheme {
//        TransactionItem()
    }
}

