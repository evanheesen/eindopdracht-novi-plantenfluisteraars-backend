package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import org.checkerframework.checker.units.qual.C;
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
    public void testAssignGardenToCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("Jan");

        Garden

    }

}
