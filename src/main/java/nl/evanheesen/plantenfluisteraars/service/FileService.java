package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.model.File;
import nl.evanheesen.plantenfluisteraars.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FileService {

    @Autowired
    private FileRepository fileRepository;

//    Deze niet nodig voor mijn applicatie:
    public Iterable<File> getFiles() {
        return fileRepository.findAll();
    }




}
