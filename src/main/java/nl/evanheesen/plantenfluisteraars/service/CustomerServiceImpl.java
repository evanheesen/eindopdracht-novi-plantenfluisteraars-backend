package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;
//    Dit toegevoegd voor DTO:
    private ModelMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public Iterable<Customer> getCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public Optional<Customer> getCustomerById(long id) {
    if (!customerRepository.existsById(id))
        throw new RecordNotFoundException("Bewoner met id " + id + " niet gevonden.");
    return customerRepository.findById(id);
    }

    public long createCustomer(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer.getId();
    }

//    public Customer createCustomer(Customer customer) {
//        Customer newCustomer = customerRepository.save(customer);
//        return newCustomer;
//    }

    public void partialUpdateCustomer(long id, Map<String, String> fields) {
        if (!customerRepository.existsById(id)) {
            throw new RecordNotFoundException();
        }
        Customer customer = customerRepository.findById(id).get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "first_name":
                    customer.setFirstName((String) fields.get(field));
                    break;
                case "last_name":
                    customer.setLastName((String) fields.get(field));
                    break;
                case "street":
                    customer.setStreet((String) fields.get(field));
                    break;
                case "housenumber":
                    customer.setHouseNumber((String) fields.get(field));
                    break;
                case "city":
                    customer.setCity((String) fields.get(field));
                    break;
                case "phone":
                    customer.setPhone((String) fields.get(field));
                    break;

            }
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(long id) {
        if (!customerRepository.existsById(id)) { throw new RecordNotFoundException(); }
        customerRepository.deleteById(id);
    }

//    Dit toegevoegd voor DTO:
    public CustomerRequest convertCustomerToDTO(Customer customer) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFirstName(customer.getFirstName());
        customerRequest.setLastName(customer.getLastName());
        return customerRequest;
    }

    public Customer convertDTOToCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        return customer;
    }

//    converters with mapper:

//    public CustomerRequest convertCustomerToDTO(Customer customer) {
//        CustomerRequest customerRequest = mapper.map(customer, CustomerRequest.class);
//        return customerRequest;

//    public Customer convertDTOTOCustomer(CustomerRequest customerRequest) {
//        Customer customer = mapper.map(customerRequest, Customer.class);
//        return customer;
//    }

}
