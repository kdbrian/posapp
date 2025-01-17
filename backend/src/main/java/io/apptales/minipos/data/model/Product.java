package io.apptales.minipos.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
public class Product {

    @Id
    private String productId;

    @Indexed(unique = true)
    private String productName;

    private Double productPrice;

    private Double productDiscount;

    private Long stock;

    private Set<String> images;

    private Set<String> categories;

    private String insertingTransaction;

    private Long dateAdded = System.currentTimeMillis();

    private Long dateUpdated = System.currentTimeMillis();

    @DBRef
    private Store store;

    public Product() {
    }

    public Product(String productId, String productName, Double productPrice, Double productDiscount, Long stock, Set<String> images, Set<String> categories, String insertingTransaction, Long dateAdded, Long dateUpdated, Store store) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.stock = stock;
        this.images = images;
        this.categories = categories;
        this.insertingTransaction = insertingTransaction;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
        this.store = store;
    }

    public Product(String productName, Double productPrice, Double productDiscount, Long stock, Set<String> categories, String insertingTransaction) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.stock = stock;
        this.categories = categories;
        this.insertingTransaction = insertingTransaction;
    }

    public Product(String productId, String productName, Double productPrice, Double productDiscount, Long stock, Set<String> categories) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.stock = stock;
        this.categories = categories;
    }

    public Product(String productName, Double productPrice, Double productDiscount, Long stock, List<String> images, Set<String> categories) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.stock = stock;
        this.images = new HashSet<>(images);
        this.categories = categories;
    }

    public void addImage(String imageLink) {
        if (this.images == null)
            this.images = new HashSet<>();
        this.images.add(imageLink);
    }

    public void addCategory(String category) {
        if (this.categories == null)
            this.categories = new HashSet<>();
        this.images.add(category);
    }


    public Product(String productId, String productName, Double productPrice, Double productDiscount, Long stock, Set<String> images, Set<String> categories, String insertingTransaction, Long dateAdded, Long dateUpdated) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.stock = stock;
        this.images = images;
        this.categories = categories;
        this.insertingTransaction = insertingTransaction;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
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

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public String getInsertingTransaction() {
        return insertingTransaction;
    }

    public void setInsertingTransaction(String insertingTransaction) {
        this.insertingTransaction = insertingTransaction;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDiscount=" + productDiscount +
                ", stock=" + stock +
                ", images=" + images +
                ", categories=" + categories +
                ", insertingTransaction='" + insertingTransaction + '\'' +
                ", dateAdded=" + dateAdded +
                ", dateUpdated=" + dateUpdated +
                ", store=" + store +
                '}';
    }
}
