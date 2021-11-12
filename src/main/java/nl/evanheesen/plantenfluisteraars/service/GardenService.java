package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Customer;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class GardenService {

    private GardenRepository gardenRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public GardenService(GardenRepository gardenRepository, EmployeeRepository employeeRepository) {
        this.gardenRepository = gardenRepository;
        this.employeeRepository = employeeRepository;
    }

    public Collection<Garden> getAllGardens() {
        Collection<Garden> gardens = gardenRepository.findAll();
        return gardens;
    }

    public Optional<Garden> getGardenById(long id) {
        if (!gardenRepository.existsById(id))
            throw new RecordNotFoundException("Geveltuintje met id " + id + " niet gevonden.");
        return gardenRepository.findById(id);
    }

    public Collection<Garden> getGardensByStatus(String status) {
        return gardenRepository.findAllByStatusIgnoreCase(status);
    }

    public Collection<Garden> getGardensByEmployeeId(long employeeId) {
        return gardenRepository.findAllByEmployeeId(employeeId);
    }

    public Collection<Garden> getGardensByCustomerId(long id) {
        return gardenRepository.findAllByCustomerId(id);
    }

    public long addGarden(Garden garden) {
        Garden newGarden = gardenRepository.save(garden);
        return newGarden.getId();
    }

    public Garden convertDTOToGarden(CustomerRequest customerRequest) {
        Garden garden = new Garden();
        garden.setStatus("Open");
        garden.setSubmissionDate(customerRequest.getSubmissionDate());
        garden.setStreet(customerRequest.getStreet());
        garden.setHouseNumber(customerRequest.getHouseNumber());
        garden.setPostalCode(customerRequest.getPostalCode());
        garden.setCity(customerRequest.getCity());
        garden.setPackagePlants(customerRequest.getPackagePlants());
        return garden;
    }

    public void addEmployeeToGarden(long id, long employeeId) {
        Optional<Garden> optionalGarden = gardenRepository.findById(id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalGarden.isPresent() && optionalEmployee.isPresent()) {
            Garden garden = optionalGarden.get();
            Employee employee = optionalEmployee.get();
            garden.setEmployee(employee);
            garden.setStatus("Actief");
            gardenRepository.save(garden);
        }
        else {
            throw new RecordNotFoundException();
        }
    }

    public void updateGarden(long id, long employeeId, Map<String, String> fields) {
        if (!gardenRepository.existsById(id)) {
            throw new RecordNotFoundException("Geveltuin kan niet gevonden worden");
        }
        Garden garden = gardenRepository.findById(id).get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "status":
                    garden.setStatus((String) fields.get(field));
                    if(fields.get(field).equals("Actief")) {
                        addEmployeeToGarden(id, employeeId);
                    }
                    break;
                case "house_number":
                    garden.setHouseNumber((String) fields.get(field));
                    break;
                case "package_plants":
                    garden.setPackagePlants((String) fields.get(field));
                    break;
            }
        }
        gardenRepository.save(garden);
    }


}
