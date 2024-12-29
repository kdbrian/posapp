package io.apptales.minipos.service;

import io.apptales.minipos.data.model.StockTransaction;
import org.bson.Document;

import java.util.List;

public interface StockTransactionsService {


    List<StockTransaction> getAllTransactions();

    StockTransaction saveTransaction(StockTransaction transaction);

    StockTransaction updateTransaction(String transactionId);

    List<StockTransaction> getTransactionsForDate(Long date);

    List<StockTransaction> getTransactionsBeforeDate(Long date);

    List<StockTransaction> getTransactionsAfterDate(Long date);

    List<StockTransaction> getTransactionsFromDateToDate(Long fromDate, Long toDate);

    List<StockTransaction> getTransactionsForProduct(String productId);

    void deleteTransaction(String transactionId);

    StockTransaction getTransactionWithId(String id);
}
