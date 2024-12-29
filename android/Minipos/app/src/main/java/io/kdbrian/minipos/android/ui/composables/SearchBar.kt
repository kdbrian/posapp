package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.kdbrian.minipos.android.ui.theme.MiniposTheme


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    placeholder: String = "Search",
    onQueryChange: (String) -> Unit,
    keyBoardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        keyboardActions = keyBoardActions,
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(text = placeholder)
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedPlaceholderColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
            focusedTextColor = Color.DarkGray,
            unfocusedTextColor = Color.DarkGray,
        )
    )
}

@Preview
@Composable
private fun SearchBarPrev() {
    MiniposTheme {
        val query by remember { mutableStateOf("") }
        SearchBar(modifier = Modifier, query, onQueryChange = {})
    }
}