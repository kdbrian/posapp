package io.apptales.minipos.controller.graphql;

import io.apptales.minipos.data.dao.StoreDao;
import io.apptales.minipos.data.model.AbsoluteLocation;
import io.apptales.minipos.data.model.Store;
import io.apptales.minipos.domain.dto.StoreDto;
import io.apptales.minipos.util.errors.NotFoundException;
import io.apptales.minipos.util.mappers.StoreDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StoreGraphController {


    private static final Logger log = LoggerFactory.getLogger(StoreGraphController.class);
    private final StoreDao storeDao;
    private final StoreDtoMapper storeDtoMapper;

    public StoreGraphController(StoreDao storeDao, StoreDtoMapper storeDtoMapper) {
        this.storeDao = storeDao;
        this.storeDtoMapper = storeDtoMapper;
    }


    @QueryMapping
    public List<Store> getAllStores() {
        return storeDao.findAll();
    }


    @QueryMapping
    public Store getStoreById(
            @Argument String id
    ) {
        return storeDao.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @QueryMapping
    public List<Store> getStoreByName(@Argument String name) {
        return storeDao.findByStoreName(name);
    }

    @QueryMapping
    public List<Store> getStoreByLocationName(@Argument String name) {
        return storeDao.findByStoreLocation(name);
    }

    @MutationMapping
    public Store addStore(@Argument StoreDto dto) {
        return storeDao.save(storeDtoMapper.fromDto(dto));
    }

    @MutationMapping
    public Store updateStore(@Argument StoreDto dto) {
        if (dto.getStoreId() == null)
            throw new NotFoundException("Missing Id");

        var store = storeDao.findById(dto.getStoreId()).orElseThrow();

        if (dto.getStoreLocation() != null)
            store.setStoreLocation(dto.getStoreLocation());

        if (dto.getStoreName() != null)
            store.setStoreName(dto.getStoreName());

        if (dto.getStoreOwner() != null)
            store.setStoreOwner(dto.getStoreOwner());

        if (dto.getLatitude() != null && dto.getLongitude() != null)
            store.setAbsoluteLocation(
                    new AbsoluteLocation(
                            dto.getLatitude(),
                            dto.getLongitude()
                    )
            );

        return storeDao.save(store);
    }

    @MutationMapping
    public Boolean deleteStore(@Argument String id) {
        if (!storeDao.existsById(id))
            throw new NotFoundException(id);

        storeDao.deleteById(id);
        return true;
    }

}
