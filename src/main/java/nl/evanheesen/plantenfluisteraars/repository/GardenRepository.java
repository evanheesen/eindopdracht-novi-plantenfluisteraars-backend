package nl.evanheesen.plantenfluisteraars.repository;

import nl.evanheesen.plantenfluisteraars.model.Garden;
//import nl.evanheesen.plantenfluisteraars.model.GardenKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface GardenRepository extends JpaRepository<Garden, Long> {
    Collection<Garden> findAllByEmployeeId(long employeeId);
    Collection<Garden> findAllByCustomerId(long customerId);
}
