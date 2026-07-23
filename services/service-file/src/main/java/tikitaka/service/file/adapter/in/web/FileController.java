package tikitaka.service.file.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @GetMapping("/health")
    public String health() {
        return "health";
    }
}
