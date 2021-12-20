package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private GardenRepository gardenRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository, GardenRepository gardenRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.gardenRepository = gardenRepository;
    }

    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
    if (optionalCustomer.isEmpty())
        throw new RecordNotFoundException("Bewoner met id " + id + " niet gevonden.");
    return optionalCustomer;
    }

    public long createCustomer(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer.getId();
    }

    public void partialUpdateCustomer(long id, Map<String, String> fields) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException();
        }
        Customer customer = optionalCustomer.get();
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
        Optional<Garden> optionalGarden = gardenRepository.findById(gardenId);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalGarden.isPresent() && optionalCustomer.isPresent()) {
            Garden garden = optionalGarden.get();
            Customer customer = optionalCustomer.get();
            garden.setCustomer(customer);
            customer.setGarden(garden);
            gardenRepository.save(garden);
            customerRepository.save(customer);
        } else {
            throw new RecordNotFoundException();
        }
    }

}
