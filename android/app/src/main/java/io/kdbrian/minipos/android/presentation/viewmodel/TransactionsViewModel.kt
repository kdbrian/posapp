package io.kdbrian.minipos.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import io.kdbrian.minipos.android.domain.graphql.TransactionsService
import io.kdbrian.minipos.android.data.remote.repo.TransactionsServiceImpl
import io.kdbrian.minipos.android.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import src.main.graphql.GetAllTransactionsQuery
import src.main.graphql.GetProductTransactionQuery
import src.main.graphql.GetTransactionWithIdQuery
import src.main.graphql.GetTransactionsAfterDateQuery
import src.main.graphql.GetTransactionsBeforeDateQuery
import src.main.graphql.GetTransactionsBetweenDatesQuery
import src.main.graphql.GetTransactionsForDateQuery

class TransactionsViewModel(
    private val transactionsService: TransactionsService
) : ViewModel() {

    private val _allTransactions: MutableSharedFlow<Resource<GetAllTransactionsQuery.Data>> =
        MutableSharedFlow()

    val allTransactions: SharedFlow<Resource<GetAllTransactionsQuery.Data>>
        get() = _allTransactions

    private val _transactionWithId: MutableSharedFlow<Resource<GetTransactionWithIdQuery.Data>> =
        MutableSharedFlow()

    val transactionWithId: SharedFlow<Resource<GetTransactionWithIdQuery.Data>>
        get() = _transactionWithId

    private val _transactionForDate: MutableSharedFlow<Resource<GetTransactionsForDateQuery.Data>> =
        MutableSharedFlow()

    val transactionForDate: SharedFlow<Resource<GetTransactionsForDateQuery.Data>>
        get() = _transactionForDate

    private val _transactionBeforeDate: MutableSharedFlow<Resource<GetTransactionsBeforeDateQuery.Data>> =
        MutableSharedFlow()

    val transactionBeforeDate: SharedFlow<Resource<GetTransactionsBeforeDateQuery.Data>>
        get() = _transactionBeforeDate

    private val _transactionAfterDate: MutableSharedFlow<Resource<GetTransactionsAfterDateQuery.Data>> =
        MutableSharedFlow()
    val transactionAfterDate: SharedFlow<Resource<GetTransactionsAfterDateQuery.Data>>
        get() = _transactionAfterDate

    private val _transactionBetweenDates: MutableSharedFlow<Resource<GetTransactionsBetweenDatesQuery.Data>> =
        MutableSharedFlow()
    val transactionBetweenDates: SharedFlow<Resource<GetTransactionsBetweenDatesQuery.Data>>
        get() = _transactionBetweenDates

    private val _productTransactions: MutableSharedFlow<Resource<GetProductTransactionQuery.Data>> =
        MutableSharedFlow()
    val productTransactions: SharedFlow<Resource<GetProductTransactionQuery.Data>>
        get() = _productTransactions


    init {

        viewModelScope.launch {
            transactionsService.getAllTransactions().fold(
                onSuccess = {
                    _allTransactions.emit(Resource.Success(it))
                },
                onFailure = {
                    _allTransactions.emit(Resource.Error(it.message.toString()))
                }
            )
        }
    }

    fun getTransactionWithId(productId: String) {
        viewModelScope.launch {
            transactionsService.getTransactionWithId(productId).fold(
                onSuccess = {
                    _transactionWithId.emit(Resource.Success(it))
                },
                onFailure = {
                    _transactionWithId.emit(Resource.Error(it.message.toString()))
                }
            )
        }
    }

    fun getTransactionsForDate(date: Double) {
        viewModelScope.launch {
            transactionsService.getTransactionsForDate(date).fold(
                onSuccess = {
                    _transactionForDate.emit(Resource.Success(it))
                },

                onFailure = {
                    _transactionForDate.emit(Resource.Error(it.message.toString()))
                }
            )
        }
    }

    fun getTransactionsBeforeDate(date: Double) {
        viewModelScope.launch {
            transactionsService.getTransactionsBeforeDate(date).fold(
                onSuccess = {
                    _transactionBeforeDate.emit(Resource.Success(it))
                },
                onFailure = {
                    _transactionBeforeDate.emit(Resource.Error(it.message.toString()))
                }
            )
        }
    }

    fun getTransactionsAfterDate(date: Double) {
        viewModelScope.launch {
            transactionsService.getTransactionsAfterDate(date).fold(
                onSuccess = {
                    _transactionAfterDate.emit(Resource.Success(it))
                },

                onFailure = {
                    _transactionAfterDate.emit(Resource.Error(it.message.toString()))
                }
            )
        }

    }

    fun getTransactionsBetweenDates(fromDate: Double, toDate: Double) {
        viewModelScope.launch {
            transactionsService.getTransactionsBetweenDates(fromDate, toDate).fold(
                onSuccess = {
                    _transactionBetweenDates.emit(Resource.Success(it))
                },
                onFailure = {
                    _transactionBetweenDates.emit(Resource.Error(it.message.toString()))
                }
            )
        }
    }

    fun getProductTransactions(productId: String) {
        viewModelScope.launch {
            transactionsService.getProductTransactions(productId).fold(
                onSuccess = {
                    _productTransactions.emit(Resource.Success(it))
                },

                onFailure = {
                    _productTransactions.emit(Resource.Error(it.message.toString()))
                }
            )
        }
    }

}

@Suppress("UNCHECKED_CAST")
class TransactionsViewModelProvider(
    private val apolloClient: ApolloClient
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val transactionsService = TransactionsServiceImpl(apolloClient)
        return TransactionsViewModel(transactionsService) as T
    }
}