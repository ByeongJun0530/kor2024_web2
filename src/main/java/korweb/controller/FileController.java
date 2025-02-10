package korweb.controller;

import korweb.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired private FileService fileService;

    // [1] 파일 업로드 매핑
    @PostMapping("/file/upload")
    public String fileUpload(MultipartFile multipartFile){
        return fileService.fileUpload(multipartFile);
    }
}
