package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Map;

@RestController
public class CustomersController {

    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    public CustomersController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @GetMapping("/bewoners") // get collection
    public ResponseEntity<Object> getCustomers() {
        return ResponseEntity.ok().body(customerServiceImpl.getCustomers());
    }

//    Test voor secure. Later weghalen!!
    @GetMapping("/secure/bewoners") // get collection
    public ResponseEntity<Object> getCustomersByAdmin() {
        return ResponseEntity.ok().body(customerServiceImpl.getCustomers());
    }

    @GetMapping("/bewoners/{id}") // get item
    public ResponseEntity<Object> getCustomer(@PathVariable long id) {
        return ResponseEntity.ok().body(customerServiceImpl.getCustomerById(id));
    }

    @PostMapping(value = "/bewoners")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        long newId = customerServiceImpl.createCustomer(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/bewoners/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PatchMapping(value = "/bewoners/{id}")
    public ResponseEntity<Object> updateCustomerPartial(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        customerServiceImpl.partialUpdateCustomer(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/bewoners/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") long id) {
        customerServiceImpl.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

//    endpoint niet perse nodig voor deze relatie, later nog de assignUserToCustomer methode aanroepen in de POST request endpoint?
    @PutMapping("/bewoners/{id}/users/{name}")
    public void assignUserToCustomer(@PathVariable("id") long customerId, @PathVariable("name") String userId) {
        customerServiceImpl.assignUserToCustomer(userId, customerId);
    }

//    @GetMapping("/bewoners?status={status}")
//    public ResponseEntity<Object> getAllBewoners(@RequestParam String status) {
//        return ...;
//    }

}
