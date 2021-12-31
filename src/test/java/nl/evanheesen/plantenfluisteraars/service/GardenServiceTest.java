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
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStreet("Kalverstraat");

        gardenRepository.save(garden1);

        verify(gardenRepository, times(1)).save(gardenCaptor.capture());
        var testGarden = gardenCaptor.getValue();

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
        garden1.setStatus("Open");
        var gardenId = garden1.getId();

        when(gardenRepository.findById(gardenId)).thenReturn(Optional.of(garden1));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee1));

        gardenService.addEmployeeToGarden(gardenId, employeeId);

        verify(gardenRepository, times(1)).findById(gardenId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(gardenRepository).save(garden1);

        assertThat(garden1.getEmployee()).isEqualTo(employee1);
        assertThat(garden1.getStatus()).isEqualTo("Actief");
        assertThat(employee1.getStatus()).isEqualTo("Actief");
    }

    @Test
    public void testDeleteEmployeeFromGarden() {
        Employee employee1 = new Employee(101L, "Piet", "Jansen");
        var employeeId = employee1.getId();

        List<Garden> testGardens = new ArrayList<>();
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStreet("Kalverstraat");
        garden1.setStatus("Actief");
        garden1.setEmployee(employee1);
        var garden1Id = garden1.getId();
        Garden garden2 = new Garden();
        garden2.setId(2L);
        garden2.setStreet("Wibautstraat");
        garden2.setStatus("Open");

        testGardens.add(garden1);

        when(gardenRepository.findById(garden1Id)).thenReturn(Optional.of(garden1));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee1));
        when(gardenRepository.findAllByEmployeeId(employeeId)).thenReturn(testGardens);

        gardenService.deleteEmployeeFromGarden(garden1Id, employeeId, "Afgerond");

        verify(gardenRepository, times(1)).findById(garden1Id);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(gardenRepository, times(1)).findAllByEmployeeId(employeeId);
        verify(gardenRepository).save(garden1);

        assertThat(garden1.getEmployee()).isEqualTo(null);
        assertThat(garden1.getStatus()).isEqualTo("Afgerond");
        assertThat(employee1.getStatus()).isEqualTo("Inactief");
    }

    @Test
    public void testEditGardenByEmployee() {
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStatus("Open");
        garden1.setStreet("Neude");
        garden1.setHouseNumber("100");
        garden1.setPostalCode("3091FG");
        garden1.setCity("Utrecht");
        garden1.setPackagePlants("Pakket 2");
        var gardenId = garden1.getId();

        Employee employee1 = new Employee(101L, "Piet", "Jansen");
        var employeeId = employee1.getId();

        Map<String, String> testFields = Map.ofEntries(
                Map.entry("street", "Nassaukade"),
                Map.entry("houseNumber", "66"),
                Map.entry("postalCode", "1016TK"),
                Map.entry("city", "Amsterdam"),
                Map.entry("status", "Actief"),
                Map.entry("packagePlants", "Pakket 1")
        );

        when(gardenRepository.findById(gardenId)).thenReturn(Optional.of(garden1));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee1));

        gardenService.editGardenByEmployee(gardenId, employeeId, testFields);

        verify(gardenRepository, times(2)).findById(1L);
        verify(employeeRepository, times(1)).findById(101L);
        verify(gardenRepository, times(2)).save(garden1);

        assertThat(garden1.getStatus()).isEqualTo("Actief");
        assertThat(garden1.getEmployee()).isEqualTo(employee1);
        assertThat(garden1.getStreet()).isEqualTo("Nassaukade");
        assertThat(garden1.getHouseNumber()).isEqualTo("66");
        assertThat(garden1.getPostalCode()).isEqualTo("1016TK");
        assertThat(garden1.getCity()).isEqualTo("Amsterdam");
        assertThat(garden1.getPackagePlants()).isEqualTo("Pakket 1");
    }

    @Test
    public void testEditGardenByAdmin() {
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStatus("Open");
        garden1.setStreet("Neude");
        garden1.setHouseNumber("100");
        garden1.setPostalCode("3091FG");
        garden1.setCity("Utrecht");
        garden1.setPackagePlants("Pakket 2");
        var gardenId = garden1.getId();

        Employee employee1 = new Employee(101L, "Piet", "Jansen");
        var employeeId = employee1.getId();

        Map<String, String> testFields = Map.ofEntries(
                Map.entry("street", "Nassaukade"),
                Map.entry("houseNumber", "66"),
                Map.entry("postalCode", ""),
                Map.entry("city", ""),
                Map.entry("status", "Actief"),
                Map.entry("employee", "101"),
                Map.entry("packagePlants", "Pakket 1")
        );

        when(gardenRepository.findById(gardenId)).thenReturn(Optional.of(garden1));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee1));

        gardenService.editGardenByAdmin(gardenId, testFields);

        verify(gardenRepository, times(2)).findById(1L);
        verify(employeeRepository, times(1)).findById(101L);
        verify(gardenRepository, times(2)).save(garden1);

        assertThat(garden1.getStatus()).isEqualTo("Actief");
        assertThat(garden1.getEmployee()).isEqualTo(employee1);
        assertThat(garden1.getStreet()).isEqualTo("Nassaukade");
        assertThat(garden1.getHouseNumber()).isEqualTo("66");
        assertThat(garden1.getPostalCode()).isEqualTo("3091FG");
        assertThat(garden1.getCity()).isEqualTo("Utrecht");
        assertThat(garden1.getPackagePlants()).isEqualTo("Pakket 1");
    }

    @Test
    public void testDeleteGarden() {
        Employee employee1 = new Employee(101L, "Piet", "Jansen");
        employee1.setStatus("Actief");
        var employeeId = employee1.getId();

        List<Garden> testGardens = new ArrayList<>();
        Garden garden1 = new Garden();
        garden1.setId(1L);
        garden1.setStatus("Actief");
        garden1.setEmployee(employee1);
        var gardenId = garden1.getId();

        testGardens.add(garden1);

        when(gardenRepository.findById(gardenId)).thenReturn(Optional.of(garden1));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee1));
        when(gardenRepository.findAllByEmployeeId(employeeId)).thenReturn(testGardens);

        gardenService.deleteGarden(gardenId);

        verify(gardenRepository, times(2)).findById(gardenId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(gardenRepository, times(1)).findAllByEmployeeId(employeeId);
        verify(gardenRepository).save(garden1);
        verify(gardenRepository).deleteById(gardenId);

    }

}
