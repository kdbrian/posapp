package io.kdbrian.minipos.android.data.remote.repo

import src.main.graphql.AddProductMutation
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetProductWithIdQuery
import src.main.graphql.SearchProductsWithNameQuery
import src.main.graphql.UpdateProductStockMutation
import src.main.graphql.type.ProductDto
import src.main.graphql.type.StockTransactionType

interface PosService {

    suspend fun addProduct(product: ProductDto): Result<AddProductMutation.Data>
    suspend fun getAllProducts(): Result<GetAllProductsQuery.Data>
    suspend fun getProductWithId(productId: String): Result<GetProductWithIdQuery.Data>
    suspend fun searchProductsWithName(name: String): Result<SearchProductsWithNameQuery.Data>
    suspend fun updateProductStock(
        productId: String,
        newStock: Int,
        transactiontype: StockTransactionType
    ): Result<UpdateProductStockMutation.Data>



}