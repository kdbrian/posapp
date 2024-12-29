package io.apptales.minipos.service;

import io.apptales.minipos.data.StockTransactionType;
import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.domain.dto.ProductDto;

import java.util.List;

public interface PosService {
    Product addProduct(String addedBy, ProductDto productDto);
    List<Product> addProducts(String addedBy, List<ProductDto> productDtos);
    List<Product> getAllProducts();
    List<Product> searchProductsWithName(String name);
    Product searchProductWithProductId(String productId);
    Product addProduct(ProductDto dto);
    Product updateProduct(ProductDto dto);
    Product updateProductStock(String productId, Long newStock, StockTransactionType stockTransactionType);
    void deleteProduct(String productId);
}
