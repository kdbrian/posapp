package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.kdbrian.minipos.android.ui.theme.MiniposTheme


@Composable
fun SearchBarWithRecents(
    modifier: Modifier = Modifier,
    recents: MutableList<String>,
    onRecentRemove: (Int) -> Unit,
    query: String,
    placeholder: String = "Search",
    trailingIcon: ImageVector? = Icons.Default.Search,
    leadingIcon: ImageVector? = null,
    onQueryChange: (String) -> Unit,
    keyBoardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIconOnclick: () -> Unit,
) {
    Card(
        modifier= modifier
        ,
        shape = RoundedCornerShape(16.dp),
    ) {
        TextField(value = query,
            onValueChange = onQueryChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
                .border(
                    width = 1.dp, color = Color(0xFF241715), shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            keyboardActions = keyBoardActions,
            keyboardOptions = keyboardOptions,
            placeholder = {
                Text(text = placeholder)
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedPlaceholderColor = Color.DarkGray,
                focusedPlaceholderColor = Color.White
            ),
            trailingIcon = {
                trailingIcon?.let {
                    IconButton(onClick = trailingIconOnclick) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = null,
                            modifier = Modifier
                                .size(35.dp)
                                .background(shape = CircleShape, color = Color(0xFFDD1C1A))
                                .padding(8.dp),
                            tint = Color.White
                        )
                    }
                }
            })

        Spacer(Modifier.height(8.dp))

        if (recents.isEmpty()) {
            Text(
                text = " 👆 type to search",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            recents.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it, modifier = Modifier.padding(8.dp)
                    )

                    IconButton(onClick = {
                        onRecentRemove(recents.indexOf(it))
                    }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchBarWithRecentsPrev(modifier: Modifier = Modifier) {
    MiniposTheme {

        val query by remember { mutableStateOf("") }

        val recents = remember {
            mutableStateListOf(
                "Recent 1", "Recent 2", "Recent 3"
            )
        }

        SearchBarWithRecents(modifier = modifier,
            recents = recents.toMutableList(),
            onRecentRemove = {
                recents.removeAt(it)
            },
            query = query,
            onQueryChange = {},
            trailingIcon = Icons.Default.Delete,
            trailingIconOnclick = {
                recents.clear()
            }
        )
    }
}