package io.apptales.minipos.data.dao;

import io.apptales.minipos.data.model.Images;
import io.apptales.minipos.data.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesDao extends MongoRepository<Images, String> {
    List<Images> findByProduct(Product product);
}
