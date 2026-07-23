package tikitaka.service.file.application.port.out.data;

import java.io.InputStream;

public record FileContent(
        String contentType,
        long size,
        InputStream inputStream
) {
}