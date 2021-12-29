//package nl.evanheesen.plantenfluisteraars.service;
//
//import nl.evanheesen.plantenfluisteraars.PlantenfluisteraarsApplication;
//import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
//import nl.evanheesen.plantenfluisteraars.model.Customer;
//import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@ContextConfiguration(classes = {PlantenfluisteraarsApplication.class})
//@EnableConfigurationProperties
//public class CustomerServiceImplTest {
//
//    @Autowired
//    private CustomerServiceImpl customerServiceImpl;
//
//    @MockBean
//    private CustomerRepository customerRepository;
//
//    @Mock
//    Customer customer;
//
////    @Test
////    public void testConvertDTOToCustomer() {
////        CustomerRequest customerRequest = new CustomerRequest("piet2021", "password", "piet@planten.nl", null , "Piet", "Jansen", "Kerkstraat", "10", "1091AP", "Amsterdam", "0677788812", "Package 1");
////        customer.setFirstName(customerRequest.getFirstName());
////        String expected = "Piet";
////        assertEquals(expected, customer.getFirstName());
////    }
//
//    @Test
//    public void testOptionalCustomer() {
//        customer = new Customer(1001, "piet", "jansen", "0847848998");
//
//        CustomerRequest customerRequest = new CustomerRequest("piet2021", "password", "piet@planten.nl", null , "Piet", "Jansen", "Kerkstraat", "10", "1091AP", "Amsterdam", "0677788812", "Package 1");
//        customer.setFirstName(customerRequest.getFirstName());
//        String expected = "Piet";
//        assertEquals(expected, customer.getFirstName());
//    }
//
//
//
//}
