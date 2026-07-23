package tikitaka.service.file.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import tikitaka.core.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements StatusCode {

    EMPTY_FILE("FILE_001", "업로드할 파일이 비어 있습니다.", HttpStatus.BAD_REQUEST),
    FILE_READ_FAILED("FILE_002", "파일을 읽는 데 실패했습니다.", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED("FILE_003", "파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNSUPPORTED_FILE_TYPE("FILE_004", "지원하지 않는 파일 형식입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}