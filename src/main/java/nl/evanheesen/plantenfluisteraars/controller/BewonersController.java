package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.model.Bewoner;
import nl.evanheesen.plantenfluisteraars.repository.BewonerRepository;
import nl.evanheesen.plantenfluisteraars.service.BewonerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Map;

@RestController
public class BewonersController {

    private BewonerServiceImpl bewonerServiceImpl;

    @Autowired
    public BewonersController(BewonerServiceImpl bewonerServiceImpl) {
        this.bewonerServiceImpl = bewonerServiceImpl;
    }

    @GetMapping("/bewoners") // get collection
    public ResponseEntity<Object> getBewoners() {
        return ResponseEntity.ok().body(bewonerServiceImpl.getBewoners());
    }

    @GetMapping("/bewoners/{id}") // get item
    public ResponseEntity<Object> getBewoner(@PathVariable long id) {
        return ResponseEntity.ok().body(bewonerServiceImpl.getBewonerById(id));
    }

    // Nog uitzoeken hoe PostMapping in te regelen
    @PostMapping(value = "")
    public ResponseEntity<Object> createBewoner(@RequestBody Bewoner bewoner) {
        long newId = bewonerServiceImpl.createBewoner(bewoner);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/bewoners/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PatchMapping(value = "bewoners/{id}")
    public ResponseEntity<Object> updateBewonerPartial(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        bewonerServiceImpl.partialUpdateBewoner(id, fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteBewoner(@PathVariable("id") long id) {
        bewonerServiceImpl.deleteBewoner(id);
        return ResponseEntity.noContent().build();
    }


//    @GetMapping("/bewoners?status={status}")
//    public ResponseEntity<Object> getAllBewoners(@RequestParam String status) {
//        return ...;
//    }

}
