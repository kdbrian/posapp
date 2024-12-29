package io.apptales.minipos.service.impl;

import io.apptales.minipos.data.StockTransactionType;
import io.apptales.minipos.data.dao.ProductsDao;
import io.apptales.minipos.data.dao.StockTransactionDao;
import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.service.StockTransactionsService;
import io.apptales.minipos.util.errors.InvalidIdentifierFormatException;
import io.apptales.minipos.util.errors.NotFoundException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StockTransactionsServiceImpl implements StockTransactionsService {

    private static final Logger log = LoggerFactory.getLogger(StockTransactionsServiceImpl.class);
    private final StockTransactionDao transactionDao;
    private final MongoTemplate mongoTemplate;
    private final ProductsDao productsDao;

    public StockTransactionsServiceImpl(StockTransactionDao transactionDao, MongoTemplate mongoTemplate, ProductsDao productsDao) {
        this.transactionDao = transactionDao;
        this.mongoTemplate = mongoTemplate;
        this.productsDao = productsDao;
    }


    @Override
    public List<StockTransaction> getAllTransactions() {
        return transactionDao.findAll();
    }

    @Override
    public StockTransaction saveTransaction(StockTransaction transaction) {
        return transactionDao.save(transaction);
    }

    @Override
    public StockTransaction updateTransaction(String transactionId) {

        if (!ObjectId.isValid(transactionId))
            throw new InvalidIdentifierFormatException(transactionId);

        var opttransaction = transactionDao.findById(transactionId);

        if (opttransaction.isPresent()) {
            StockTransaction stockTransaction = opttransaction.get();

            if (!productsDao.existsById(stockTransaction.getProduct().getProductId()))
                throw new NotFoundException(stockTransaction.getProduct().getProductId());

            stockTransaction.setDateUpdated(System.currentTimeMillis());
            if (stockTransaction.getTransactionType() == StockTransactionType.STOCK_IN)
                stockTransaction.setTransactionType(StockTransactionType.STOCK_OUT);
            else
                stockTransaction.setTransactionType(StockTransactionType.STOCK_IN);

            return transactionDao.save(stockTransaction);
        }

        throw new NotFoundException(transactionId);
    }

    @Override
    public List<StockTransaction> getTransactionsForDate(Long date) {
//        log.info("Date passed : {}\t {}", date, new Date(date));
        return transactionDao.findByDateAdded(date);
    }

    @Override
    public List<StockTransaction> getTransactionsBeforeDate(Long date) {
        return transactionDao.findByDateAddedBefore(date);
    }

    @Override
    public List<StockTransaction> getTransactionsAfterDate(Long date) {
        return transactionDao.findByDateAddedAfter(date);
    }

    @Override
    public List<StockTransaction> getTransactionsFromDateToDate(Long fromDate, Long toDate) {
        log.info("From : {} To : {}\n", new Date(fromDate), new Date(toDate));
        log.info("From : {} To : {} Diff : {}\n", fromDate, toDate, toDate - fromDate);
        return transactionDao.findByDateAddedBetween(fromDate - 1000, toDate + 1000);
    }

    @Override
    public List<StockTransaction> getTransactionsForProduct(String productId) {
        var product = productsDao.findById(productId)
                .orElseThrow(() -> new NotFoundException(productId));
        return transactionDao.findByProduct(product);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        if (!ObjectId.isValid(transactionId))
            throw new InvalidIdentifierFormatException(transactionId);

        if (!transactionDao.existsById(transactionId))
            throw new NotFoundException(transactionId);

        transactionDao.deleteById(transactionId);
    }

    @Override
    public StockTransaction getTransactionWithId(String id) {

        if (!ObjectId.isValid(id))
            throw new InvalidIdentifierFormatException(id);

        return transactionDao.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }


}
