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

@CrossOrigin(origins = "http://localhost:3001", maxAge = 3600)
@RestController
@RequestMapping("/files")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/{id}/upload-file")
    public UploadFileResponse uploadFile(@RequestBody MultipartFile file, @PathVariable("id") long employeeId) {
        DBFile dbFile = fileUploadService.storeFile(file);

        var dbFileId = dbFile.getId();
        fileUploadService.assignFileToEmployee(dbFileId, employeeId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/{id}/get-file/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable("id") String fileId, @PathVariable("fileId") long employeeId) {
        // Load file from database
        DBFile dbFile = fileUploadService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

//    @DeleteMapping(value = "/{id}/get-file/{fileId}")
//    public ResponseEntity<Object> deleteFile(@PathVariable("employeeId") long id, @PathVariable("fileId") String fileId) {
//        fileUploadService.deleteFile(id, fileId);
//        return ResponseEntity.noContent().build();
//    }

}
