package io.kdbrian.minipos.android.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.LocalFontFamily
import io.kdbrian.minipos.android.ui.theme.pally
import io.kdbrian.minipos.android.util.Resource
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.RowChart
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetAllTransactionsQuery

@Composable
fun Portfolio(
    modifier: Modifier = Modifier,
    allProductsResource: Resource<GetAllProductsQuery.Data>,
    allTransactionsResource: Resource<GetAllTransactionsQuery.Data>,
) {

    val lines = remember {
        mutableListOf<Line>()
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {

        Text(
            text = "Portfolio", style = TextStyle(
                fontSize = 30.sp,
                fontFamily = pally
            )
        )

        HorizontalDivider()

        Text(
            text = buildAnnotatedString {

                withStyle(
                    SpanStyle(
                        fontSize = 24.sp,
                        fontFamily = pally,
                        color = Color.Black
                    )
                ) {
                    append("$")
                }

                withStyle(
                    SpanStyle(
                        fontFamily = LocalFontFamily.current,
                        fontSize = 20.sp
                    )
                ) {
                    append("100,000.99")
                }
            },
            fontSize = 30.sp,
            fontFamily = pally,
            modifier = Modifier
        )



        when (allProductsResource) {
            is Resource.Error -> {
            }

            is Resource.Loading -> {
            }

            is Resource.Success -> {
                val products = allProductsResource.data?.getProducts
                products?.let { productList ->
                    val productQuantities = productList.map { it?.stock?.toDouble() ?: 0.0 }

                    lines.add(
                        Line(
                            label = "Product Quantities",
                            values = productQuantities,
                            color = Brush.linearGradient(
                                listOf(
                                    Color(0xFF56E39F),
                                    Color(0xFF56E39F)
                                )
                            ),
                            firstGradientFillColor = Color(0xFF56E39F),
                            secondGradientFillColor = Color(0xFF56E39F),
                            drawStyle = DrawStyle.Stroke(4.dp),
                            strokeAnimationSpec = tween(durationMillis = 500),
                            gradientAnimationSpec = tween(durationMillis = 800),
                            gradientAnimationDelay = 100,
                            dotProperties = DotProperties(

                                radius = 6.dp,
                                strokeWidth = 2.dp
                            ),
                            popupProperties = PopupProperties(
                                enabled = true,
                                containerColor = Color(0xAA000000)
                            ),
                            curvedEdges = true,
                        )
                    )
                }
            }
        }

        when (allTransactionsResource) {
            is Resource.Error -> {
            }

            is Resource.Loading -> {
            }

            is Resource.Success -> {
                val transactions = allTransactionsResource.data?.getAllTransactions
                transactions?.let { transactionsList ->
                    val transactionsQuery = transactionsList.map { it.transactionInfo.product?.stock?.toDouble() ?: 0.0 }

                    lines.add(
                        Line(
                            label = "Product Quantities",
                            values = transactionsQuery,
                            color = Brush.linearGradient(
                                listOf(
                                    Color(0xFFAD343E),
                                    Color(0xFFF2AF29)
                                )
                            ),
                            firstGradientFillColor = Color(0xFFF2AF29),
                            secondGradientFillColor = Color(0xFFAD343E),
                            drawStyle = DrawStyle.Stroke(4.dp),
                            strokeAnimationSpec = tween(durationMillis = 500),
                            gradientAnimationSpec = tween(durationMillis = 800),
                            gradientAnimationDelay = 100,
                            dotProperties = DotProperties(

                                radius = 6.dp,
                                strokeWidth = 2.dp
                            ),
                            popupProperties = PopupProperties(
                                enabled = true,
                                containerColor = Color(0xAA000000)
                            ),
                            curvedEdges = true,
                        )
                    )
                }
            }
        }



        LineChart(
            data = lines,
            modifier = Modifier.fillMaxWidth()
                .height(200.dp),
        )

    }


}