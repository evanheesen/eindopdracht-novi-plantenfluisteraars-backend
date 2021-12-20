package nl.evanheesen.plantenfluisteraars.repository;

import nl.evanheesen.plantenfluisteraars.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Collection<Employee> findAllByStatusIgnoreCase(String status);
}
