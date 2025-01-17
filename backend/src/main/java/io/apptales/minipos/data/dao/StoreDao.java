package io.apptales.minipos.data.dao;

import io.apptales.minipos.data.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDao extends MongoRepository<Store, String> {
}
