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

@RestController
@RequestMapping("/aanvragen")
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

    @GetMapping("/plantenfluisteraars/{id}")
    public ResponseEntity<Object> getGardensByEmployeeId(@PathVariable long id) {
        return ResponseEntity.ok().body(gardenService.getGardensByEmployeeId(id));
    }

    @GetMapping("/bewoners/{id}")
    public ResponseEntity<Object> getGardensByCustomerId(@PathVariable long id) {
        return ResponseEntity.ok().body(gardenService.getGardensByCustomerId(id));
    }

    @PostMapping("")
    public ResponseEntity<Object> addGarden(@RequestBody Garden garden) {
        long newId = gardenService.addGarden(garden);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/gardens/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }


}
