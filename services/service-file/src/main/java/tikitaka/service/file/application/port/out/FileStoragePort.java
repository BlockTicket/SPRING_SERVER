package tikitaka.service.file.application.port.out;

import tikitaka.service.file.application.port.out.data.FileContent;

public interface FileStoragePort {

    void upload(String s3Key, FileContent fileContent);
}