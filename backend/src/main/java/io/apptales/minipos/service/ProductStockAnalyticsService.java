package io.apptales.minipos.service;

import io.apptales.minipos.data.StockTransactionType;
import io.apptales.minipos.data.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductStockAnalyticsService {

    /**
     * Get a summary of product categories and the total count of products in each category.
     * Useful for analyzing the distribution of products across categories.
     */
    Map<String, Long> getProductCategoryDistribution();

    /**
     * Calculate the average, minimum, and maximum prices of products across all categories.
     * Helps in understanding price trends.
     */
    Map<String, Double> getPriceStatistics();

    /**
     * Get a trend of stock additions and deductions over time.
     * Useful for visualizing inventory changes.
     */
    List<Map<String, Object>> getStockTransactionTrends(Long fromDate, Long toDate);

    /**
     * Get the products with the highest stock and lowest stock levels.
     * Helps in identifying overstocked and understocked products.
     */
    Map<String, Product> getStockExtremes();

    /**
     * Calculate the total stock transactions (additions and deductions) for each product.
     * Useful for analyzing which products have the most activity.
     */
    Map<String, Long> getProductTransactionCounts();

    /**
     * Analyze the average time between stock transactions for each product.
     * Useful for understanding stock movement frequency.
     */
    Map<String, Double> getAverageTimeBetweenTransactions();

    /**
     * Get a breakdown of transactions by type (addition/deduction) and by user.
     * Useful for tracking operational efficiency and identifying patterns.
     */
    Map<String, Map<StockTransactionType, Long>> getTransactionBreakdownByUser();

    /**
     * Calculate revenue impact based on products' prices and stock changes over time.
     * Useful for financial forecasting.
     */
    Double getRevenueImpact(Long fromDate, Long toDate);

    /**
     * Identify products that haven't had stock transactions in a given period.
     * Useful for detecting inactive or obsolete inventory.
     */
    List<Product> getInactiveProducts(Long sinceDate);


}
