package tikitaka.service.file.application.port.in.data;

import tikitaka.service.file.domain.enums.FileType;

import java.io.InputStream;

public record UploadFileCommand(
        FileType fileType,
        String originalFilename,
        String contentType,
        long size,
        InputStream inputStream
) {
}