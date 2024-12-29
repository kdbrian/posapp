package io.apptales.minipos.service.impl;

import io.apptales.minipos.data.dao.ProductsDao;
import io.apptales.minipos.data.dao.StockTransactionDao;
import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.service.ProductStockStreamingService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RestController
@RequestMapping("/streaming")
public class ProductStockStreamingServiceImpl implements ProductStockStreamingService {

    private final ProductsDao productsDao;
    private final StockTransactionDao stockTransactionDao;

    public ProductStockStreamingServiceImpl(ProductsDao productsDao, StockTransactionDao stockTransactionDao) {
        this.productsDao = productsDao;
        this.stockTransactionDao = stockTransactionDao;
    }

    @GetMapping("/test")
    public String test() {
        return "Am a tea pot.";
    }

    @GetMapping(value = "/product/{productId}/stock-updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamStockUpdates(@PathVariable String productId) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> {
                    io.apptales.minipos.data.model.Product product = productsDao.findById(productId).orElse(null);
                    if (product != null) {
                        return "Stock level for product " + productId + ": " + product.getStock();
                    }
                    return "Product not found";
                });
    }

    @GetMapping(value = "/product/{productId}/transaction-activities", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamTransactionActivities(@PathVariable String productId) {
        return Flux.interval(Duration.ofSeconds(2))
                .map(tick -> {
                    List<StockTransaction> transactions = stockTransactionDao.findByProduct(
                            productsDao.findById(productId).orElse(null));
                    return transactions.stream()
                            .map(tx -> "Transaction: " + tx.getTransactionType() + " by " + tx.getAddedBy())
                            .collect(Collectors.joining(", "));
                });
    }

    @Override
    public void streamRevenueImpact(String productId) {

    }

    @Override
    public void streamStockStatus(String productId) {

    }
}
