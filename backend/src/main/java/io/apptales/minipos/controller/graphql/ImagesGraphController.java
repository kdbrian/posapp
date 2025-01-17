package io.apptales.minipos.controller.graphql;

import io.apptales.minipos.data.model.Images;
import io.apptales.minipos.service.ImagesService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ImagesGraphController {

    private final ImagesService imagesService;

    public ImagesGraphController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @QueryMapping(name = "getImages")
    public List<Images> getImages(){
        return imagesService.getImages();
    }


}
