package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController

// Wat betekent dit??
@RequestMapping("api/files")

@CrossOrigin
public class FileUploadController {

    private FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Object> getFileById(@PathVariable long id) {
        return ResponseEntity.ok().body(fileUploadService.getFileById(id));
    }

    @PostMapping(value = "/files")
    public ResponseEntity<Object> uploadFile(@RequestBody MultipartFile file) {
        long newId = fileUploadService.uploadFile(file);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/files/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @DeleteMapping(value = "/files/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable("id") long id) {
        fileUploadService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

}
