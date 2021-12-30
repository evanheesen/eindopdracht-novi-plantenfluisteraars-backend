package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GardenServiceTest {

    @Mock
    private GardenRepository gardenRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private GardenService gardenService;

    @Captor
    ArgumentCaptor<Garden> gardenCaptor;

    @Test
    public void testGetAllGardens() {
        List<Garden> testGardens = new ArrayList<>();
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStreet("Kalverstraat");
        Garden garden2 = new Garden();
        garden2.setId(2L);
        garden2.setStreet("Leidscheplein");
        Garden garden3 = new Garden();
        garden3.setId(3L);
        garden3.setStreet("Kinkerstraat");

        testGardens.add(garden1);
        testGardens.add(garden2);
        testGardens.add(garden3);

        when(gardenRepository.findAll()).thenReturn(testGardens);

        gardenService.getAllGardens();

        verify(gardenRepository, times(1)).findAll();

        assertThat(testGardens.size()).isEqualTo(3);
        assertThat(testGardens.get(0)).isEqualTo(garden1);
        assertThat(testGardens.get(1)).isEqualTo(garden2);
        assertThat(testGardens.get(2)).isEqualTo(garden3);
    }

    @Test
    public void testGetGardenById() {
        Garden testGarden = new Garden();
        testGarden.setId(1L);
        testGarden.setStreet("Kalverstraat");

        when(gardenRepository.findById(1L)).thenReturn(Optional.of(testGarden));

        var garden1 = gardenService.getGardenById(1L);
        assertThat(garden1.get().getStreet()).isEqualTo("Kalverstraat");
    }

    @Test
    public void testGetGardenException() {
        assertThrows(RecordNotFoundException.class, () -> gardenService.getGardenById(888));
    }

    @Test
    public void testGetGardensByStatus() {
        List<Garden> testGardens = new ArrayList<>();
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStreet("Kalverstraat");
        garden1.setStatus("Actief");
        Garden garden2 = new Garden();
        garden2.setId(2L);
        garden2.setStreet("Leidscheplein");
        garden2.setStatus("Actief");
        Garden garden3 = new Garden();
        garden3.setId(3L);
        garden3.setStreet("Kinkerstraat");
        garden3.setStatus("Afgerond");

        testGardens.add(garden1);
        testGardens.add(garden3);

        when(gardenRepository.findAllByStatusIgnoreCase("Actief")).thenReturn(testGardens);

        gardenService.getGardensByStatus("Actief");

        verify(gardenRepository, times(1)).findAllByStatusIgnoreCase("Actief");

        assertThat(testGardens.size()).isEqualTo(2);
        assertThat(testGardens.get(0)).isEqualTo(garden1);
        assertThat(testGardens.get(1)).isEqualTo(garden3);
    }

    @Test
    public void testGetGardenByEmployeeId() {
        Employee employee1 = new Employee();
        employee1.setId(101L);
        employee1.setFirstName("Jan");

        Employee employee2 = new Employee();
        employee1.setId(102L);
        employee1.setFirstName("Gerda");

        List<Garden> testGardens = new ArrayList<>();
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStreet("Kalverstraat");
        garden1.setStatus("Actief");
        garden1.setEmployee(employee2);
        Garden garden2 = new Garden();
        garden2.setId(2L);
        garden2.setStreet("Leidscheplein");
        garden2.setStatus("Actief");
        garden2.setEmployee(employee1);
        Garden garden3 = new Garden();
        garden3.setId(3L);
        garden3.setStreet("Kinkerstraat");
        garden3.setStatus("Afgerond");

        testGardens.add(garden2);

        when(gardenRepository.findAllByEmployeeId(101L)).thenReturn(testGardens);

        gardenService.getGardensByEmployeeId(101L);

        verify(gardenRepository, times(1)).findAllByEmployeeId(101L);

        assertThat(testGardens.size()).isEqualTo(1);
        assertThat(testGardens.get(0)).isEqualTo(garden2);
        assertThat(testGardens.get(0).getEmployee()).isEqualTo(employee1);
    }

    @Test
    public void testGetGardenByCustomerId() {
        Customer customer1 = new Customer();
        customer1.setId(201L);
        customer1.setFirstName("Jan");

        Customer customer2 = new Customer();
        customer1.setId(202L);
        customer1.setFirstName("Gerda");

        List<Garden> testGardens = new ArrayList<>();
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStreet("Kalverstraat");
        garden1.setStatus("Actief");
        garden1.setCustomer(customer2);
        Garden garden2 = new Garden();
        garden2.setId(2L);
        garden2.setStreet("Leidscheplein");
        garden2.setStatus("Actief");
        garden2.setCustomer(customer1);
        Garden garden3 = new Garden();
        garden3.setId(3L);
        garden3.setStreet("Kinkerstraat");
        garden3.setStatus("Afgerond");

        testGardens.add(garden2);

        when(gardenRepository.findAllByCustomerId(201L)).thenReturn(testGardens);

        gardenService.getGardensByCustomerId(201L);

        verify(gardenRepository, times(1)).findAllByCustomerId(201L);

        assertThat(testGardens.size()).isEqualTo(1);
        assertThat(testGardens.get(0)).isEqualTo(garden2);
        assertThat(testGardens.get(0).getCustomer()).isEqualTo(customer1);
    }

    @Test
    public void testAddGarden() {
        Garden testGarden = new Garden();
        testGarden.setId(1L);
        testGarden.setStreet("Kalverstraat");

        gardenRepository.save(testGarden);

        verify(gardenRepository, times(1)).save(gardenCaptor.capture());
        var garden1 = gardenCaptor.getValue();

        assertThat(garden1.getId()).isEqualTo(1);
        assertThat(garden1.getStreet()).isEqualTo("Kalverstraat");
    }

    @Test
    public void testConvertDTOToGarden() {
        CustomerRequest testCustomerRequest = new CustomerRequest();
        testCustomerRequest.setStreet("Kalverstraat");
        testCustomerRequest.setHouseNumber("101");
        testCustomerRequest.setPostalCode("1019PK");
        testCustomerRequest.setCity("Amsterdam");
        testCustomerRequest.setPackagePlants("Package 1");

        Garden testGarden = gardenService.convertDTOToGarden(testCustomerRequest);

        assertThat(testGarden.getStatus()).isEqualTo("Open");
        assertThat(testGarden.getStreet()).isEqualTo("Kalverstraat");
        assertThat(testGarden.getHouseNumber()).isEqualTo("101");
        assertThat(testGarden.getPostalCode()).isEqualTo("1019PK");
        assertThat(testGarden.getCity()).isEqualTo("Amsterdam");
        assertThat(testGarden.getPackagePlants()).isEqualTo("Package 1");
        assertThat(testGarden.getSubmissionDate()).isEqualTo(LocalDate.now());
    }

    @Test
    public void testAddEmployeeToGarden() {
        Employee employee1 = new Employee(101L, "Piet", "Jansen");
        var employeeId = employee1.getId();

        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStatus("Inactief");
        var gardenId = garden1.getId();

        when(gardenRepository.findById(1L)).thenReturn(Optional.of(garden1));
        when(employeeRepository.findById(101L)).thenReturn(Optional.of(employee1));

        gardenService.addEmployeeToGarden(gardenId, employeeId);

        verify(gardenRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findById(101L);

        assertThat(garden1.getEmployee()).isEqualTo(employee1);
        assertThat(garden1.getStatus()).isEqualTo("Actief");
        assertThat(employee1.getStatus()).isEqualTo("Actief");
    }


}
