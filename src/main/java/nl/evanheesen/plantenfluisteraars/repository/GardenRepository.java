package nl.evanheesen.plantenfluisteraars.repository;

import nl.evanheesen.plantenfluisteraars.model.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface GardenRepository extends JpaRepository<Garden, Long> {
    Collection<Garden> findAllByEmployeeId(long employeeId);
    Collection<Garden> findAllByCustomerId(long id);
    Collection<Garden> findAllByStatusIgnoreCase(String status);

}
