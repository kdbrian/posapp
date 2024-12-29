package io.kdbrian.minipos.android.ui.composables.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import io.kdbrian.minipos.android.LocalFontFamily
import io.kdbrian.minipos.android.R
import io.kdbrian.minipos.android.ui.composables.Toasting
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.util.ResolveFileFromUri.extractBasicMetadata
import io.kdbrian.minipos.android.util.ResolveFileFromUri.getFileNameFromUri


@Preview
@Composable
private fun AddImagePrev() {

    var imageUri by remember { mutableStateOf(Uri.EMPTY) }

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )

    val (isImageExpanded, expandImage) = remember { mutableStateOf(true) }


    MiniposTheme {
        AddImage.AddSingleImage(
            imageUri = imageUri,
            onImageUriChange = { passedUri ->
                if (passedUri != null) {
                    imageUri = passedUri
                } else
                    selectImageLauncher.launch("image/*")
            },
            modifier = Modifier,
            isExpanded = isImageExpanded,
            onExpand = expandImage
        )
    }
}

object AddImage {


    @Composable
    fun AddSingleImage(
        modifier: Modifier = Modifier
            .padding(4.dp),
        isExpanded: Boolean,
        onExpand: (Boolean) -> Unit,
        imageUri: Uri = Uri.EMPTY,
        onImageUriChange: (Uri?) -> Unit,
    ) {

        val context = LocalContext.current
        var imageName by remember {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = imageUri) {
            if (imageUri != Uri.EMPTY) {
                val fileNameFromUri = context.getFileNameFromUri(imageUri)
                imageName = fileNameFromUri.toString()
            } else
                imageName = ""
        }
        var showImageMetadata by remember {
            mutableStateOf(false)
        }

        val infoIcon by remember {
            mutableStateOf(
                if (showImageMetadata)
                    Icons.Rounded.Clear
                else
                    Icons.Rounded.Info
            )
        }

        val weights by animateFloatAsState(if (isExpanded) 1f else 0.5f, label = "")
        val icon = if (isExpanded) painterResource(R.drawable.fullscreen_exit_24dp)
        else painterResource(R.drawable.fullscreen_24dp_e8eaed_fill0_wght400_grad0_opsz24)

        Box(
            modifier = modifier
                .background(shape = RoundedCornerShape(12.dp), color = Color.White)
                .fillMaxSize(weights),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x80000000)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(0f, 1000f)
                        )
                    )
                    .clickable {
                        onExpand(!isExpanded)
                    },

                ) {

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onImageUriChange(Uri.EMPTY)
                    },
                    modifier = Modifier.background(shape = CircleShape, color = Color.LightGray)
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }


                Crossfade(imageUri) {
                    if (it != Uri.EMPTY) {
                        BadgedBox(
                            badge = {
                                Icon(
                                    imageVector = infoIcon, contentDescription = null,
                                    modifier = Modifier
                                        .clickable {
                                            showImageMetadata = !showImageMetadata
                                        })
                            }
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontFamily = LocalFontFamily.current,
                                            textDecoration = TextDecoration.Underline,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append(imageName)
                                    }

                                },
                                modifier = Modifier,
                            )
                        }
                    }
                }

                Crossfade(isExpanded, label = "") {
                    if (it) {
                        IconButton(
                            onClick = {
                                onExpand(!isExpanded)
                            },
                            modifier = Modifier.background(
                                shape = CircleShape,
                                color = Color.LightGray
                            )
                        ) {
                            Icon(
                                painter = icon,
                                contentDescription = null
                            )
                        }
                    }
                }

            }

            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    Text(
                        text = if (isExpanded) {
                            if (imageUri == Uri.EMPTY)
                                "Add Image"
                            else
                                "Change Image"
                        } else ""
                    )

                    IconButton(onClick = { onImageUriChange(null) }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_camera_24),
                            contentDescription = null
                        )
                    }
                }
            }


            if (showImageMetadata && isExpanded) {
                Toasting.ToastComposable(metadata = context.extractBasicMetadata(imageUri))
            }
        }


    }


    @Composable
    fun AddMultipleImages(
        modifier: Modifier = Modifier,
        images : Int
    ) {
    }
}