package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.model.Customer;

import java.util.Map;
import java.util.Optional;

public interface CustomerService {

    public Iterable<Customer> getCustomers();

    public Optional<Customer> getCustomerById(long id);

    public long createCustomer(Customer customer);

    public void partialUpdateCustomer(long id, Map<String, String> fields);

    public void deleteCustomer(long id);

}
