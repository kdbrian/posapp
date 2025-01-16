package io.kdbrian.minipos.android.data.remote.repo

import com.apollographql.apollo.ApolloClient
import io.kdbrian.minipos.android.domain.graphql.PosService
import io.kdbrian.minipos.android.util.AppErrors
import src.main.graphql.AddProductMutation
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetProductWithIdQuery
import src.main.graphql.SearchProductsWithNameQuery
import src.main.graphql.UpdateProductStockMutation
import src.main.graphql.type.ProductDto
import src.main.graphql.type.StockTransactionType

class PosServiceImpl(
    private val apolloClient: ApolloClient
) : PosService {

    override suspend fun addProduct(product: ProductDto): Result<AddProductMutation.Data> {
        return try {
            val response = apolloClient.mutation(AddProductMutation(product)).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors.toString()))
            } else
                Result.success(response.data!!)
        } catch (e: Exception) {
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getAllProducts(): Result<GetAllProductsQuery.Data> {
        return try {
            val response = apolloClient.query(GetAllProductsQuery()).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors.toString()))
            } else
                Result.success(response.data!!)
        } catch (e: Exception) {
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun getProductWithId(productId: String): Result<GetProductWithIdQuery.Data> {
        return try {
            val response = apolloClient.query(GetProductWithIdQuery(productId)).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors.toString()))
            } else
                Result.success(response.data!!)
        } catch (e: Exception) {
            AppErrors.handleErrors(e)
        }
    }

    override suspend fun searchProductsWithName(name: String): Result<SearchProductsWithNameQuery.Data> {
        return try {
            val response = apolloClient.query(SearchProductsWithNameQuery(name)).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors.toString()))
            } else
                Result.success(response.data!!)
        } catch (e: Exception) {
            AppErrors.handleErrors(e)
        }

    }

    override suspend fun updateProductStock(
        productId: String,
        newStock: Int,
        transactiontype: StockTransactionType
    ): Result<UpdateProductStockMutation.Data> {
        return try {
            val response = apolloClient.mutation(UpdateProductStockMutation(productId, newStock, transactiontype)).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors.toString()))
            } else
                Result.success(response.data!!)
        } catch (e: Exception) {
            AppErrors.handleErrors(e)
        }
        TODO("Not yet implemented")
    }

}