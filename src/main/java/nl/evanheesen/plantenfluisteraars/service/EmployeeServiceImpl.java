package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public Iterable<Employee> getEmployees() {
        Iterable<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Iterable<Employee> getEmployeesByStatus(String status) {
        Iterable<Employee> employees = employeeRepository.findAllByStatusIgnoreCase(status);
        return employees;
    }

    public Optional<Employee> getEmployeeById(long id) {
        if (!employeeRepository.existsById(id))
            throw new RecordNotFoundException("Plantenfluisteraar met id " + id + " niet gevonden.");
        return employeeRepository.findById(id);
    }

    public long createEmployee(Employee employee) {
        Employee newEmployee = employeeRepository.save(employee);
        return newEmployee.getId();
    }

    public void partialUpdateEmployee(long id, Map<String, String> fields) {
        if (!employeeRepository.existsById(id)) {
            throw new RecordNotFoundException();
        }
        Employee employee = employeeRepository.findById(id).get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "firstname":
                    employee.setFirstName((String) fields.get(field));
                    break;
                case "lastname":
                    employee.setLastName((String) fields.get(field));
                    break;
                case "street":
                    employee.setStreet((String) fields.get(field));
                    break;
                case "housenumber":
                    employee.setHouseNumber((String) fields.get(field));
                    break;
                case "postalcode":
                    employee.setPostalCode((String) fields.get(field));
                    break;
                case "city":
                    employee.setCity((String) fields.get(field));
                    break;
                case "phone":
                    employee.setPhone((String) fields.get(field));
                    break;

            }
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RecordNotFoundException();
        }
        employeeRepository.deleteById(id);
    }

    public Employee convertDTOToEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setStreet(employeeRequest.getStreet());
        employee.setHouseNumber(employeeRequest.getHouseNumber());
        employee.setPostalCode(employeeRequest.getPostalCode());
        employee.setCity(employeeRequest.getCity());
        employee.setPhone(employeeRequest.getPhone());
        employee.setStatus("Inactief");
//        set here the DBFile from DTO or in FileUploadService
        return employee;
    }

}
