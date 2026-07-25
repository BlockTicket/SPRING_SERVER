package tikitaka.service.file.application.port.out;

import tikitaka.service.file.domain.File;

public interface FilePort {

    File save(File file);
}