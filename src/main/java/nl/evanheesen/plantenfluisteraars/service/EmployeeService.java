package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.model.Employee;

import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    public Iterable<Employee> getEmployees();

    public Optional<Employee> getEmployeeById(long id);

    public long createEmployee(Employee employee);

    public void partialUpdateEmployee(long id, Map<String, String> fields);

    public void deleteEmployee(long id);

//    public void assignEmployeeToUser(String username, long employeeId);

}
