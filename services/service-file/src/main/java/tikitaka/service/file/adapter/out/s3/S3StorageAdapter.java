package tikitaka.service.file.adapter.out.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import tikitaka.core.common.exception.CommonException;
import tikitaka.service.file.application.port.out.FileStoragePort;
import tikitaka.service.file.application.port.out.data.FileContent;
import tikitaka.service.file.domain.exception.FileErrorCode;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3StorageAdapter implements FileStoragePort {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @Override
    public void upload(String s3Key, FileContent fileContent) {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Properties.bucket())
                .key(s3Key)
                .contentType(fileContent.contentType())
                .contentLength(fileContent.size())
                .build();

        try {

            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(fileContent.inputStream(), fileContent.size())
            );
        } catch (Exception e) {

            log.error("파일 업로드 도중 문제가 발생했습니다.", e);
            throw new CommonException(FileErrorCode.FILE_UPLOAD_FAILED);
        }
    }
}