package io.kdbrian.minipos.android.domain.graphql

import src.main.graphql.AddProductMutation
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetAllProductsWithImagesQuery
import src.main.graphql.GetProductWithIdQuery
import src.main.graphql.SearchProductsWithNameQuery
import src.main.graphql.UpdateProductStockMutation
import src.main.graphql.type.ProductDto
import src.main.graphql.type.StockTransactionType

interface ProductsService {

    suspend fun getAllProducts(): Result<GetAllProductsQuery.Data>
    suspend fun getProductWithId(id : String): Result<GetProductWithIdQuery.Data>
    suspend fun addProduct(dto: ProductDto): Result<AddProductMutation.Data>
    suspend fun getAllProductsWithImages(): Result<GetAllProductsWithImagesQuery.Data>
    suspend fun searchProductsWithName(name : String): Result<SearchProductsWithNameQuery.Data>
    suspend fun updateProductStock(
        productId: String,
        newStock: Int,
        transactionType: StockTransactionType
    ): Result<UpdateProductStockMutation.Data>

}