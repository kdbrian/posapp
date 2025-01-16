package io.kdbrian.minipos.android.data.remote.repo

import com.apollographql.apollo.ApolloClient
import io.kdbrian.minipos.android.domain.graphql.TransactionsService
import io.kdbrian.minipos.android.util.AppErrors
import src.main.graphql.GetAllTransactionsQuery
import src.main.graphql.GetProductTransactionQuery
import src.main.graphql.GetTransactionWithIdQuery
import src.main.graphql.GetTransactionsAfterDateQuery
import src.main.graphql.GetTransactionsBeforeDateQuery
import src.main.graphql.GetTransactionsBetweenDatesQuery
import src.main.graphql.GetTransactionsForDateQuery

class TransactionsServiceImpl(
    private val apolloClient: ApolloClient
) : TransactionsService {

    override suspend fun getAllTransactions(): Result<GetAllTransactionsQuery.Data> {
        return try {
            val allTransactions = apolloClient.query(GetAllTransactionsQuery()).execute()

            if (allTransactions.hasErrors()){
                Result.failure(Exception("Error fetching all transactions"))
            }else{
                Result.success(allTransactions.data!!)
            }
        }catch (e : Exception){
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getTransactionWithId(id: String): Result<GetTransactionWithIdQuery.Data> {
        return try {
            val allTransactions = apolloClient.query(GetTransactionWithIdQuery(id)).execute()

            if (allTransactions.hasErrors()){
                Result.failure(Exception("Error fetching all transactions"))
            }else{
                Result.success(allTransactions.data!!)
            }
        }catch (e : Exception){
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getTransactionsForDate(date: Double): Result<GetTransactionsForDateQuery.Data> {
        return try {
            val allTransactions = apolloClient.query(GetTransactionsForDateQuery(date)).execute()

            if (allTransactions.hasErrors()){
                Result.failure(Exception("Error fetching all transactions"))
            }else{
                Result.success(allTransactions.data!!)
            }
        }catch (e : Exception){
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getTransactionsBeforeDate(date: Double): Result<GetTransactionsBeforeDateQuery.Data> {
        return try {
            val allTransactions = apolloClient.query(GetTransactionsBeforeDateQuery(date)).execute()

            if (allTransactions.hasErrors()){
                Result.failure(Exception("Error fetching all transactions"))
            }else{
                Result.success(allTransactions.data!!)
            }
        }catch (e : Exception){
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getTransactionsAfterDate(date: Double): Result<GetTransactionsAfterDateQuery.Data> {
        return try {
            val allTransactions = apolloClient.query(GetTransactionsAfterDateQuery(date)).execute()

            if (allTransactions.hasErrors()){
                Result.failure(Exception("Error fetching all transactions"))
            }else{
                Result.success(allTransactions.data!!)
            }
        }catch (e : Exception){
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getTransactionsBetweenDates(
        fromDate: Double,
        toDate: Double
    ): Result<GetTransactionsBetweenDatesQuery.Data> {
        return try {
            val allTransactions = apolloClient.query(GetTransactionsBetweenDatesQuery(fromDate, toDate)).execute()

            if (allTransactions.hasErrors()){
                Result.failure(Exception("Error fetching all transactions"))
            }else{
                Result.success(allTransactions.data!!)
            }
        }catch (e : Exception){
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getProductTransactions(productId: String): Result<GetProductTransactionQuery.Data> {
        return try {
            val allTransactions = apolloClient.query(GetProductTransactionQuery(productId)).execute()

            if (allTransactions.hasErrors()){
                Result.failure(Exception("Error fetching all transactions"))
            }else{
                Result.success(allTransactions.data!!)
            }
        }catch (e : Exception){
            AppErrors.handleErrors(e)
        }
    }
}