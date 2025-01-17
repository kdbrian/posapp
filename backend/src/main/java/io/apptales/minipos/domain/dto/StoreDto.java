package io.apptales.minipos.domain.dto;

public class StoreDto {

    private String storeOwner;

    private String storeId;

        private String storeName;

    private String storeLocation;

    private Long latitude;

    private Long longitude;

    public StoreDto() {
    }

    public StoreDto(String storeId, String storeOwner, String storeName, String storeLocation, Long latitude, Long longitude) {
        this.storeId = storeId;
        this.storeOwner = storeOwner;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StoreDto(String storeOwner, String storeName, String storeLocation, Long latitude, Long longitude) {
        this.storeOwner = storeOwner;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "StoreDto{" +
                "storeOwner='" + storeOwner + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeLocation='" + storeLocation + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
