package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.UserPostRequest;
import nl.evanheesen.plantenfluisteraars.exception.BadRequestException;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.exception.UserNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Authority;
import nl.evanheesen.plantenfluisteraars.model.User;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import nl.evanheesen.plantenfluisteraars.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private EmployeeRepository employeeRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Add in controller still!!
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserPostRequest userPostRequest) {
        try {
            String encryptedPassword = passwordEncoder.encode(userPostRequest.getPassword());

            User user = new User();
            user.setUsername(userPostRequest.getUsername());
            user.setPassword(encryptedPassword);
            user.setEmail(userPostRequest.getEmail());
            user.setEnabled(true);
            user.addAuthority("ROLE_USER");
            User newUser = userRepository.save(user);
            return newUser.getUsername();
        }
         catch (Exception ex) {
            throw new BadRequestException("Kan de gebruiker niet aanmaken");
        }
    }

//// createUser zoals in Books voorbeeld:

//        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
//        user.setApikey(randomString);
//
//        String password = user.getPassword();
//        String encoded = passwordEncoder.encode(password);
//        user.setPassword(encoded);
//
//        User newUser = userRepository.save(user);
//        return newUser.getUsername();
//    }

    public void deleteUser(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
        }
        else {
            throw new UserNotFoundException(username);
        }
    }

    public void updateUser(String username, User newUser) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        else {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setEmail(newUser.getEmail());
            user.setEnabled(newUser.isEnabled());
            userRepository.save(user);
        }
    }

    public Set<Authority> getAuthorities(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        else {
            User user = userOptional.get();
            return user.getAuthorities();
        }
    }

    public void addAuthority(String username, String authorityString) {
// Ophalen user:
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException();
        }
        else {
            User user = userOptional.get();
// Toevoegen authority:
            user.addAuthority(authorityString);
            userRepository.save(user);
        }
    }

    public void removeAuthority(String username, String authorityString) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        else {
            User user = userOptional.get();
            user.removeAuthority(authorityString);
            userRepository.save(user);
        }
    }

    public void assignEmployeeToUser(String username, long employeeId) {
        var optionalUser = userRepository.findById(username);
        var optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalUser.isPresent() && optionalEmployee.isPresent()) {
            var user = optionalUser.get();
            var employee = optionalEmployee.get();
            user.setEmployee(employee);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }

    // Dit nog toevoegen?

//    private boolean isValidPassword(String password) {
//        final int MIN_LENGTH = 8;
//        final int MIN_DIGITS = 1;
//        final int MIN_LOWER = 1;
//        final int MIN_UPPER = 1;
//        final int MIN_SPECIAL = 1;
//        final String SPECIAL_CHARS = "@#$%&*!()+=-_";
//
//        long countDigit = password.chars().filter(ch -> ch >= '0' && ch <= '9').count();
//        long countLower = password.chars().filter(ch -> ch >= 'a' && ch <= 'z').count();
//        long countUpper = password.chars().filter(ch -> ch >= 'A' && ch <= 'Z').count();
//        long countSpecial = password.chars().filter(ch -> SPECIAL_CHARS.indexOf(ch) >= 0).count();
//
//        boolean validPassword = true;
//        if (password.length() < MIN_LENGTH) validPassword = false;
//        if (countLower < MIN_LOWER) validPassword = false;
//        if (countUpper < MIN_UPPER) validPassword = false;
//        if (countDigit < MIN_DIGITS) validPassword = false;
//        if (countSpecial < MIN_SPECIAL) validPassword = false;
//
//        return validPassword;
//    }
//
//    public void setPassword(String username, String password) {
//        if (username.equals(getCurrentUserName())) {
//            if (isValidPassword(password)) {
//                Optional<User> userOptional = userRepository.findById(username);
//                if (userOptional.isPresent()) {
//                    User user = userOptional.get();
//                    user.setPassword(passwordEncoder.encode(password));
//                    userRepository.save(user);
//                }
//                else {
//                    throw new UserNotFoundException(username);
//                }
//            }
//            else {
//                throw new InvalidPasswordException();
//            }
//        }
//        else {
//            throw new NotAuthorizedException();
//        }
//    }

}