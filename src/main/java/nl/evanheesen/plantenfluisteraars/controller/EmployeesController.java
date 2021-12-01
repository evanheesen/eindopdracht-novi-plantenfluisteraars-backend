package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.User;
import nl.evanheesen.plantenfluisteraars.service.CustomerService;
import nl.evanheesen.plantenfluisteraars.service.EmployeeService;
import nl.evanheesen.plantenfluisteraars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3001", maxAge = 3600)
@RestController
@RequestMapping(value = "/employees")
public class EmployeesController {

    private EmployeeService employeeService;
    private UserService userService;

    @Autowired
    public EmployeesController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getEmployees() {
        return ResponseEntity.ok().body(employeeService.getEmployees());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getEmployeesByStatus(@PathVariable String status) {
        return ResponseEntity.ok().body(employeeService.getEmployeesByStatus(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable long id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
//        convert DTO to entity
        Employee employee = employeeService.convertDTOToEmployee(employeeRequest);
        String username = userService.createEmployeeUser(employeeRequest);
        long employeeId = employeeService.createEmployee(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(employeeId).toUri();

        userService.assignEmployeeToUser(username, employeeId);
        return ResponseEntity.created(location).body(location);
    }

    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<Object> editEmployee(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        employeeService.editEmployee(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
