package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.User;
import nl.evanheesen.plantenfluisteraars.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
public class EmployeesController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/plantenfluisteraars") // get collection
    public ResponseEntity<Object> getEmployees() {
        return ResponseEntity.ok().body(employeeService.getEmployees());
    }

    @GetMapping("/plantenfluisteraars/{id}") // get item
    public ResponseEntity<Object> getEmployees(@PathVariable long id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    }

    @PostMapping(value = "/plantenfluisteraars")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee, User user) {
        long newId = employeeService.createEmployee(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/plantenfluisteraars/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PatchMapping(value = "/plantenfluisteraars/{id}")
    public ResponseEntity<Object> updateEmployeePartial(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        employeeService.partialUpdateEmployee(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/plantenfluisteraars/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }



//    @PutMapping("/plantenfluisteraars/{id}/users/{username}")
//    public void assignEmployeeToUser(@PathVariable("id") long employeeId, @PathVariable("username") String username) {
//        employeeService.assignEmployeeToUser(username, employeeId);
//    }

}
