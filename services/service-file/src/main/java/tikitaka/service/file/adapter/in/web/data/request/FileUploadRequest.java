package tikitaka.service.file.adapter.in.web.data.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import tikitaka.core.common.exception.CommonException;
import tikitaka.service.file.application.port.in.data.UploadFileCommand;
import tikitaka.service.file.domain.enums.FileType;
import tikitaka.service.file.domain.exception.FileErrorCode;

import java.io.IOException;
import java.io.InputStream;

public record FileUploadRequest(

        @NotNull
        FileType fileType
) {
    public UploadFileCommand toCommand(MultipartFile file) {
        return new UploadFileCommand(
                fileType,
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                inputStreamOf(file)
        );
    }

    private static InputStream inputStreamOf(MultipartFile file) {
        try {

            return file.getInputStream();
        } catch (IOException e) {

            throw new CommonException(FileErrorCode.FILE_READ_FAILED);
        }
    }
}