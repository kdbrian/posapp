package io.apptales.minipos.controller.graphql;

import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.service.StockTransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StockTransactionController {


    private static final Logger log = LoggerFactory.getLogger(StockTransactionController.class);
    private final StockTransactionsService stockTransactionsService;

    public StockTransactionController(StockTransactionsService stockTransactionsService) {
        this.stockTransactionsService = stockTransactionsService;
    }

    @QueryMapping
    public List<StockTransaction> getAllTransactions(){
        return stockTransactionsService.getAllTransactions();
    }

    @QueryMapping
    public StockTransaction getTransactionWithId(@Argument String id){
        log.info("For id : {}", id);
        return stockTransactionsService.getTransactionWithId(id);
    }

    @QueryMapping
    public List<StockTransaction> getTransactionsForDate(@Argument Long date){
        return stockTransactionsService.getTransactionsForDate(date);
    }

    @QueryMapping
    public List<StockTransaction> getTransactionsBeforeDate(@Argument Long date){
        return stockTransactionsService.getTransactionsBeforeDate(date);
    }

    @QueryMapping
    public List<StockTransaction> getTransactionsAfterDate(@Argument Long date){
        return stockTransactionsService.getTransactionsAfterDate(date);
    }

    @QueryMapping
    public List<StockTransaction> getTransactionsFromDateToDate(@Argument Long fromDate,@Argument Long toDate){
        return stockTransactionsService.getTransactionsFromDateToDate(fromDate,toDate);
    }

    @QueryMapping
    public List<StockTransaction> getTransactionsForProduct(@Argument String productId){
        return stockTransactionsService.getTransactionsForProduct(productId);
    }


    @MutationMapping
    public StockTransaction saveTransaction(@Argument StockTransaction transaction){
        return stockTransactionsService.saveTransaction(transaction);
    }

    @MutationMapping
    public StockTransaction updateTransaction(@Argument String transactionId){
        return stockTransactionsService.updateTransaction(transactionId);
    }

    @MutationMapping
    public boolean deleteTransaction(@Argument String transactionId){
//        TODO: 🤫send notification to admin for confirmation
        stockTransactionsService.deleteTransaction(transactionId);
        return true;
    }



}
