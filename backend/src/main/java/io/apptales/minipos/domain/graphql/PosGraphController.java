package io.apptales.minipos.domain.graphql;

import io.apptales.minipos.data.StockTransactionType;
import io.apptales.minipos.data.model.Product;
import io.apptales.minipos.domain.dto.ProductDto;
import io.apptales.minipos.service.PosService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PosGraphController {

    private final PosService posService;


    public PosGraphController(PosService posService) {
        this.posService = posService;
    }



    @QueryMapping(name = "getAllProducts")
    public List<Product> getAllProducts() {
        return posService.getAllProducts();
    }


    @QueryMapping
    public List<Product> searchProductsWithName(@Argument(name = "name") String name) {
        //TODO: 🤑 stream product searches for most searched products
        return posService.searchProductsWithName(name);
    }

    @QueryMapping
    public Product searchProductWithProductId(@Argument(name = "productId") String productId) {
        return posService.searchProductWithProductId(productId);
    }

    @MutationMapping(name = "updateProductStock")
    public Product updateProductStock(
            @Argument(name = "productId") String productId,
            @Argument(name = "newStock") Long newStock,
            @Argument(name = "stockTransactionType") String stockTransactionType
    ) {
        return posService.updateProductStock(productId, newStock, StockTransactionType.valueOf(stockTransactionType));
    }

    @MutationMapping(name = "updatePosProduct")
    public Product updateProductStock(
            @Argument(name = "dto") ProductDto dto
    ) {
        return posService.updateProduct(dto);
    }

    @MutationMapping(name = "addProduct")
    public Product addProduct(
            @Argument(name = "addedBy") String addedBy,
            @Argument(name = "dto") ProductDto dto
    ) {
        return posService.addProduct(addedBy, dto);
    }

}
