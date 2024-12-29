package io.apptales.minipos.data.model;

public record ImageMetadata(
        String fileType,
        String fileSize,
        String fileName,
        String fileOriginalName,
        String serverUrl
) {
}
