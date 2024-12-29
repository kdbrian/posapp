package io.kdbrian.minipos.android.ui.composables

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.kdbrian.minipos.android.util.CategoryItem
import io.kdbrian.minipos.android.util.Resource
import timber.log.Timber

@Composable
fun HorizontalProductCategoriesList(
    modifier: Modifier = Modifier,
    categoriesResource: Resource<List<CategoryItem>>,
    onItemSelect: (CategoryItem) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        when (categoriesResource) {
            is Resource.Error -> {
                Text(
                    text = categoriesResource.message.toString(), modifier = modifier.align(
                        Alignment.Center
                    )
                )
                Timber.d("err ${categoriesResource.message}")
            }

            is Resource.Loading -> {
                //TODO: use shimmers
            }

            is Resource.Success -> {
                categoriesResource.data?.let { categories ->
                    LazyRow(modifier = Modifier.fillMaxWidth()) {
                        items(items = categories) {
                            ProductCategoryCard(
                                categoryItem = it,
                                onCategoryClick = onItemSelect
                            )
                        }
                    }
                }
            }
        }
    }

}