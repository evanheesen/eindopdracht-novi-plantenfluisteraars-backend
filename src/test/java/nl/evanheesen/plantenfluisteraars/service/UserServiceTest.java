package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.dto.request.UserPostRequest;
import nl.evanheesen.plantenfluisteraars.dto.response.UsernameResponse;
import nl.evanheesen.plantenfluisteraars.exception.UserNotFoundException;
import nl.evanheesen.plantenfluisteraars.exception.UsernameExistsAlready;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.User;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    public void testGetUsers() {
        List<User> testUsers = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("piet2021");
        user1.setEnabled(true);
        User user2 = new User();
        user2.setUsername("kees2021");
        user2.setEnabled(false);

        testUsers.add(user1);
        testUsers.add(user2);

        when(userRepository.findAll()).thenReturn(testUsers);

        userService.getUsers();

        verify(userRepository, times(1)).findAll();

        assertThat(testUsers.size()).isEqualTo(2);
        assertThat(testUsers.get(0)).isEqualTo(user1);
        assertThat(testUsers.get(1)).isEqualTo(user2);
    }

    @Test
    public void testGetUser() {
        User user1 = new User();
        user1.setUsername("piet2021");
        user1.setEnabled(true);
        var username = user1.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        var testUser = userService.getUser(username);

        assertThat(testUser.get().getUsername()).isEqualTo("piet2021");
        assertThat(testUser.get().getEnabled()).isEqualTo(true);
    }

    @Test
    public void testCreateUserWithExistingUsername() {
        UserPostRequest testUserPostRequest = new UserPostRequest();
        testUserPostRequest.setUsername("Piet2021");
        testUserPostRequest.setEmail("piet@planten.nl");
        testUserPostRequest.setPassword("password");
        testUserPostRequest.setIsAdmin(false);
        var username = testUserPostRequest.getUsername();

        User user1 = new User();
        user1.setUsername(username);

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        assertThrows(UsernameExistsAlready.class, () -> userService.createUser(testUserPostRequest));

    }

    @Test
    public void testDeleteUser() {
        User user1 = new User();
        user1.setUsername("piet2021");
        user1.setEnabled(true);
        var username = user1.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        userService.deleteUser(username);

        verify(userRepository, times(1)).findById(username);
        verify(userRepository, times(1)).deleteById(username);
    }

    @Test
    public void testAssignEmployeeToUser() {
        User user1 = new User();
        user1.setUsername("piet2021");
        user1.setEnabled(true);
        var username = user1.getUsername();

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Piet");
        var employeeId = employee1.getId();

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee1));

        userService.assignEmployeeToUser(username, employeeId);

        verify(userRepository, times(1)).findById(username);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(userRepository).save(user1);

        assertThat(user1.getEmployee()).isEqualTo(employee1);
    }

    @Test
    public void testAssignCustomerToUser() {
        User user1 = new User();
        user1.setUsername("piet2021");
        user1.setEnabled(true);
        var username = user1.getUsername();

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Piet");
        var customerId = customer1.getId();

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer1));

        userService.assignCustomerToUser(username, customerId);

        verify(userRepository, times(1)).findById(username);
        verify(customerRepository, times(1)).findById(customerId);
        verify(userRepository).save(user1);

        assertThat(user1.getCustomer()).isEqualTo(customer1);
    }

    @Test
    public void testCreateCustomerUserWithExistingUsername() {
        CustomerRequest testCustomerRequest = new CustomerRequest();
        testCustomerRequest.setUsername("Piet2021");
        testCustomerRequest.setEmail("piet@planten.nl");
        testCustomerRequest.setPassword("password");
        var username = testCustomerRequest.getUsername();

        User user1 = new User();
        user1.setUsername(username);

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        assertThrows(UserNotFoundException.class, () -> userService.createCustomerUser(testCustomerRequest));
    }

    @Test
    public void testCreateEmployeeUserWithExistingUsername() {
        EmployeeRequest testEmployeeRequest = new EmployeeRequest();
        testEmployeeRequest.setUsername("Piet2021");
        testEmployeeRequest.setEmail("piet@planten.nl");
        testEmployeeRequest.setPassword("password");
        var username = testEmployeeRequest.getUsername();

        User user1 = new User();
        user1.setUsername(username);

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        assertThrows(UserNotFoundException.class, () -> userService.createEmployeeUser(testEmployeeRequest));
    }

    @Test
    public void testEditUser() {
        User user1 = new User();
        user1.setUsername("piet2021");
        user1.setEnabled(false);
        var username = user1.getUsername();

        Map<String, String> testFields = Map.ofEntries(
                Map.entry("status", "Actief"),
                Map.entry("email", "piet@hotmail.com")
        );

        when(userRepository.findById(username)).thenReturn(Optional.of(user1));

        userService.editUser(username, testFields);

        verify(userRepository, times(1)).findById(username);
        verify(userRepository, times(1)).save(user1);

        assertThat(user1.getEnabled()).isEqualTo(true);
        assertThat(user1.getEmail()).isEqualTo("piet@hotmail.com");

    }


}
