package tikitaka.service.file.application.port.in;

import tikitaka.service.file.application.port.in.data.UploadFileCommand;

public interface UploadFileUseCase {

    String uploadFile(UploadFileCommand command);
}