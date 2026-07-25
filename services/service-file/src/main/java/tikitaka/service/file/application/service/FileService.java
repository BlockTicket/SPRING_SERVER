package tikitaka.service.file.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tikitaka.service.file.application.port.in.UploadFileUseCase;
import tikitaka.service.file.application.port.in.data.UploadFileCommand;
import tikitaka.service.file.application.port.out.FilePort;
import tikitaka.service.file.application.port.out.FileStoragePort;
import tikitaka.service.file.application.port.out.data.FileContent;
import tikitaka.service.file.domain.File;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class FileService implements UploadFileUseCase {

    private final FilePort filePort;
    private final FileStoragePort fileStoragePort;

    @Override
    public File uploadFile(UploadFileCommand command) {

        File file = File.create(command.fileType(), command.originalFilename());

        filePort.save(file);
        fileStoragePort.upload(file.getS3Key(), new FileContent(
                file.getContentType(), command.size(), command.inputStream()
        ));

        return file;
    }
}
