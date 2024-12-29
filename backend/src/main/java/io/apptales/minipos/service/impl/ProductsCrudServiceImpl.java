package io.apptales.minipos.service.impl;

import io.apptales.minipos.data.dao.ProductsDao;
import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.service.ProductsCrudService;
import io.apptales.minipos.util.errors.InvalidIdentifierFormatException;
import io.apptales.minipos.util.errors.NotFoundException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsCrudServiceImpl implements ProductsCrudService {

    private static final Logger log = LoggerFactory.getLogger(ProductsCrudServiceImpl.class);
    private final ProductsDao productsDao;


    public ProductsCrudServiceImpl(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    @Override
    public List<Product> getProducts() {
        return productsDao.findAll();
    }

    @Override
    public Product getProductById(String productId) {
        return productsDao.findById(productId)
                .orElseThrow(() -> new NotFoundException(productId));
    }

    @Override
    public List<Product> getProductWithName(String name) {
        log.info("got {}", name);
        return productsDao.findByProductNameContaining(name);
    }

    @Override
    public Product saveProduct(Product product) {
        return productsDao.save(product);
    }

    @Override
    public Product updateProduct(Product product) {

        if (!ObjectId.isValid(product.getProductId()))
            throw new InvalidIdentifierFormatException(product.getProductId());

        var forUpdate = productsDao.findById(product.getProductId())
                .orElseThrow(() -> new NotFoundException(product.getProductId()));

        forUpdate.setProductName(product.getProductName());
        forUpdate.setProductPrice(product.getProductPrice());
        forUpdate.setProductDiscount(product.getProductDiscount());
        forUpdate.setStock(product.getStock());
        forUpdate.setDateUpdated(System.currentTimeMillis());

        return productsDao.save(forUpdate);
    }

    @Override
    public List<Product> saveProducts(List<Product> products) {
        return productsDao.saveAll(products);
    }

    @Override
    public void deleteProduct(Product product) {
        productsDao.delete(product);
    }

    @Override
    public void deleteProductWithId(String productId) {
        if (productsDao.existsById(productId))
            productsDao.deleteById(productId);
        else
            throw new NotFoundException(productId);
    }

    @Override
    public void deleteProducts(List<Product> products) {
        productsDao.deleteAll(products);
    }

    @Override
    public void clearProducts() {
        productsDao.deleteAll();
    }
}
