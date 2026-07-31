package tikitaka.service.file.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tikitaka.core.common.data.CommonResponse;
import tikitaka.service.file.adapter.in.web.data.request.FileUploadRequest;
import tikitaka.service.file.adapter.in.web.data.response.UploadFileResponse;
import tikitaka.service.file.application.port.in.UploadFileUseCase;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final UploadFileUseCase fileUseCase;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<UploadFileResponse> uploadFile(
            @RequestPart("file") MultipartFile file,
            @Valid @ModelAttribute FileUploadRequest request) {

        String s3Key = fileUseCase.uploadFile(request.toCommand(file));

        return CommonResponse.ok("파일이 업로드되었습니다.", UploadFileResponse.of(s3Key));
    }

    @GetMapping("/health")
    public String health() {

        return "health";
    }
}