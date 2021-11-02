package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.repository.AanvraagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AanvragenController {

    @Autowired
    AanvraagRepository aanvraagRepository;

    @GetMapping(value = "/aanvragen")
    ResponseEntity getAllAanvragen() {
        return ResponseEntity.ok(aanvraagRepository.findAll());
    }
}
