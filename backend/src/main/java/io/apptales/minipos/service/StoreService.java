package io.apptales.minipos.service;

import io.apptales.minipos.data.model.Store;

import java.util.List;

public interface StoreService {
    List<Store> getAllStores();
    Store addStore();

}
