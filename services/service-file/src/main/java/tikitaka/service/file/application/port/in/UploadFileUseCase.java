package tikitaka.service.file.application.port.in;

import tikitaka.service.file.application.port.in.data.UploadFileCommand;
import tikitaka.service.file.domain.File;

public interface UploadFileUseCase {

    File uploadFile(UploadFileCommand command);
}