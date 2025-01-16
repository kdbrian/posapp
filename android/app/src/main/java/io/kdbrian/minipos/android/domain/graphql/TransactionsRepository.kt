package io.kdbrian.minipos.android.domain.graphql

import src.main.graphql.GetAllTransactionsQuery
import src.main.graphql.GetProductTransactionQuery
import src.main.graphql.GetTransactionWithIdQuery
import src.main.graphql.GetTransactionsAfterDateQuery
import src.main.graphql.GetTransactionsBeforeDateQuery
import src.main.graphql.GetTransactionsBetweenDatesQuery
import src.main.graphql.GetTransactionsForDateQuery

interface TransactionsRepository {
    suspend fun getAllTransactions(): Result<GetAllTransactionsQuery.Data>
    suspend fun getTransactionWithId(id: String): Result<GetTransactionWithIdQuery.Data>
    suspend fun getTransactionsForDate(date: Double): Result<GetTransactionsForDateQuery.Data>
    suspend fun getTransactionsBeforeDate(date: Double): Result<GetTransactionsBeforeDateQuery.Data>
    suspend fun getTransactionsAfterDate(date: Double): Result<GetTransactionsAfterDateQuery.Data>
    suspend fun getTransactionsBetweenDates(
        fromDate: Double,
        toDate: Double
    ): Result<GetTransactionsBetweenDatesQuery.Data>

    suspend fun getProductTransactions(productId: String): Result<GetProductTransactionQuery.Data>

}