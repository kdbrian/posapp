package io.apptales.minipos.data.dao;

import io.apptales.minipos.data.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDao extends MongoRepository<Store, String> {
    List<Store> findByStoreOwner(String owner);

    List<Store> findByStoreName(String name);

    List<Store> findByStoreLocation(String location);

}
