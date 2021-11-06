package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.File;
import nl.evanheesen.plantenfluisteraars.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

//    Deze in principe niet nodig voor mijn applicatie:
//    public Iterable<File> getFiles() {
//        return fileRepository.findAll();
//    }

    public long uploadFile(MultipartFile file) {

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        File newFileToStore = new File();
        newFileToStore.setFileName(originalFileName);

        File storedFile = fileUploadRepository.save(newFileToStore);

        return storedFile.getId();
    }

    public void deleteFile(long id) {
        if (!fileUploadRepository.existsById(id)) throw new RecordNotFoundException();
        fileUploadRepository.deleteById(id);
    }

    public Optional<File> getFileById(long id) {
        if (!fileUploadRepository.existsById(id)) throw new RecordNotFoundException();
        return fileUploadRepository.findById(id);
    }

//    public boolean fileExistsById(long id) {
//        return fileRepository.existsById(id);
//    }

}
