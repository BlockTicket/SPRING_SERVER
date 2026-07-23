package tikitaka.service.file.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tikitaka.core.common.exception.CommonException;
import tikitaka.service.file.domain.enums.FileType;
import tikitaka.service.file.domain.exception.FileErrorCode;

import java.util.Locale;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class File {

    private final UUID fileId;
    private final FileType fileType;
    private final String s3Key;
    private final String title;
    private final String contentType;

    public static File create(FileType fileType, String originalFilename) {

        String extension = extensionOf(originalFilename);

        return new File(
                null,
                fileType,
                generateS3Key(fileType, extension),
                originalFilename,
                fileType.resolveContentType(extension)
        );
    }

    private static String generateS3Key(FileType fileType, String extension) {

        return fileType.name()
                .toLowerCase(Locale.ROOT) + "/" + UUID.randomUUID() + "." + extension;
    }

    private static String extensionOf(String originalFilename) {
        if (originalFilename == null) throw new CommonException(FileErrorCode.UNSUPPORTED_FILE_TYPE);

        int index = originalFilename.lastIndexOf('.');
        if (index == -1 || index == originalFilename.length() - 1) {
            throw new CommonException(FileErrorCode.UNSUPPORTED_FILE_TYPE);
        }

        return originalFilename.substring(index + 1).toLowerCase(Locale.ROOT);
    }
}