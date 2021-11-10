package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.dto.request.UserPostRequest;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.service.CustomerServiceImpl;
import nl.evanheesen.plantenfluisteraars.service.GardenService;
import nl.evanheesen.plantenfluisteraars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value = "/bewoners")
public class CustomersController {

    private CustomerServiceImpl customerServiceImpl;
    private UserService userService;
    private GardenService gardenService;

    @Autowired
    public CustomersController(CustomerServiceImpl customerServiceImpl, UserService userService, GardenService gardenService) {
        this.customerServiceImpl = customerServiceImpl;
        this.userService = userService;
        this.gardenService = gardenService;
    }

    @GetMapping("") // get collection
    public ResponseEntity<Object> getCustomers() {

        return ResponseEntity.ok().body(customerServiceImpl.getCustomers());
    }

    @GetMapping("/{id}") // get item
    public ResponseEntity<Object> getCustomer(@PathVariable long id) {
        return ResponseEntity.ok().body(customerServiceImpl.getCustomerById(id));
    }

//    @PostMapping(value = "")
//    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
//        long newId = customerServiceImpl.createCustomer(customer);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/bewoners/{id}")
//                .buildAndExpand(newId).toUri();
//
//        return ResponseEntity.created(location).body(location);
//    }

    @PostMapping(value = "")
    public ResponseEntity<Object> newCustomerRegistration(@RequestBody CustomerRequest customerRequest) {
//        convert DTO to entity
        Customer customer = customerServiceImpl.convertDTOToCustomer(customerRequest);
        String username = userService.createCustomerUser(customerRequest);
        Garden garden = gardenService.convertDTOToGarden(customerRequest);
        long customerId = customerServiceImpl.createCustomer(customer);
        long gardenId = gardenService.addGarden(garden);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/bewoners/{id}")
                .buildAndExpand(customerId).toUri();

        userService.assignCustomerToUser(username, customerId);
        customerServiceImpl.assignGardenToCustomer(gardenId, customerId);
        return ResponseEntity.created(location).body(location);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateCustomerPartial(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        customerServiceImpl.partialUpdateCustomer(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") long id) {
        customerServiceImpl.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
