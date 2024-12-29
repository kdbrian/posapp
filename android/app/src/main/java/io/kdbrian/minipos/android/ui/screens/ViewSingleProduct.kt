package io.kdbrian.minipos.android.ui.screens

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.kdbrian.minipos.android.BuildConfig
import io.kdbrian.minipos.android.LocalFontFamily
import io.kdbrian.minipos.android.R
import io.kdbrian.minipos.android.ui.theme.pally
import src.main.graphql.GetProductWithIdQuery
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewSingleProduct(
    modifier: Modifier = Modifier,
    productId: String,
    product: GetProductWithIdQuery.Data?,
    onNavigateBack: (GetProductWithIdQuery.Data?) -> Unit = {}
) {

    val productInfo = product?.getProductWithId

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Product Details",
                            fontSize = 18.sp,
                            fontFamily = LocalFontFamily.current
                        )
                        Text(text = "#$productId", color = Color.LightGray, fontSize = 14.sp)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateBack(product)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        var currentImage by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            productInfo?.let {
                Column(
                    modifier = Modifier.align(Alignment.TopCenter),
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Box {

                            productInfo.images?.let {
                                Timber.d("Url ${BuildConfig.ngrokHost}${productInfo.images[0]}")
                                //TODO: optionally load image metadata and resize accordingly
                                //TODO: Add background animations and image scroll
                                currentImage = "${BuildConfig.ngrokHost}${productInfo.images[0]}"

                                AsyncImage(
                                    model = currentImage,
                                    contentDescription = productInfo.productName,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    ,
                                    placeholder = painterResource(R.drawable.product_preview),
                                    contentScale = ContentScale.Crop,
                                )
                            } ?: kotlin.run {
                                Image(
                                    painter = painterResource(R.drawable.product_preview),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .align(Alignment.Center)
                                    ,
                                )
                            }
                        }

                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = productInfo.productName,
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontFamily = LocalFontFamily.current
                            )
                        )

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    //insert dynamic price
                                    append("$")
                                }

                                withStyle(
                                    SpanStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xFFD96C06),
                                        fontFamily = pally
                                    )
                                ) {
                                    append(productInfo.productPrice.toString())
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                        )
                    }


                }

            }
        }
    }

}