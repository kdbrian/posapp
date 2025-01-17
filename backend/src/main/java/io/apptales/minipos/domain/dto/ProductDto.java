package io.apptales.minipos.domain.dto;

import io.apptales.minipos.data.model.Store;

import java.util.HashSet;
import java.util.Set;

public class ProductDto {

    private String productId;

    private String store;

    transient private String productInsertionTransaction;

    private String productName;

    private Double productPrice;

    private Double productDiscount;

    private Set<String> images;
    private Set<String> categories;

    private Long stock;

    private Long dateAdded = System.currentTimeMillis();

    private Long dateUpdated = System.currentTimeMillis();

    public ProductDto() {
    }

    public ProductDto(String productId, String store, String productInsertionTransaction, String productName, Double productPrice, Double productDiscount, Set<String> images, Set<String> categories, Long stock, Long dateAdded, Long dateUpdated) {
        this.productId = productId;
        this.store = store;
        this.productInsertionTransaction = productInsertionTransaction;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.images = images;
        this.categories = categories;
        this.stock = stock;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
    }

    public ProductDto(String productName, Double productPrice, Double productDiscount, Long stock, String productInsertionTransaction) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.stock = stock;
        this.productInsertionTransaction = productInsertionTransaction;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(Double productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Long getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Long dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getProductInsertionTransaction() {
        return productInsertionTransaction;
    }

    public void setProductInsertionTransaction(String productInsertionTransaction) {
        this.productInsertionTransaction = productInsertionTransaction;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        if (this.images == null)
            this.images = new HashSet<>();
        this.images.add(image);
    }

    public Set<String> setCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category) {
        if (this.categories == null)
            this.categories = new HashSet<>();
        this.categories.add(category);
    }

    public Set<String> getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId='" + productId + '\'' +
                ", productInsertionTransaction='" + productInsertionTransaction + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDiscount=" + productDiscount +
                ", images=" + images +
                ", categories=" + categories +
                ", stock=" + stock +
                ", dateAdded=" + dateAdded +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
