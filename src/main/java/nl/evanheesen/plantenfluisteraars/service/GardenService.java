package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.dto.request.CustomerRequest;
import nl.evanheesen.plantenfluisteraars.exception.NotAuthorizedException;
import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Employee;
import nl.evanheesen.plantenfluisteraars.model.Garden;
import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return gardenRepository.findAll();
    }

    public Optional<Garden> getGardenById(long id) {
        Optional<Garden> optionalGarden = gardenRepository.findById(id);
        if (optionalGarden.isEmpty())
            throw new RecordNotFoundException("Geveltuintje met id " + id + " niet gevonden.");
        return optionalGarden;
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
            employee.setStatus("Actief");
            gardenRepository.save(garden);
        } else {
            throw new RecordNotFoundException();
        }
    }

    public void deleteEmployeeFromGarden(long id, long employeeId, String valueStatus) {
        Optional<Garden> optionalGarden = gardenRepository.findById(id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalGarden.isPresent() && optionalEmployee.isPresent()) {
            Garden garden = optionalGarden.get();
            Employee employee = optionalEmployee.get();
            int gardensEmployee = getGardensByEmployeeId(employeeId).size();
            garden.setEmployee(null);
            garden.setStatus(valueStatus);
            if (gardensEmployee == 1) {
                employee.setStatus("Inactief");
            }
            gardenRepository.save(garden);
        } else {
            throw new RecordNotFoundException();
        }
    }

    public void updateGarden(long id, long employeeId, Map<String, String> fields) {
        Optional<Garden> optionalGarden = gardenRepository.findById(id);
        if (optionalGarden.isEmpty()) {
            throw new RecordNotFoundException("Geveltuin kan niet gevonden worden");
        }
        Garden garden = optionalGarden.get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "status":
                    if (garden.getStatus().equals("Open") && fields.containsValue("Actief")) {
                        addEmployeeToGarden(id, employeeId);
                    } else if (garden.getStatus().equals("Actief") && fields.containsValue("Afgerond")) {
                        String valueStatus = fields.get(field);
                        deleteEmployeeFromGarden(id, employeeId, valueStatus);
                    } else {
                        throw new NotAuthorizedException("Deze statuswijziging is niet mogelijk");
                    }
                    break;
                case "street":
                    garden.setStreet((String) fields.get(field));
                    break;
                case "houseNumber":
                    garden.setHouseNumber((String) fields.get(field));
                    break;
                case "postalCode":
                    garden.setPostalCode((String) fields.get(field));
                    break;
                case "city":
                    garden.setCity((String) fields.get(field));
                    break;
                case "packagePlants":
                    garden.setPackagePlants((String) fields.get(field));
                    break;
            }
        }
        gardenRepository.save(garden);
    }

    public void editGardenByAdmin(long id, Map<String, String> fields) {
        Optional<Garden> optionalGarden = gardenRepository.findById(id);
        if (optionalGarden.isEmpty()) {
            throw new RecordNotFoundException();
        }
        Garden garden = optionalGarden.get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "firstname":
                    if (!fields.get(field).equals("")) {
                        garden.getCustomer().setFirstName((String) fields.get(field));
                    }
                    break;
                case "lastname":
                    if (!fields.get(field).equals("")) {
                        garden.getCustomer().setLastName((String) fields.get(field));
                    }
                    break;
                case "street":
                    if (!fields.get(field).equals("")) {
                        garden.setStreet((String) fields.get(field));
                    }
                    break;
                case "housenumber":
                    if (!fields.get(field).equals("")) {
                        garden.setHouseNumber((String) fields.get(field));
                    }
                    break;
                case "postalcode":
                    if (!fields.get(field).equals("")) {
                        garden.setPostalCode((String) fields.get(field));
                    }
                    break;
                case "city":
                    if (!fields.get(field).equals("")) {
                        garden.setCity((String) fields.get(field));
                    }
                    break;
                case "packageplants":
                    String newPackage = fields.get(field);
                    if (!newPackage.equalsIgnoreCase("packageDefault")) {
                        garden.setPackagePlants(newPackage);
                    }
                    break;
                case "status":
                    String newStatus = fields.get(field);
                    String currentStatus = garden.getStatus();
                    if (!newStatus.equalsIgnoreCase("statusDefault")) {
                        long employeeId = garden.getEmployee().getId();
                        if ((newStatus.equals("Open") || newStatus.equals("Afgerond")) && currentStatus.equals("Actief")) {
                            deleteEmployeeFromGarden(id, employeeId, newStatus);
                        } else {
                            garden.setStatus(newStatus);
                        }
                    }
                    break;
                case "employee":
                    String newEmployee = fields.get(field);
                    if (!newEmployee.equalsIgnoreCase("employeeDefault")) {
                        Long idNewEmployee = Long.valueOf(newEmployee);
                        addEmployeeToGarden(id, idNewEmployee);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + field.toLowerCase());
            }
        }
        gardenRepository.save(garden);
    }

    public void deleteGarden(long id) {
        Optional<Garden> optionalGarden = gardenRepository.findById(id);
        if (optionalGarden.isEmpty()) {
            throw new RecordNotFoundException();
        }
        Garden garden = optionalGarden.get();
        Employee employee = garden.getEmployee();
        if (employee != null) {
            long employeeId = garden.getEmployee().getId();
            String employeeStatus = garden.getEmployee().getStatus();
            deleteEmployeeFromGarden(id, employeeId, employeeStatus);
        }
        gardenRepository.deleteById(id);
    }

}
