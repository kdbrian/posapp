package io.apptales.minipos.domain.rest;

import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.service.StockTransactionsService;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class StockTransactionsController {

    private final StockTransactionsService stockTransactionsService;

    public StockTransactionsController(StockTransactionsService stockTransactionsService) {
        this.stockTransactionsService = stockTransactionsService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<StockTransaction>> getProductTransactions(
            @PathVariable String productId
    ){
        return ResponseEntity.ok(stockTransactionsService.getTransactionsForProduct(productId));
    }
}
