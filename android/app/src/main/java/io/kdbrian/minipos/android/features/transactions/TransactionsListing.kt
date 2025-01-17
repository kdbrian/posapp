package io.kdbrian.minipos.android.features.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllTransactionsQuery

@Composable
fun TransactionsViewModel(
    modifier: Modifier = Modifier,
    transactionResource: Resource<GetAllTransactionsQuery.Data> = Resource.Nothing()
) {

}