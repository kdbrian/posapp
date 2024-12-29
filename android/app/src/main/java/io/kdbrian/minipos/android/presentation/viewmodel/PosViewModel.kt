package io.kdbrian.minipos.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import io.kdbrian.minipos.android.data.remote.repo.PosService
import io.kdbrian.minipos.android.data.remote.repo.impl.PosServiceImpl
import io.kdbrian.minipos.android.util.CategoryItem
import io.kdbrian.minipos.android.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import src.main.graphql.GetAllProductsQuery
import src.main.graphql.GetProductTransactionQuery
import src.main.graphql.GetProductWithIdQuery
import timber.log.Timber

class PosViewModel(
    private val posService: PosService
) : ViewModel() {

    private val _products: MutableSharedFlow<Resource<GetAllProductsQuery.Data>> =
        MutableSharedFlow()
    val products: SharedFlow<Resource<GetAllProductsQuery.Data>>
        get() = _products

    private val _productCategories: MutableSharedFlow<Resource<List<CategoryItem>>> =
        MutableSharedFlow()
    val productCategories: SharedFlow<Resource<List<CategoryItem>>>
        get() = _productCategories

    private val _selectedProductWithId: MutableSharedFlow<Resource<GetProductWithIdQuery.Data>> =
        MutableSharedFlow()

    val selectedProductWithId: SharedFlow<Resource<GetProductWithIdQuery.Data>>
        get() = _selectedProductWithId

    private val _filteredProducts: MutableSharedFlow<Resource<GetProductWithIdQuery.Data>> =
        MutableSharedFlow()

    val filteredProducts: SharedFlow<Resource<GetProductWithIdQuery.Data>>
        get() = _filteredProducts

    private val _aggregateProducts: MutableSharedFlow<Resource<GetProductTransactionQuery.Data>> =
        MutableSharedFlow()
    val aggregateProducts: SharedFlow<Resource<GetProductTransactionQuery.Data>>
        get() = _aggregateProducts


    private val _groupedProducts: MutableSharedFlow<Resource<GetAllProductsQuery.Data>> =
        MutableSharedFlow()
    val groupedProducts: SharedFlow<Resource<GetAllProductsQuery.Data>>
        get() = _groupedProducts

    init {
        viewModelScope.launch {

            posService.getAllProducts().fold(
                onSuccess = { data ->
                    Timber.d("Got $data")
                    _products.emit(Resource.Success(data))
                    data.getProducts?.apply {
                        _productCategories.emit(
                            Resource.Success(map {
                                CategoryItem(
                                    it?.productId?.substring(7).toString()
                                )
                            })
                        )

                        _groupedProducts.emit(
                            Resource.Success(data)
                        )
                    }
                },
                onFailure = {
                    Timber.d("Failed $it ${it.message}")
                    _groupedProducts.emit(Resource.Error(it.message.toString()))
                    _products.emit(Resource.Error(it.message.toString()))
                    _productCategories.emit(Resource.Error(it.message.toString()))
                }
            )
        }
    }


    suspend fun getProductWithId(productId: String) {
        return posService.getProductWithId(productId).fold(
            onSuccess = {
                _selectedProductWithId.emit(
                    Resource.Success(it)
                )
            },
            onFailure = {
                _selectedProductWithId.emit(
                    Resource.Error(it.message.toString())
                )
            }
        )
    }

    fun getAggregateProducts(productId: String) {
        viewModelScope.launch {
//            posService.getAggregateProducts(productId).fold(
//                onSuccess = {
//                    _aggregateProducts.emit(
//                        Resource
        }
    }

}

@Suppress("UNCHECKED_CAST")
class PosViewModelProvider(
    private val apolloClient: ApolloClient
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val posService = PosServiceImpl(apolloClient)
        return PosViewModel(posService) as T
    }
}


