package io.apptales.minipos.service.impl;

import io.apptales.minipos.data.StockTransactionType;
import io.apptales.minipos.data.dao.ProductsDao;
import io.apptales.minipos.data.dao.StockTransactionDao;
import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.service.ProductStockAnalyticsService;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductStockAnalyticsServiceImpl implements ProductStockAnalyticsService {

    private final StockTransactionDao stockTransactionDao;
    private final ProductsDao productsDao;

    public ProductStockAnalyticsServiceImpl(StockTransactionDao stockTransactionDao, ProductsDao productsDao) {
        this.stockTransactionDao = stockTransactionDao;
        this.productsDao = productsDao;
    }

    @Override
    public Map<String, Long> getProductCategoryDistribution() {
        return productsDao.findAll().stream()
                .flatMap(product -> product.getCategories().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public Map<String, Double> getPriceStatistics() {
        List<Product> products = productsDao.findAll();
        DoubleSummaryStatistics stats = products.stream()
                .mapToDouble(Product::getProductPrice)
                .summaryStatistics();
        Map<String, Double> result = new HashMap<>();
        result.put("average", stats.getAverage());
        result.put("min", stats.getMin());
        result.put("max", stats.getMax());
        return result;
    }

    @Override
    public List<Map<String, Object>> getStockTransactionTrends(Long fromDate, Long toDate) {
        List<StockTransaction> transactions = stockTransactionDao.findByDateAddedBetween(fromDate, toDate);
        return transactions.stream()
                .collect(Collectors.groupingBy(tx -> new Date(tx.getDateAdded()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> trendData = new HashMap<>();
                    trendData.put("date", entry.getKey());
                    trendData.put("transactionCount", entry.getValue().size());
                    return trendData;
                }).collect(Collectors.toList());
    }


    @Override
    public Map<String, Product> getStockExtremes() {
        List<Product> products = productsDao.findAll();
        Product minStock = products.stream().min(Comparator.comparing(Product::getStock)).orElse(null);
        Product maxStock = products.stream().max(Comparator.comparing(Product::getStock)).orElse(null);
        return Map.of("lowestStock", minStock, "highestStock", maxStock);
    }

    @Override
    public Map<String, Long> getProductTransactionCounts() {
        return stockTransactionDao.findAll().stream()
                .collect(Collectors.groupingBy(tx -> tx.getProduct().getProductId(), Collectors.counting()));
    }

    @Override
    public Map<String, Double> getAverageTimeBetweenTransactions() {
        return stockTransactionDao.findAll().stream()
                .collect(Collectors.groupingBy(
                        tx -> tx.getProduct().getProductId(),
                        Collectors.mapping(
                                StockTransaction::getDateAdded,
                                Collectors.toList()
                        )))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<Long> dates = entry.getValue();
                            Collections.sort(dates);
                            List<Long> intervals = IntStream.range(1, dates.size())
                                    .mapToObj(i -> dates.get(i) - dates.get(i - 1))
                                    .toList();
                            return intervals.stream().mapToLong(Long::longValue).average().orElse(0.0);
                        }
                ));
    }

    @Override
    public Map<String, Map<StockTransactionType, Long>> getTransactionBreakdownByUser() {
        return stockTransactionDao.findAll().stream()
                .collect(Collectors.groupingBy(
                        StockTransaction::getAddedBy,
                        Collectors.groupingBy(
                                StockTransaction::getTransactionType,
                                Collectors.counting()
                        )));
    }

    @Override
    public Double getRevenueImpact(Long fromDate, Long toDate) {
        List<StockTransaction> transactions = stockTransactionDao.findByDateAddedBetween(fromDate, toDate);
        return transactions.stream()
                .mapToDouble(tx -> tx.getTransactionType() == StockTransactionType.STOCK_IN
                        ? tx.getProduct().getProductPrice() * tx.getProduct().getStock()
                        : -tx.getProduct().getProductPrice() * tx.getProduct().getStock())
                .sum();
    }

    @Override
    public List<Product> getInactiveProducts(Long sinceDate) {
        List<String> activeProductIds = stockTransactionDao.findByDateAddedAfter(sinceDate).stream()
                .map(tx -> tx.getProduct().getProductId())
                .distinct()
                .collect(Collectors.toList());
        return productsDao.findAll().stream()
                .filter(product -> !activeProductIds.contains(product.getProductId()))
                .collect(Collectors.toList());
    }
}
