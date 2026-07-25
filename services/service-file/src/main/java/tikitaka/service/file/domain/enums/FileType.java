package tikitaka.service.file.domain.enums;

import lombok.RequiredArgsConstructor;
import tikitaka.core.common.exception.CommonException;
import tikitaka.service.file.domain.exception.FileErrorCode;

import java.util.Map;

@RequiredArgsConstructor
public enum FileType {

    PERFORMANCE_PROFILE(Map.of(
            "jpg", "image/jpeg",
            "jpeg", "image/jpeg",
            "png", "image/png",
            "webp", "image/webp"
    ));

    private final Map<String, String> allowedExtensions;

    public String resolveContentType(String extension) {

        String contentType = allowedExtensions.get(extension);
        if (contentType == null) {
            throw new CommonException(FileErrorCode.UNSUPPORTED_FILE_TYPE);
        }

        return contentType;
    }
}