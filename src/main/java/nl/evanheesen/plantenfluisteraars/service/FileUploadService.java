package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.exception.FileStorageException;
import nl.evanheesen.plantenfluisteraars.model.DBFile;
import nl.evanheesen.plantenfluisteraars.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    public DBFile storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the name of the file has no invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("De bestandsnaam bevat ongeldige karakters " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return fileUploadRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Kan het bestand niet opslaan " + fileName + ". Probeer het opnieuw!", ex);
        }
    }

//    public File getFile(String fileId) {
//        return FileUploadRepository.findById(fileId)
//                .orElseThrow(() -> new FileNotFoundException("Bestand niet gevonden met dit id " + fileId));
//    }

}
