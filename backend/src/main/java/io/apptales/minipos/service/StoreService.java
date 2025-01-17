package io.apptales.minipos.service;

import io.apptales.minipos.data.model.Store;
import io.apptales.minipos.domain.dto.StoreDto;

import java.util.List;

public interface StoreService {

    List<Store> getAllStores();

    Store getStoreById(String storeId);

    List<Store> getStoreByName(String storeName);

    List<Store> getStoreByLocation(String locationName);

    Store addStore(StoreDto dto);

    Store updateStore(StoreDto dto);

    void deleteStore(String storeId);


}
