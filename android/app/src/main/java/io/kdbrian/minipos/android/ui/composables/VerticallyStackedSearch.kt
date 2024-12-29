package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kdbrian.minipos.android.R
import io.kdbrian.minipos.android.ui.theme.pally

@Composable
fun VerticallyStackedSearch(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onFilter: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
        ,
    ) {

        SearchBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            query = searchQuery,
            onQueryChange = onQueryChange,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyBoardActions = KeyboardActions(
                onSearch = { onSearch(searchQuery) }
            )
        )


        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            color = Color.LightGray,
            contentColor = Color.Black,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .padding(8.dp),
            onClick = onFilter

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.round_filter_list_24),
                    contentDescription = "filter",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Filter By",
                    fontSize = 10.sp,

                    )
            }


        }

    }
}