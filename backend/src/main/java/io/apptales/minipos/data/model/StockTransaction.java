package io.apptales.minipos.data.model;


import io.apptales.minipos.data.StockTransactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StockTransaction {

    @Id
    private String transactionId;

    @DBRef
    private Product product;


    //TODO: add dbref to users later
    private String addedBy;

    private StockTransactionType transactionType;

    private Long dateAdded = System.currentTimeMillis();

    private Long dateUpdated;

    public StockTransaction() {
    }

    public StockTransaction(Product product, String addedBy, StockTransactionType transactionType) {
        this.product = product;
        this.addedBy = addedBy;
        this.transactionType = transactionType;
    }

    public StockTransaction(String transactionId, Product product, String addedBy, StockTransactionType transactionType) {
        this.transactionId = transactionId;
        this.product = product;
        this.addedBy = addedBy;
        this.transactionType = transactionType;
    }

    protected StockTransaction(String transactionId, String addedBy, StockTransactionType transactionType, Long dateAdded, Long dateUpdated) {
        this.transactionId = transactionId;
        this.addedBy = addedBy;
        this.transactionType = transactionType;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public StockTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(StockTransactionType transactionType) {
        this.transactionType = transactionType;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "StockTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", addedBy='" + addedBy + '\'' +
                ", transactionType=" + transactionType +
                ", dateAdded=" + dateAdded +
                ", product=" + product +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
