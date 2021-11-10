package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.dto.response.UploadFileResponse;
import nl.evanheesen.plantenfluisteraars.model.DBFile;
import nl.evanheesen.plantenfluisteraars.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/plantenfluisteraars/{id}/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") long employeeId) {
        DBFile dbFile = fileUploadService.storeFile(file);

        var dbFileId = dbFile.getId();
        fileUploadService.assignFileToEmployee(dbFileId, employeeId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

//        Dit werkt blijkbaar niet!!
        dbFile.setLocationURL(fileDownloadUri);

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

//     is dit nodig??
    @GetMapping("/plantenfluisteraars/{id}/getFile/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileId, @PathVariable("id") long employeeId) {
        // Load file from database
        DBFile dbFile = fileUploadService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}
