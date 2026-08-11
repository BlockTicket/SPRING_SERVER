package tikitaka.service.file.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.core.common.exception.CommonException;
import tikitaka.service.file.application.port.in.UploadFileUseCase;
import tikitaka.service.file.application.port.in.data.UploadFileCommand;
import tikitaka.service.file.application.port.out.FilePort;
import tikitaka.service.file.application.port.out.FileStoragePort;
import tikitaka.service.file.application.port.out.data.FileContent;
import tikitaka.service.file.domain.File;
import tikitaka.service.file.domain.exception.FileErrorCode;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class FileService implements UploadFileUseCase {

    private final FilePort filePort;
    private final FileStoragePort fileStoragePort;

    @Override
    public String uploadFile(UploadFileCommand command) {

        if (command.size() <= 0) throw new CommonException(FileErrorCode.EMPTY_FILE);

        File file = File.create(command.fileType(), command.originalFilename());

        filePort.save(file);
        fileStoragePort.upload(file.getS3Key(), new FileContent(
                file.getContentType(), command.size(), command.inputStream()
        ));

        return file.getS3Key();
    }
}