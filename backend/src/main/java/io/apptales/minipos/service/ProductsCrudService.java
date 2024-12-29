package io.apptales.minipos.service;

import io.apptales.minipos.data.model.Product;

import java.util.List;


public interface ProductsCrudService {

    List<Product> getProducts();

    Product getProductById(String productId);

    List<Product> getProductWithName(String name);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    List<Product> saveProducts(List<Product> products);

    void deleteProduct(Product product);
    void deleteProductWithId(String productId);

    void deleteProducts(List<Product> products);

    void clearProducts();
}
