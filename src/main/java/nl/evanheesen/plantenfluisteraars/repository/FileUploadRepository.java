package nl.evanheesen.plantenfluisteraars.repository;

import nl.evanheesen.plantenfluisteraars.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<DBFile, String> {
}
