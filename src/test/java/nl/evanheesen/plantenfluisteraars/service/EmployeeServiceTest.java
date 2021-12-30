package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.model.User;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
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
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private GardenRepository gardenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Captor
    ArgumentCaptor<Employee> employeeCaptor;

    @Test
    public void testGetEmployees() {
        List<Employee> testEmployees = new ArrayList<>();
        Employee employee1 = new Employee(1L, "Piet", "Jansen");
        Employee employee2 = new Employee(2L, "Josie", "Gerritsen");
        Employee employee3 = new Employee(3L, "Klaas", "Frederiks");

        testEmployees.add(employee1);
        testEmployees.add(employee2);
        testEmployees.add(employee3);

        when(employeeRepository.findAll()).thenReturn(testEmployees);

        employeeService.getEmployees();

        verify(employeeRepository, times(1)).findAll();

        assertThat(testEmployees.size()).isEqualTo(3);
        assertThat(testEmployees.get(0)).isEqualTo(employee1);
        assertThat(testEmployees.get(1)).isEqualTo(employee2);
        assertThat(testEmployees.get(2)).isEqualTo(employee3);
    }

    @Test
    public void testGetEmployeesById() {
        Employee employee = new Employee(1L, "Piet", "Jansen");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        var employee1 = employeeService.getEmployeeById(1L);
        assertThat(employee1.get().getFirstName()).isEqualTo("Piet");
    }

    @Test
    public void testGetEmployeeException() {
        assertThrows(RecordNotFoundException.class, () -> employeeService.getEmployeeById(888));
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee(1L, "Piet", "Jansen");

        employeeRepository.save(employee);

        verify(employeeRepository, times(1)).save(employeeCaptor.capture());
        var employee1 = employeeCaptor.getValue();

        assertThat(employee1.getFirstName()).isEqualTo("Piet");
        assertThat(employee1.getId()).isEqualTo(1);
    }

    @Test
    public void testDeleteEmployee() {
        User testUser = new User();
        testUser.setUsername("Piet2021");
        testUser.setPassword("password");
        testUser.setEnabled(true);
        testUser.setEmail("piet@planten.nl");
        var username = testUser.getUsername();

        Employee testEmployee = new Employee(1L, "Piet", "Jansen");
        testEmployee.setUser(testUser);
        var id = testEmployee.getId();

        Collection<Garden> testGardens = new ArrayList<>();
        Garden garden1 = new Garden();
        garden1.setId(101L);
        garden1.setStatus("Actief");
        garden1.setEmployee(testEmployee);
        Garden garden2 = new Garden();
        garden2.setId(102L);
        garden2.setStatus("Afgerond");

        testGardens.add(garden1);
        testGardens.add(garden2);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(testEmployee));
        var employee1 = employeeService.getEmployeeById(id);

        when(gardenRepository.findAllByEmployeeId(id)).thenReturn(testGardens);

//        userRepository.deleteById(testEmployee.getUser().getUsername());
//        verify(userRepository, times(1)).delete(testUser);

        employeeRepository.deleteById(id);

        employeeService.deleteEmployee(id);

        verify(employeeRepository, times(1)).delete(testEmployee);

        assertThat(garden1.getEmployee()).isEqualTo(null);
        assertThat(garden1.getStatus()).isEqualTo("Open");
        assertThat(garden2.getStatus()).isEqualTo("Afgerond");
    }

    @Test
    public void testEditEmployee() {
        Employee employee1 = new Employee(1L, "Piet", "Jansen");
        employee1.setStreet("Kerkstraat");
        employee1.setHouseNumber("900");
        employee1.setPostalCode("1019PL");
        employee1.setCity("Amsterdam");
        employee1.setPhone("0611223344");
        employee1.setStatus("Actief");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        Map<String, String> testFields = Map.ofEntries(
                Map.entry("firstname", "Hans"),
                Map.entry("lastname", "van Aken"),
                Map.entry("street", ""),
                Map.entry("housenumber", "100"),
                Map.entry("postalcode", "3054KK"),
                Map.entry("city", "Utrecht"),
                Map.entry("phone", "0699887766"),
                Map.entry("status", "statusDefault")
        );

        employeeService.editEmployee(1L, testFields);

        verify(employeeRepository).save(employee1);

        assertThat(employee1.getId()).isEqualTo(1);
        assertThat(employee1.getFirstName()).isEqualTo("Hans");
        assertThat(employee1.getLastName()).isEqualTo("van Aken");
        assertThat(employee1.getStreet()).isEqualTo("Kerkstraat");
        assertThat(employee1.getCity()).isEqualTo("Utrecht");
        assertThat(employee1.getStatus()).isEqualTo("Actief");
    }

//    @Test
//    public void testConvertDTOToEmployee() {
//        Employee employee1 = new Employee();
//        EmployeeRequest testEmployeeRequest = new EmployeeRequest();
//        testEmployeeRequest.setUsername("piet2000");
//        testEmployeeRequest.setPassword("password");
//        testEmployeeRequest.setEmail("piet@planten.nl");
//        testEmployeeRequest.setFirstName("Piet");
//        testEmployeeRequest.setLastName("Jansen");
//        testEmployeeRequest.setStreet("Kerkstraat");
//        testEmployeeRequest.setHouseNumber("99");
//        testEmployeeRequest.setCity("Amsterdam");
//        testEmployeeRequest.setPhone("0688899911");
//
//        employeeService.convertDTOToEmployee(testEmployeeRequest);
//
//        assertThat(employee1.getFirstName()).isEqualTo("Piet");
//        assertThat(employee1.getCity()).isEqualTo("Amsterdam");
//    }

}
