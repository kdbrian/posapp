package io.apptales.minipos.service.impl;

import io.apptales.minipos.data.StockTransactionType;
import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.domain.dto.ProductDto;
import io.apptales.minipos.service.PosService;
import io.apptales.minipos.service.ProductsCrudService;
import io.apptales.minipos.service.StockTransactionsService;
import io.apptales.minipos.util.errors.NotFoundException;
import io.apptales.minipos.util.mappers.ProductDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PosServiceImpl implements PosService {

    private final ProductsCrudService productsCrudService;
    private final ProductDtoMapper productDtoMapper;
    private final StockTransactionsService stockTransactionsService;

    public PosServiceImpl(ProductsCrudService productsCrudService, ProductDtoMapper productDtoMapper, StockTransactionsService stockTransactionsService) {
        this.productsCrudService = productsCrudService;
        this.productDtoMapper = productDtoMapper;
        this.stockTransactionsService = stockTransactionsService;
    }

    @Override
    public Product addProduct(String addedBy, ProductDto productDto) {
        Product product = productsCrudService.saveProduct(productDtoMapper.fromDto(productDto));
        //TODO:send a notification to <subscribed>clients or sth like that - use added by
        StockTransaction stockTransaction = stockTransactionsService.saveTransaction(
                new StockTransaction(
                        product,
                        addedBy,
                        StockTransactionType.STOCK_IN
                )
        );
        product.setInsertingTransaction(stockTransaction.getTransactionId());
        product.setDateUpdated(System.currentTimeMillis());

        return productsCrudService.saveProduct(product);
    }

    @Override
    public List<Product> addProducts(String addedBy, List<ProductDto> productDtos) {
        return productDtos.stream().map(this::addProduct).toList();
    }

    @Override
    public List<Product> getAllProducts() {
        return productsCrudService.getProducts();
    }

    @Override
    public List<Product> searchProductsWithName(String name) {
        return productsCrudService.getProductWithName(name);
    }

    @Override
    public Product searchProductWithProductId(String productId) {
        return productsCrudService.getProductById(productId);
    }

    @Override
    public Product addProduct(ProductDto dto) {
        Product product = productsCrudService.saveProduct(productDtoMapper.fromDto(dto));

        stockTransactionsService.saveTransaction(
                new StockTransaction(
                        product,
                        "admin".toUpperCase(Locale.getDefault()),
                        StockTransactionType.STOCK_IN
                )
        );
        return product;
    }

    @Override
    public Product updateProduct(ProductDto dto) {
        if (dto.getProductId() == null)
            throw new NotFoundException("!![Missing id]");

        var product = productsCrudService.getProductById(dto.getProductId());

        if (dto.getProductName() != null && !dto.getProductName().equals(product.getProductName()))
            product.setProductName(dto.getProductName());

        if (dto.getProductPrice() != null && !dto.getProductPrice().equals(product.getProductPrice()))
            product.setProductPrice(dto.getProductPrice());

        if (dto.getProductDiscount() != null && !dto.getProductDiscount().equals(product.getProductDiscount()))
            product.setProductDiscount(dto.getProductDiscount());

        if (dto.getImages() != null)
            product.setImages(dto.getImages());

        if (dto.getCategories() != null)
            if (product.getCategories() == null)
                product.setCategories(dto.getCategories());
            else
                product.getCategories().addAll(dto.getCategories());

        product.setDateUpdated(System.currentTimeMillis());

        return productsCrudService.saveProduct(product);
    }

    @Override
    public Product updateProductStock(String productId, Long stockChange, StockTransactionType stockTransactionType) {
        var product = productsCrudService.getProductById(productId);

        switch (stockTransactionType) {
            case STOCK_IN -> product.setStock(product.getStock() + stockChange);
            case STOCK_OUT -> product.setStock(product.getStock() - stockChange);
        }

        product.setDateUpdated(System.currentTimeMillis());
        Product saved = productsCrudService.saveProduct(product);

        stockTransactionsService.saveTransaction(
                new StockTransaction(
                        product,
                        "admin".toUpperCase(Locale.getDefault()),
                        stockTransactionType
                )
        );

        return saved;
    }

    @Override
    public void deleteProduct(String productId) {
        productsCrudService.deleteProductWithId(productId);
    }
}
