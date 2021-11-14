package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.service.CustomerService;
import nl.evanheesen.plantenfluisteraars.service.GardenService;
import nl.evanheesen.plantenfluisteraars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3001", maxAge = 3600)
@RestController
@RequestMapping(value = "/customers")
public class CustomersController {

    private CustomerService customerService;
    private UserService userService;
    private GardenService gardenService;

    @Autowired
    public CustomersController(CustomerService customerService, UserService userService, GardenService gardenService) {
        this.customerService = customerService;
        this.userService = userService;
        this.gardenService = gardenService;
    }

    @GetMapping("") // get collection
    public ResponseEntity<Object> getCustomers() {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @GetMapping("/{id}") // get item
    public ResponseEntity<Object> getCustomer(@PathVariable long id) {
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> newCustomerRegistration(@RequestBody CustomerRequest customerRequest) {
//        convert DTO to entity
        Customer customer = customerService.convertDTOToCustomer(customerRequest);
        String username = userService.createCustomerUser(customerRequest);
        Garden garden = gardenService.convertDTOToGarden(customerRequest);
        long customerId = customerService.createCustomer(customer);
        long gardenId = gardenService.addGarden(garden);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/bewoners/{id}")
                .buildAndExpand(customerId).toUri();

        userService.assignCustomerToUser(username, customerId);
        customerService.assignGardenToCustomer(gardenId, customerId);
        return ResponseEntity.created(location).body(location);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateCustomerPartial(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        customerService.partialUpdateCustomer(id, fields);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") long id) {
//        customerService.deleteCustomer(id);
//        return ResponseEntity.noContent().build();
//    }

}
