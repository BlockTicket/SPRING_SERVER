package tikitaka.service.file.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tikitaka.service.file.domain.enums.FileType;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID fileId;

    @Enumerated(EnumType.STRING)
    private FileType fileType;
    private String s3Key;
    private String title;

    @Builder
    public FileJpaEntity(FileType fileType, String s3Key, String title) {
        this.fileType = fileType;
        this.s3Key = s3Key;
        this.title = title;
    }
}