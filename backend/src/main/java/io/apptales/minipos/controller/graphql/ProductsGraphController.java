package io.apptales.minipos.controller.graphql;

import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.data.model.StockTransaction;
import io.apptales.minipos.domain.dto.ProductDto;
import io.apptales.minipos.service.ProductsCrudService;
import io.apptales.minipos.util.mappers.ProductDtoMapper;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductsGraphController {

    private final ProductsCrudService productsCrudService;
    private final ProductDtoMapper productDtoMapper;

    public ProductsGraphController(ProductsCrudService productsCrudService, ProductDtoMapper productDtoMapper) {
        this.productsCrudService = productsCrudService;
        this.productDtoMapper = productDtoMapper;
    }

    @QueryMapping(name = "getProducts")
    public List<Product> getProducts() {
        return productsCrudService.getProducts();
    }

    @QueryMapping(name = "getProductWithId")
    public Product getProductWithId(@Argument String id) {
        return productsCrudService.getProductById(id);
    }


    @QueryMapping(name = "getProductsWithName")
    public List<Product> getProductsWithName(@Argument String name) {
        return productsCrudService.getProductWithName(name);
    }

    @MutationMapping(name = "saveProduct")
    public Product saveProduct(@Argument(name = "dto") ProductDto product) {
        return productsCrudService.saveProduct(productDtoMapper.fromDto(product));
    }

    @MutationMapping(name = "updateProduct")
    public Product updateProduct(@Argument(name = "dto") ProductDto product) {
        return productsCrudService.updateProduct(productDtoMapper.fromDto(product));
    }

    @MutationMapping(name = "saveProducts")
    public List<Product> saveProducts(@Argument(name = "dto") ProductDto... products) {
        return productsCrudService.saveProducts(
                Arrays.stream(products).map(productDtoMapper::fromDto).toList()
        );
    }

    @MutationMapping(name = "deleteProductWithId")
    public boolean deleteProduct(@Argument(name = "id") String productId) {
        productsCrudService.deleteProductWithId(productId);
        return true;
    }

    @MutationMapping(name = "deleteProducts")
    public void deleteProducts(@Argument(name = "dto") ProductDto... products) {
        productsCrudService.deleteProducts(
                Arrays.stream(products).map(productDtoMapper::fromDto).toList()
        );
    }

    @MutationMapping(name = "clearProducts")
    synchronized public boolean clearProducts(){
        productsCrudService.clearProducts();
        return true;
    }

}
