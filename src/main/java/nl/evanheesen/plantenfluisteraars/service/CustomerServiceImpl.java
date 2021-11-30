package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
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
    private GardenRepository gardenRepository;
//    Dit toegevoegd voor DTO:
    private ModelMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, GardenRepository gardenRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.gardenRepository = gardenRepository;
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

    public CustomerRequest convertCustomerToDTO(Customer customer) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFirstName(customer.getFirstName());
        customerRequest.setLastName(customer.getLastName());
        customerRequest.setPhone(customer.getPhone());
        return customerRequest;
    }

    public Customer convertDTOToCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setPhone(customerRequest.getPhone());
        return customer;
    }

    public void assignGardenToCustomer(long gardenId, long customerId) {
        var optionalGarden = gardenRepository.findById(gardenId);
        var optionalCustomer = customerRepository.findById(customerId);
        if (optionalGarden.isPresent() && optionalCustomer.isPresent()) {
            var garden = optionalGarden.get();
            var customer = optionalCustomer.get();
            garden.setCustomer(customer);
            customer.setGarden(garden);
            gardenRepository.save(garden);
            customerRepository.save(customer);
        } else {
            throw new RecordNotFoundException();
        }
    }

}
