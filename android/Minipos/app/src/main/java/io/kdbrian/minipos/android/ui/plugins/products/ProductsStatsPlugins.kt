package io.kdbrian.minipos.android.ui.plugins.products

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.presentation.viewmodel.PosViewModel
import io.kdbrian.minipos.android.presentation.viewmodel.TransactionsViewModel
import io.kdbrian.minipos.android.ui.theme.pally
import io.kdbrian.minipos.android.util.Resource
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetAllTransactionsQuery
import src.main.graphql.type.StockTransactionType
import timber.log.Timber
import java.util.Locale

object ProductsStatsPlugins {

    @Composable
    fun ProductsStatsBannerWithProductsTransactionsPieChart(
        modifier: Modifier = Modifier,
        allProductsResource: Resource<GetAllProductsQuery.Data>,
        allTransactionsResource: Resource<GetAllTransactionsQuery.Data>
    ) {
        // Initialize state values
        var productsRatio by remember { mutableDoubleStateOf(50.0) }
        var stockInTransactionsRatio by remember { mutableDoubleStateOf(50.0) }
        var stockOutTransactionsRatio by remember { mutableDoubleStateOf(50.0) }

        // Update product ratio based on the products data
        productsRatio = when (allProductsResource) {
            is Resource.Error -> 0.0
            is Resource.Loading -> 50.0
            is Resource.Success -> allProductsResource.data?.getProducts?.size?.toDouble() ?: 0.0
        }

        // Use LaunchedEffect to recalculate transaction ratios when `allTransactionsResource` changes
        LaunchedEffect(allTransactionsResource) {
            // Calculate transaction ratios based on transaction data
            allTransactionsResource.data?.getAllTransactions?.let { transactions ->
                val totalTransactions = transactions.size
                val stockInTransactions =
                    transactions.count { it.transactionInfo.transactionType == StockTransactionType.STOCK_IN }
                val stockOutTransactions =
                    transactions.count { it.transactionInfo.transactionType == StockTransactionType.STOCK_OUT }

                stockInTransactionsRatio = if (totalTransactions > 0) {
                    (stockInTransactions.toDouble() / totalTransactions) * 100
                } else {
                    50.0
                }

                stockOutTransactionsRatio = if (totalTransactions > 0) {
                    (stockOutTransactions.toDouble() / totalTransactions) * 100
                } else {
                    50.0
                }
            }
        }

        // Pie chart data
        val pieList = listOf(
            Pie(
                label = "Products",
                selected = true,
                data = productsRatio,
                color = Color(0xFFF4A261) // Product color
            ),
            Pie(
                label = "Stock In",
                data = stockInTransactionsRatio,
                color = Color(0xFF56E39F) // Stock In color
            ),
            Pie(
                label = "Stock Out",
                data = stockOutTransactionsRatio,
                color = Color(0xFFFA9500) // Stock Out color
            )
        )

        // Column layout
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            var selectedPie by remember { mutableStateOf(pieList.first()) }
            var chartData by remember { mutableStateOf(pieList) }

            // Row with text and pie chart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Text display for selected pie
                Column {
                    Text(
                        text = selectedPie.label.toString(),
                        fontSize = 20.sp,
                        fontFamily = pally,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = String.format(Locale.getDefault(),"%.2f %%", selectedPie.data),
                        color = selectedPie.color,
                        fontSize = 50.sp
                    )
                }

                // Pie chart with interaction
                PieChart(
                    data = chartData,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .size(150.dp)
                        .weight(1f),
                    onPieClick = {
                        println("${it.label} Clicked")
                        val pieIndex = chartData.indexOf(it)
                        selectedPie = chartData[pieIndex]
                        chartData =
                            chartData.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
                    },
                    selectedScale = 1.2f,
                    scaleAnimEnterSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    colorAnimEnterSpec = tween(300),
                    colorAnimExitSpec = tween(300),
                    scaleAnimExitSpec = tween(300),
                    spaceDegreeAnimExitSpec = tween(300),
                    style = Pie.Style.Stroke()
                )
            }
        }
    }


}