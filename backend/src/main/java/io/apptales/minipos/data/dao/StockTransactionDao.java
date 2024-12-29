package io.apptales.minipos.data.dao;

import io.apptales.minipos.data.StockTransactionType;
import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.data.model.StockTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTransactionDao extends MongoRepository<StockTransaction, String> {

    List<StockTransaction> findByProduct(Product product);

    List<StockTransaction> findByDateAdded(Long date);

    List<StockTransaction> findByDateUpdated();

    List<StockTransaction> findByAddedBy();

    List<StockTransaction> findByTransactionType(StockTransactionType stockTransactionType);

    List<StockTransaction> findByDateAddedBetween(Long fromDate, Long toDate);

    List<StockTransaction> findByDateAddedBefore(Long date);

    List<StockTransaction> findByDateAddedAfter(Long date);

}
