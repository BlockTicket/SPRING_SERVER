package tikitaka.service.file.adapter.in.web.data.response;

public record UploadFileResponse(
        String s3Key
) {
    public static UploadFileResponse of(String s3key) {
        return new UploadFileResponse(s3key);
    }
}
