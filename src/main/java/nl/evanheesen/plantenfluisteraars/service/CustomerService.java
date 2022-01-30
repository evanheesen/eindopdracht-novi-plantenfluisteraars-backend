package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.model.Customer;

import java.util.Map;
import java.util.Optional;

public interface CustomerService {

    public long createCustomer(Customer customer);

    public void assignGardenToCustomer(long gardenId, long customerId);

    public Customer convertDTOToCustomer(CustomerRequest customerRequest);

}
