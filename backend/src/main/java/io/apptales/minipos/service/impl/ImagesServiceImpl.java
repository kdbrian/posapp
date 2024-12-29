package io.apptales.minipos.service.impl;

import io.apptales.minipos.data.dao.ImagesDao;
import io.apptales.minipos.data.dao.ProductsDao;
import io.apptales.minipos.data.model.ImageMetadata;
import io.apptales.minipos.data.model.Images;
import io.apptales.minipos.service.ImagesService;
import io.apptales.minipos.util.errors.InvalidIdentifierFormatException;
import io.apptales.minipos.util.errors.NotFoundException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ImagesServiceImpl implements ImagesService {

    private static final Logger log = LoggerFactory.getLogger(ImagesServiceImpl.class);
    @Value("${file.upload-dir-product-images}")
    private String uploadsFolder;

    private final ImagesDao imagesDao;
    private final ProductsDao productsDao;


    public ImagesServiceImpl(ImagesDao imagesDao, ProductsDao productsDao) {
        this.imagesDao = imagesDao;
        this.productsDao = productsDao;
    }

    @Override
    public List<Images> getImages() {
        return imagesDao.findAll();
    }

    @Override
    public List<Images> getProductImages(String productId) {
        CheckForProduct(productId);
        var product = productsDao.findById(productId).get();
        return imagesDao.findByProduct(product);
    }

    private void CheckForProduct(String productId) {
        if (!ObjectId.isValid(productId))
            throw new InvalidIdentifierFormatException(productId);

        if (!productsDao.existsById(productId))
            throw new NotFoundException(productId);
    }

    @Override
    public Images saveProductImage(String productId, MultipartFile image) throws IOException {
        // Check for product existence
        CheckForProduct(productId);
        log.info("Saving at {} for {}", uploadsFolder, productId);

        log.info("Image {}", image);
        var product = productsDao.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        // Create date prefix for the file
        String datePrefix = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Directory path for the product
        String directoryPath = uploadsFolder + "/" + productId;
        File uploadDir = new File(directoryPath);

        // Ensure the directories exist
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new RuntimeException("Failed to create directories.");
        } else {
            log.info("Folders exist or were created successfully");
        }

        // Full path for the destination file
        String fileName = datePrefix + "_" + Objects.requireNonNull(image.getOriginalFilename());
        File destinationFile = new File(uploadDir, fileName);

        // Save the file
        image.transferTo(destinationFile);


        // Build the image URL
        String imageUrl = String.format("/images/products/%s/%s", productId, fileName);


        // Save metadata
        var imageMetadata = new ImageMetadata(
                image.getContentType(),
                String.valueOf(image.getSize()),
                image.getName(),
                image.getOriginalFilename(),
                imageUrl
        );

        // Save the image record and return
        Images images = imagesDao.save(new Images(product, imageUrl, imageMetadata));
        product.addImage(imageUrl);

        productsDao.save(product);

        return images;
    }

    @Override
    public Images saveShopImage(String shopId, MultipartFile image) throws IOException {
        return null;
    }

    @Override
    public Images saveProductImages(String productId, List<MultipartFile> image) {
        return null;
    }

    @Override
    public void deleteImage(String imageId) {

    }
}
