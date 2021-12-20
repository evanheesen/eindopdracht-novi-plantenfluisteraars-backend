package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;
    private GardenRepository gardenRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository, GardenRepository gardenRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.gardenRepository = gardenRepository;
    }

    public Iterable<Employee> getEmployees() {
        Iterable<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Iterable<Employee> getEmployeesByStatus(String status) {
        return employeeRepository.findAllByStatusIgnoreCase(status);
    }

    public Optional<Employee> getEmployeeById(long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty())
            throw new RecordNotFoundException("Plantenfluisteraar met id " + id + " niet gevonden.");
        return optionalEmployee;
    }

    public long createEmployee(Employee employee) {
        Employee newEmployee = employeeRepository.save(employee);
        return newEmployee.getId();
    }

    public void removeEmployeeFromGardens(long id) {
        Collection<Garden> gardens = gardenRepository.findAllByEmployeeId(id);
        gardens.forEach(garden -> garden.setEmployee(null));
        gardens.forEach(garden -> garden.setStatus("Open"));
        gardens.forEach(garden -> gardenRepository.save(garden));
    }

    public void editEmployee(long id, Map<String, String> fields) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new RecordNotFoundException();
        }
        Employee employee = optionalEmployee.get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "firstname":
                    if (!fields.get(field).equals("")) {
                        employee.setFirstName((String) fields.get(field));
                    }
                    break;
                case "lastname":
                    if (!fields.get(field).equals("")) {
                        employee.setLastName((String) fields.get(field));
                    }
                    break;
                case "street":
                    if (!fields.get(field).equals("")) {
                        employee.setStreet((String) fields.get(field));
                    }
                    break;
                case "housenumber":
                    if (!fields.get(field).equals("")) {
                        employee.setHouseNumber((String) fields.get(field));
                    }
                    break;
                case "postalcode":
                    if (!fields.get(field).equals("")) {
                        employee.setPostalCode((String) fields.get(field));
                    }
                    break;
                case "city":
                    if (!fields.get(field).equals("")) {
                        employee.setCity((String) fields.get(field));
                    }
                    break;
                case "phone":
                    if (!fields.get(field).equals("")) {
                        employee.setPhone((String) fields.get(field));
                    }
                    break;
                case "status":
                    String newStatus = fields.get(field);
                    if (!newStatus.equalsIgnoreCase("statusDefault") && newStatus.equals("Inactief")) {
                        employee.setStatus("Inactief");
                        removeEmployeeFromGardens(id);
                    }
                    break;
            }
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new RecordNotFoundException();
        }
        Employee employee = optionalEmployee.get();
        removeEmployeeFromGardens(id);
        String username = employee.getUser().getUsername();
        userRepository.deleteById(username);
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
