package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.dto.request.UserPostRequest;
import nl.evanheesen.plantenfluisteraars.dto.response.UsernameResponse;
import nl.evanheesen.plantenfluisteraars.exception.BadRequestException;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.exception.UserNotFoundException;
import nl.evanheesen.plantenfluisteraars.exception.UsernameExistsAlready;
import nl.evanheesen.plantenfluisteraars.model.Authority;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.User;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import nl.evanheesen.plantenfluisteraars.utils.RandomStringGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Add in controller still!!
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    public UsernameResponse getUsername(String username) {
        String response;
        if (userRepository.existsById(username)) {
            response = "none";
        } else {
            response = username;
        }
        return new UsernameResponse(response);
    }

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String username) {
        return userRepository.findById(username);
    }

    public String createUser(UserPostRequest userPostRequest) {
        String username = userPostRequest.getUsername();
        if (userRepository.existsById(username)) {
            throw new UsernameExistsAlready(username);
        } else {
            try {
                String encryptedPassword = passwordEncoder.encode(userPostRequest.getPassword());

                User user = new User();
                user.setUsername(username);
                user.setPassword(encryptedPassword);
                user.setEmail(userPostRequest.getEmail());
                user.setEnabled(true);
                if (userPostRequest.getIsAdmin().equals(true)) {
                    user.addAuthority("ROLE_ADMIN");
                }
                user.addAuthority("ROLE_USER");
                User newUser = userRepository.save(user);
                return newUser.getUsername();
            } catch (Exception ex) {
                throw new BadRequestException("Kan de gebruiker niet aanmaken");
            }
        }
    }

    public void deleteUser(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
        } else {
            throw new UserNotFoundException(username);
        }
    }

    public void updateUser(String username, User newUser) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException();
        } else {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setEmail(newUser.getEmail());
            user.setEnabled(newUser.getEnabled());
            userRepository.save(user);
        }
    }

    public Set<Authority> getAuthorities(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException();
        } else {
            User user = userOptional.get();
            return user.getAuthorities();
        }
    }

    public void addAuthority(String username, String authorityString) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException();
        } else {
            User user = userOptional.get();
            user.addAuthority(authorityString);
            userRepository.save(user);
        }
    }

    public void removeAuthority(String username, String authorityString) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        } else {
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

    public void assignCustomerToUser(String username, long customerId) {
        var optionalUser = userRepository.findById(username);
        var optionalCustomer = customerRepository.findById(customerId);
        if (optionalUser.isPresent() && optionalCustomer.isPresent()) {
            var user = optionalUser.get();
            var customer = optionalCustomer.get();
            user.setCustomer(customer);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException();
        }
    }

    public String createCustomerUser(CustomerRequest customerRequest) {
        var username = customerRequest.getUsername();
        var optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            throw new UserNotFoundException(username);
        } else {
            try {
                String encryptedPassword = passwordEncoder.encode(customerRequest.getPassword());

                User user = new User();
                user.setUsername(customerRequest.getUsername());
                user.setPassword(encryptedPassword);
                user.setEmail(customerRequest.getEmail());
                user.setEnabled(true);
                user.addAuthority("ROLE_USER");
                User newUser = userRepository.save(user);
                return newUser.getUsername();
            } catch (Exception ex) {
                throw new BadRequestException("Kan de gebruiker niet aanmaken");
            }
        }
    }

    public String createEmployeeUser(EmployeeRequest employeeRequest) {
        var username = employeeRequest.getUsername();
        var optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            throw new UserNotFoundException(username);
        } else {
            try {
                String encryptedPassword = passwordEncoder.encode(employeeRequest.getPassword());

                User user = new User();
                user.setUsername(employeeRequest.getUsername());
                user.setPassword(encryptedPassword);
                user.setEmail(employeeRequest.getEmail());
                user.setEnabled(true);
                user.addAuthority("ROLE_USER");
                User newUser = userRepository.save(user);
                return newUser.getUsername();
            } catch (Exception ex) {
                throw new BadRequestException("Kan de gebruiker niet aanmaken");
            }
        }
    }

    public void editUser(String username, Map<String, String> fields) {
        if (!userRepository.existsById(username)) {
            throw new RecordNotFoundException();
        }
        User user = userRepository.findById(username).get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "status":
                    String newStatus = fields.get(field);
                    if (!newStatus.equals("statusDefault")) {
                        Boolean newStatusBoolean;
                        newStatusBoolean = newStatus.equals("Actief");
                        user.setEnabled(newStatusBoolean);
                    }
                    break;
                case "email":
                    if (!fields.get(field).equals("")) {
                        user.setEmail((String) fields.get(field));
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
            }
        }
        userRepository.save(user);
    }

}
