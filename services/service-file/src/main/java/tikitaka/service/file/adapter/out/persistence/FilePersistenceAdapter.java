package tikitaka.service.file.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tikitaka.service.file.adapter.out.persistence.mapper.FileMapper;
import tikitaka.service.file.application.port.out.FilePort;
import tikitaka.service.file.domain.File;

@Component
@RequiredArgsConstructor
public class FilePersistenceAdapter implements FilePort {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public File save(File file) {
        fileRepository.save(fileMapper.toEntity(file));
        return file;
    }
}