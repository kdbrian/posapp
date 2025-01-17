package io.kdbrian.minipos.android.features.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.kdbrian.minipos.android.ui.composables.CenteredText
import io.kdbrian.minipos.android.ui.theme.MiniposTheme
import io.kdbrian.minipos.android.util.Resource
import src.main.graphql.GetAllTransactionsQuery

@Composable
fun TransactionsListing(
    modifier: Modifier = Modifier,
    transactionResource: Resource<GetAllTransactionsQuery.Data> = Resource.Loading(),
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (transactionResource) {
            is Resource.Error -> CenteredText(message = transactionResource.message.toString())
            is Resource.Loading -> CenteredText(message = "Fetching transactions..")
            is Resource.Nothing -> CenteredText()
            is Resource.Success -> {
                transactionResource.data?.getAllTransactions?.let { transactions ->
                    if (transactions.isEmpty()) {
                        CenteredText(message = "No transactions yet.")
                    } else {

                        if (transactions.size > 100) {
                            LazyColumn(modifier = modifier) {
                                items(transactions.map { it.transactionInfo }){
                                    TransactionItem(
                                        transactionItem = it,
                                        onSelect = {  }
                                    )
                                    HorizontalDivider()
                                }
                            }
                        } else {
                            val verticalScroll = rememberScrollState()
                            Column(
                                modifier = modifier
                                    .fillMaxSize()
                                    .verticalScroll(verticalScroll)
                            ) {
                                transactions.map { it.transactionInfo }.forEach {
                                    TransactionItem(
                                        transactionItem = it,
                                        onSelect = {  }
                                    )

                                    HorizontalDivider()
                                }
                            }
                        }

                    }
                }
            }
        }
    }


}


@Preview
@Composable
private fun TransactionsListingPrev() {
    MiniposTheme {
        TransactionsListing()
    }
}

