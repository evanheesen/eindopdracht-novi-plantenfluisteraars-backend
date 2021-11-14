package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import nl.evanheesen.plantenfluisteraars.service.CustomerService;
import nl.evanheesen.plantenfluisteraars.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3001", maxAge = 3600)
@RestController
@RequestMapping("/gardens")
public class GardensController {

    private GardenService gardenService;
    private CustomerService customerService;

    @Autowired
    public GardensController(GardenService gardenService, CustomerService customerService) {
        this.gardenService = gardenService;
        this.customerService = customerService;
    }

    @GetMapping("")
    ResponseEntity getAllGardens() {
        return ResponseEntity.ok().body(gardenService.getAllGardens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGarden(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(gardenService.getGardenById(id));
    }

    @GetMapping("/status/{status}")
    ResponseEntity getGardensByStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok().body(gardenService.getGardensByStatus(status));
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Object> getGardensByEmployeeId(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(gardenService.getGardensByEmployeeId(id));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> getGardensByCustomerId(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(gardenService.getGardensByCustomerId(id));
    }

    @PutMapping(value = "/{id}/employees/{employeeId}")
    public void addEmployeeToGarden(@PathVariable("id") long id, @PathVariable("employeeId") long employeeId) {
        gardenService.addEmployeeToGarden(id, employeeId);
    }

    @PatchMapping(value = "/garden/{id}/employees/{employeeId}")
    public ResponseEntity<Object> updateGarden(@PathVariable("id") long id, @PathVariable("employeeId") long employeeId, @RequestBody Map<String, String> fields) {
        gardenService.updateGarden(id, employeeId, fields);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("")
//    public ResponseEntity<Object> addGarden(@RequestBody Garden garden) {
//        long newId = gardenService.addGarden(garden);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/gardens/{id}")
//                .buildAndExpand(newId).toUri();
//
//        return ResponseEntity.created(location).body(location);
//    }


}
