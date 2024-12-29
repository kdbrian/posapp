package io.apptales.minipos.util.mappers;

import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.domain.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper extends EntityToDtoMapper<Product, ProductDto> {

    @Override
    public Product fromDto(ProductDto productDto) {
        return new Product(
                productDto.getProductId(),
                productDto.getProductName(),
                productDto.getProductPrice(),
                productDto.getProductDiscount(),
                productDto.getStock(),
                productDto.getImages(),
                productDto.getCategories(),
                productDto.getDateAdded(),
                productDto.getDateUpdated()
        );
    }

    @Override
    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getInsertingTransaction(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductDiscount(),
                product.getImages(),
                product.getCategories(),
                product.getStock(),
                product.getDateAdded(),
                product.getDateUpdated()
        );
    }
}
