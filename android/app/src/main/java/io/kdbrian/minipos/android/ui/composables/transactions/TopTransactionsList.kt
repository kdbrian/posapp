package io.kdbrian.minipos.android.ui.composables.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.LocalFontFamily
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.ui.theme.pally
import io.kdbrian.minipos.android.util.DatePatterns
import io.kdbrian.minipos.android.util.Extension
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllTransactionsQuery
import src.main.graphql.type.StockTransactionType
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID
import kotlin.random.Random

@Composable
fun TopTransactionsList(
    modifier: Modifier = Modifier,
    data: List<GetAllTransactionsQuery.GetAllTransaction?>
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Top Transactions",
            fontFamily = LocalFontFamily.current,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        data.forEach {
            TopTransactionItem(
                modifier = Modifier,
                transaction = it!!
            )
        }
    }

}


@Composable
fun TopTransactionItem(
    modifier: Modifier = Modifier,
    transaction: GetAllTransactionsQuery.GetAllTransaction
) {
    val info = transaction.transactionInfo

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(8.dp),
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {

            Column {

                Text(
                    text = info.transactionId,
                    fontFamily = LocalFontFamily.current,
                    fontSize = 10.sp,
                    color = Color(0xFF88958D)
                )

                Text(
                    text = info.product?.productName.toString(),
                    fontFamily = pally,
                    fontSize = 18.sp,
                    letterSpacing = 4.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(Modifier.width(12.dp))

            Row {

                val types = listOf(
                    StockTransactionType.STOCK_IN,
                    StockTransactionType.STOCK_OUT,
                )

                val (icon, tint) = when (types[
                    Random.nextInt(
                        types.size - 1
                    )
                ]) {
                    StockTransactionType.STOCK_IN -> {
                        Pair(Icons.Default.Add, Color.Green)
                    }

                    StockTransactionType.STOCK_OUT -> {
                        Pair(Icons.Default.Clear, Color.Red)
                    }

                    else -> {
                        Pair(Icons.Default.Add, Color.Green)
                    }
                }

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "${info.product?.stock}",
                    fontFamily = LocalFontFamily.current,
                    fontSize = 26.sp,
                    color = Color(0xFFEB6424)
                )
            }

        }


        Text(
            text = "Added : ${
                Extension.formatDateTime(
                    info.dateAdded?.toLong() ?: 0L,
                    DatePatterns.READABLE_DATE_TIME
                )
            }"
        )

        HorizontalDivider()
    }

}


@Preview
@Composable
private fun TopTransactionItemPrev() {
    MiniposTheme {
//        TopTransactionItem()
    }
}



