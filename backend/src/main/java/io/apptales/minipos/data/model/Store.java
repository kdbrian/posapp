package io.apptales.minipos.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
public class Store {

    @Id
    private String storeId;

    private String storeOwner;

    private String storeName;

    private String storeLocation;

    private AbsoluteLocation absoluteLocation;

    public Store() {
    }

    public Store(String storeId, String storeOwner, String storeName, String storeLocation, AbsoluteLocation absoluteLocation) {
        this.storeId = storeId;
        this.storeOwner = storeOwner;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.absoluteLocation = absoluteLocation;
    }

    public Store(String storeOwner, String storeName, String storeLocation, AbsoluteLocation absoluteLocation) {
        this.storeOwner = storeOwner;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.absoluteLocation = absoluteLocation;
    }

    public Store(String storeOwner, String storeName, String storeLocation) {
        this.storeOwner = storeOwner;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(String storeOwner) {
        this.storeOwner = storeOwner;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public AbsoluteLocation getAbsoluteLocation() {
        return absoluteLocation;
    }

    public void setAbsoluteLocation(AbsoluteLocation absoluteLocation) {
        this.absoluteLocation = absoluteLocation;
    }

    @Override
    public String toString() {
        return "Store{" +
                "storeId='" + storeId + '\'' +
                ", storeOwner='" + storeOwner + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeLocation='" + storeLocation + '\'' +
                ", absoluteLocation=" + absoluteLocation +
                '}';
    }
}
