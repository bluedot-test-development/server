package develop.bluedot.server.controller;

import develop.bluedot.server.component.Uploader;
import develop.bluedot.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UploadController {

    private final Uploader uploader;

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/upload")
    public ApiResponse<String> upload(@RequestParam("data") MultipartFile file, @RequestParam Long id) throws IOException {

        ApiResponse<String> returnData = ApiResponse.of(uploader.upload(file, "static"));

        String videoURL = returnData.getData();

        return returnData;
    }

}
