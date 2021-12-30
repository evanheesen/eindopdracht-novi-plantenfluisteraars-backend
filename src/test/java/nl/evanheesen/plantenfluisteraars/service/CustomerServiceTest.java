package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GardenRepository gardenRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Captor
    ArgumentCaptor<Customer> customerCaptor;

    @Test
    public void testCreateCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("Jan");

        customerRepository.save(testCustomer);

        verify(customerRepository, times(1)).save(customerCaptor.capture());
        var customer1 = customerCaptor.getValue();

        assertThat(customer1.getFirstName()).isEqualTo("Jan");
        assertThat(customer1.getId()).isEqualTo(1);
    }

    @Test
    public void testConvertDTOToCustomer() {
        CustomerRequest testCustomerRequest = new CustomerRequest();
        testCustomerRequest.setFirstName("Jan");
        testCustomerRequest.setLastName("Klaasen");
        testCustomerRequest.setPhone("0688899911");

        customerService.convertDTOToCustomer(testCustomerRequest);
        Customer testCustomer = customerService.convertDTOToCustomer(testCustomerRequest);

        assertThat(testCustomer.getFirstName()).isEqualTo("Jan");
        assertThat(testCustomer.getPhone()).isEqualTo("0688899911");
    }

    @Test
    public void testAssignGardenToCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("Jan");
        var customerId = testCustomer.getId();

        Garden testGarden = new Garden();
        testGarden.setId(101L);
        testGarden.setCustomer(testCustomer);
        var gardenId = testGarden.getId();

        when(gardenRepository.findById(gardenId)).thenReturn(Optional.of(testGarden));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));

        customerService.assignGardenToCustomer(gardenId, customerId);

        verify(gardenRepository).save(testGarden);
        verify(customerRepository).save(testCustomer);

        assertThat(testCustomer.getGarden()).isEqualTo(testGarden);
        assertThat(testGarden.getCustomer()).isEqualTo(testCustomer);

    }

}
