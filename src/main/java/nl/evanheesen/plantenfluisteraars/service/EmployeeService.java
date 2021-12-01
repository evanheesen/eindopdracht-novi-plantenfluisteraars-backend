package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.model.Employee;

import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    public Iterable<Employee> getEmployees();

    public Optional<Employee> getEmployeeById(long id);

    public Iterable<Employee> getEmployeesByStatus(String status);

    public long createEmployee(Employee employee);

    public void editEmployee(long id, Map<String, String> fields);

    public void deleteEmployee(long id);

    public Employee convertDTOToEmployee(EmployeeRequest employeeRequest);

}
