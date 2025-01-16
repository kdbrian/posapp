package io.kdbrian.minipos.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apollographql.apollo.ApolloClient
import io.kdbrian.minipos.android.data.remote.repo.ProductsRepositoryImpl
import io.kdbrian.minipos.android.domain.graphql.ProductsRepository
import io.kdbrian.minipos.android.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import src.main.graphql.GetAllProductsQuery
import timber.log.Timber

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    private val _allProducts: MutableSharedFlow<Resource<GetAllProductsQuery.Data>> =
        MutableSharedFlow()
    val allProducts get() = _allProducts.asSharedFlow()


    init {
        viewModelScope.launch {
            getAllProducts()
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _allProducts.emit(Resource.Loading())
            _allProducts.emit(
                productsRepository.getAllProducts().fold(
                    onSuccess = {
                        Timber.d("prods $it")
                        Resource.Success(it)
                    },
                    onFailure = {
                        Timber.d("err $it")
                        Resource.Error(it.message.toString())
                    }
                )
            )
        }

    }


    @Suppress("UNCHECKED_CAST")
    internal class Factory(
        private val apolloClient: ApolloClient,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val productsRepo: ProductsRepository = ProductsRepositoryImpl(apolloClient)
            return ProductsViewModel(productsRepo) as T
        }
    }

}