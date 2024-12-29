package io.apptales.minipos.domain.rest;

import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.service.StockTransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-transactions")
public class StockTransactionsController {

    private final StockTransactionsService stockTransactionsService;

    public StockTransactionsController(StockTransactionsService stockTransactionsService) {
        this.stockTransactionsService = stockTransactionsService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<StockTransaction>> getProductTransactions(
            @PathVariable String productId
    ) {
        return ResponseEntity.ok(stockTransactionsService.getTransactionsForProduct(productId));
    }


    @GetMapping("/trends")
    public ResponseEntity<Map<String, Integer>> getTrends(
            @RequestParam(value = "main", required = false) String main
    ) {

        if (main != null) {
            switch (main.toLowerCase()) {

                case "stockinvsstockout", "stockinstockout", "stock" -> {
                    return ResponseEntity.ok(stockTransactionsService.getStockInVsOutCount());
                }


                case "cat","categories" -> {
                    return ResponseEntity.ok(stockTransactionsService.getProductsByCategories());
                }
            }
        }

        return ResponseEntity.ok(Map.of("Missing query parameter", 0));

    }
}
