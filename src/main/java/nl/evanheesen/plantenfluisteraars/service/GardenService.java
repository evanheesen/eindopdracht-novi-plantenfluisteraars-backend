//package nl.evanheesen.plantenfluisteraars.service;
//
//import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
//import nl.evanheesen.plantenfluisteraars.model.Customer;
//import nl.evanheesen.plantenfluisteraars.model.Garden;
//import nl.evanheesen.plantenfluisteraars.repository.CustomerRepository;
//import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
//import nl.evanheesen.plantenfluisteraars.repository.GardenRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//
//@Service
//public class GardenService {
//
//    private GardenRepository gardenRepository;
//
//    @Autowired
//    public GardenService(GardenRepository gardenRepository) {
//        this.gardenRepository = gardenRepository;
//    }
//
//    public Collection<Garden> getAllGardens() {
//        Collection<Garden> gardens = gardenRepository.findAll();
//        return gardens;
//    }
//
//    public Collection<Garden> getGardensByEmployeeId(long employeeId) {
//        return gardenRepository.findAllByEmployeeId(employeeId);
//    }
//
//    public Collection<Garden> getGardensByCustomerId(long customerId) {
//        return gardenRepository.findAllByCustomerId(customerId);
//    }
//
//    public long addGarden(Garden garden) {
//        Garden newGarden = gardenRepository.save(garden);
//        return newGarden.getId();
//    }
//
//}
