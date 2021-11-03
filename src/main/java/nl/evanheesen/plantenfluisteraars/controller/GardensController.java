package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GardensController {

    @Autowired
    GardenRepository gardenRepository;

    @GetMapping(value = "/aanvragen")
    ResponseEntity getAllAanvragen() {
        return ResponseEntity.ok(gardenRepository.findAll());
    }
}
