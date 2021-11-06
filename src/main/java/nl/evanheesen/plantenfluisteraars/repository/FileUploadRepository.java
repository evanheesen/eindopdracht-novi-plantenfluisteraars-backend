package nl.evanheesen.plantenfluisteraars.repository;

import nl.evanheesen.plantenfluisteraars.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileUploadRepository extends CrudRepository<File, Long> {
}
