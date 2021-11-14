package nl.evanheesen.plantenfluisteraars.controller;

import nl.evanheesen.plantenfluisteraars.dto.request.AuthenticationRequest;
import nl.evanheesen.plantenfluisteraars.dto.response.AuthenticationResponse;
import nl.evanheesen.plantenfluisteraars.service.UserAuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Cross origin port 3000:
@CrossOrigin(origins = "http://localhost:3001", maxAge = 3600)
@RestController
public class AuthenticationController {

    UserAuthenticateService userAuthenticateService;

    @Autowired
    public AuthenticationController(UserAuthenticateService userAuthenticateService) {
        this.userAuthenticateService = userAuthenticateService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        AuthenticationResponse authenticationResponse = userAuthenticateService.authenticateUser(authenticationRequest);

        return ResponseEntity.ok(authenticationResponse);
    }

}
