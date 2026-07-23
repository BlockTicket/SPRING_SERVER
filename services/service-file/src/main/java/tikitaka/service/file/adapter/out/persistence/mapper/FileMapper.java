package tikitaka.service.file.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;
import tikitaka.service.file.adapter.out.persistence.FileJpaEntity;
import tikitaka.service.file.domain.File;

@Component
public class FileMapper {

    public FileJpaEntity toEntity(File file) {
        return FileJpaEntity.builder()
                .fileType(file.getFileType())
                .s3Key(file.getS3Key())
                .title(file.getTitle())
                .build();
    }
}