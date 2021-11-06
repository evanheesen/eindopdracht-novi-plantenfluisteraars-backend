package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

//    @Autowired
    private CustomerRepository customerRepository;
    private UserRepository userRepository;

//    Moderne manier in plaats van @Autowired:
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

//    @Autowired
//    private BewonersController bewonersController;

    public Iterable<Customer> getCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public Optional<Customer> getCustomerById(long id) {
    if (!customerRepository.existsById(id)) throw new RecordNotFoundException("Klant met id " + id + " niet gevonden.");
    return customerRepository.findById(id);
    }

    public long createCustomer(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);

        return newCustomer.getId();
    }

    public void partialUpdateCustomer(long id, Map<String, String> fields) {
        if (!customerRepository.existsById(id)) { throw new RecordNotFoundException(); }
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

                    // hoe phone (type long) aan te passen?
//                case "phone":
//                    bewoner.setPhone((long) fields.get(field));
//                    break;
            }
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(long id) {
        if (!customerRepository.existsById(id)) { throw new RecordNotFoundException(); }
        customerRepository.deleteById(id);
    }

    @Override
    public void assignUserToCustomer(String userId, long customerId) {
        var optionalUser = userRepository.findById(userId);
        var optionalCustomer = customerRepository.findById(customerId);
        if (optionalUser.isPresent() && optionalCustomer.isPresent()) {
            var user = optionalUser.get();
            var customer = optionalCustomer.get();
            customer.setUser(user);
            customerRepository.save(customer);
        } else {
            throw new RecordNotFoundException();
        }
    }


}
