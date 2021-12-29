package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.EmployeeRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
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

//    @Test
//    public void testDeleteEmployee() {
//        Employee employee = new Employee(1L, "Piet", "Jansen");
//
//        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
//        var employee1 = employeeService.getEmployeeById(1L);
//
//        employeeRepository.deleteById(1L);
//
//        employeeService.deleteEmployee(1L);
//
//        verify(employeeRepository, times(1)).delete(employee);
//
//    }

    @Test
    public void testEditEmployee() {
        Employee employee1 = new Employee(1L, "Piet", "Jansen");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        Map<String, String> testFields = Map.ofEntries(
                Map.entry("firstname", "Hans"),
                Map.entry("lastname", "van Aken")
        );

        employeeService.editEmployee(1L, testFields);

        verify(employeeRepository).save(employee1);

        assertThat(employee1.getId()).isEqualTo(1);
        assertThat(employee1.getFirstName()).isEqualTo("Hans");
        assertThat(employee1.getLastName()).isEqualTo("van Aken");
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
