package io.apptales.minipos.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Images {

    @DBRef
    private Product product;

    @Id
    private String imageId;

    @Indexed(unique = true)
    private String link;

    private ImageMetadata metaInfo;

    public Images() {
    }


    public Images(Product product, String link, ImageMetadata metadata) {
        this.product = product;
        this.link = link;
        this.metaInfo = metadata;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ImageMetadata getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(ImageMetadata metaInfo) {
        this.metaInfo = metaInfo;
    }

    @Override
    public String toString() {
        return "Images{" +
                "product=" + product +
                ", imageId='" + imageId + '\'' +
                ", link='" + link + '\'' +
                ", metaInfo=" + metaInfo +
                '}';
    }
}

