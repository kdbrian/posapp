package io.apptales.minipos.service;

import io.apptales.minipos.data.model.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImagesService {
    List<Images> getImages();
    List<Images> getProductImages(String productId);
    Images saveProductImage(String productId, MultipartFile image) throws IOException;
    Images saveShopImage(String shopId, MultipartFile image) throws IOException;
    Images saveProductImages(String productId, List<MultipartFile> image);
    void deleteImage(String imageId);
}
