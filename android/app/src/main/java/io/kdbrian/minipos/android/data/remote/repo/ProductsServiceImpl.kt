package io.kdbrian.minipos.android.data.remote.repo

import com.apollographql.apollo.ApolloClient
import io.kdbrian.minipos.android.domain.graphql.ProductsService
import src.main.graphql.AddProductMutation
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetAllProductsWithImagesQuery
import src.main.graphql.GetProductWithIdQuery
import src.main.graphql.SearchProductsWithNameQuery
import src.main.graphql.UpdateProductStockMutation
import src.main.graphql.type.ProductDto
import src.main.graphql.type.StockTransactionType

class ProductsServiceImpl(
    private val apolloClient: ApolloClient,
) : ProductsService {

    override suspend fun getAllProducts(): Result<GetAllProductsQuery.Data> {
        return apolloClient.query(GetAllProductsQuery()).execute().let { result ->
            if (result.hasErrors())
                Result.failure(Exception(result.errors.toString()))
            else
                Result.success(result.data!!)
        }
    }

    override suspend fun getProductWithId(id: String): Result<GetProductWithIdQuery.Data> {
        return apolloClient.query(GetProductWithIdQuery(id)).execute().let { result ->
            if (result.hasErrors())
                Result.failure(Exception(result.errors.toString()))
            else
                Result.success(result.data!!)

        }
    }

    override suspend fun addProduct(dto: ProductDto): Result<AddProductMutation.Data> {
        return apolloClient.mutation(AddProductMutation(dto)).execute().let { result ->
            if (result.hasErrors())
                Result.failure(Exception(result.errors.toString()))
            else
                Result.success(result.data!!)

        }
    }

    override suspend fun getAllProductsWithImages(): Result<GetAllProductsWithImagesQuery.Data> {
        return apolloClient.query(GetAllProductsWithImagesQuery()).execute().let { result ->
            if (result.hasErrors())
                Result.failure(Exception(result.errors.toString()))
            else
                Result.success(result.data!!)
        }
    }

    override suspend fun searchProductsWithName(name: String): Result<SearchProductsWithNameQuery.Data> {
        return apolloClient.query(SearchProductsWithNameQuery(name)).execute().let { result ->
            if (result.hasErrors())
                Result.failure(Exception(result.errors.toString()))
            else
                Result.success(result.data!!)
        }
    }

    override suspend fun updateProductStock(
        productId: String,
        newStock: Int,
        transactionType: StockTransactionType,
    ): Result<UpdateProductStockMutation.Data> {
        return apolloClient.mutation(
            UpdateProductStockMutation(
                productId,
                newStock,
                transactionType
            )
        ).execute().let { result ->
            if (result.hasErrors())
                Result.failure(Exception(result.errors.toString()))
            else
                Result.success(result.data!!)
        }
    }

}