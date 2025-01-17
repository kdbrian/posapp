package io.apptales.minipos.util.mappers;

import io.apptales.minipos.data.model.AbsoluteLocation;
import io.apptales.minipos.data.model.Store;
import io.apptales.minipos.domain.dto.StoreDto;
import org.springframework.stereotype.Service;

@Service
public class StoreDtoMapper extends EntityToDtoMapper<Store, StoreDto> {

    @Override
    public Store fromDto(StoreDto storeDto) {
        return new Store(
                storeDto.getStoreOwner(),
                storeDto.getStoreName(),
                storeDto.getStoreLocation(),
                new AbsoluteLocation(
                        storeDto.getLatitude(),
                        storeDto.getLongitude()
                )
        );
    }

    @Override
    public StoreDto toDto(Store store) {
        return new StoreDto(
                store.getStoreId(),
                store.getStoreOwner(),
                store.getStoreName(),
                store.getStoreLocation(),
                store.getAbsoluteLocation().latitude(),
                store.getAbsoluteLocation().longitude()
        );
    }

}
