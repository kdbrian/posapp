package io.apptales.minipos.domain.rest;

import io.apptales.minipos.data.model.Images;
import io.apptales.minipos.service.ImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImagesController {

    private static final Logger log = LoggerFactory.getLogger(ImagesController.class);
    private final ImagesService imagesService;


    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

//    @GetMapping("/")
//    public @ResponseBody byte[] getImages(){
//        var images = imagesService.getImages();
//        return images[0].
//    }

    @PostMapping("/upload")
    public ResponseEntity<Images> uploadImage(
            @RequestParam(value = "productId") String productId,
            @RequestBody MultipartFile file
    ) {
        try {
            var imageResult = imagesService.saveProductImage(productId, file);
            return ResponseEntity.ok(imageResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
