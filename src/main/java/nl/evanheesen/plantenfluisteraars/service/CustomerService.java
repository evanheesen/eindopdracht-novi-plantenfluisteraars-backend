package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Employee;

import java.util.Map;
import java.util.Optional;

public interface CustomerService {

    public Iterable<Customer> getCustomers();

    public Optional<Customer> getCustomerById(long id);

    public long createCustomer(Customer customer);

    public void partialUpdateCustomer(long id, Map<String, String> fields);

    public void deleteCustomer(long id);

    public void assignGardenToCustomer(long gardenId, long customerId);

//    //    Dit toegevoegd voor DTO
    public CustomerRequest convertCustomerToDTO(Customer customer);

    public Customer convertDTOToCustomer(CustomerRequest customerRequest);

}
