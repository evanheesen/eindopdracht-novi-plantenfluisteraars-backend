//package nl.evanheesen.plantenfluisteraars.service;
//
//import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
//import nl.evanheesen.plantenfluisteraars.model.Employee;
//import nl.evanheesen.plantenfluisteraars.repository.EmployeeRepository;
//import nl.evanheesen.plantenfluisteraars.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.Optional;
//
//
//@Service
//public class EmployeeServiceImpl implements EmployeeService {
//
//    //    @Autowired
//    private EmployeeRepository employeeRepository;
//    private UserRepository userRepository;
//
//    //    Moderne manier in plaats van @Autowired:
//    @Autowired
//    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository) {
//        this.employeeRepository = employeeRepository;
//        this.userRepository = userRepository;
//    }
//
////    @Autowired
////    private BewonersController bewonersController;
//
//    public Iterable<Employee> getEmployees() {
//        Iterable<Employee> employees = employeeRepository.findAll();
//        return employees;
//    }
//
//    public Optional<Employee> getEmployeeById(long id) {
//        if (!employeeRepository.existsById(id))
//            throw new RecordNotFoundException("Plantenfluisteraar met id " + id + " niet gevonden.");
//        return employeeRepository.findById(id);
//    }
//
//    public long createEmployee(Employee employee) {
//        Employee newEmployee = employeeRepository.save(employee);
//        return newEmployee.getId();
//    }
//
//    public void partialUpdateEmployee(long id, Map<String, String> fields) {
//        if (!employeeRepository.existsById(id)) {
//            throw new RecordNotFoundException();
//        }
//        Employee employee = employeeRepository.findById(id).get();
//        for (String field : fields.keySet()) {
//            switch (field.toLowerCase()) {
//                case "first_name":
//                    employee.setFirstName((String) fields.get(field));
//                    break;
//                case "last_name":
//                    employee.setLastName((String) fields.get(field));
//                    break;
//                case "street":
//                    employee.setStreet((String) fields.get(field));
//                    break;
//                case "housenumber":
//                    employee.setHouseNumber((String) fields.get(field));
//                    break;
//                case "city":
//                    employee.setCity((String) fields.get(field));
//                    break;
//                case "phone":
//                    employee.setPhone((String) fields.get(field));
//                    break;
//
//                // hoe phone (type long) aan te passen?
////                case "phone":
////                    bewoner.setPhone((long) fields.get(field));
////                    break;
//            }
//        }
//        employeeRepository.save(employee);
//    }
//
//    public void deleteEmployee(long id) {
//        if (!employeeRepository.existsById(id)) {
//            throw new RecordNotFoundException();
//        }
//        employeeRepository.deleteById(id);
//    }
//
//    @Override
//    public void assignUserToEmployee(String userId, long employeeId) {
//       var optionalUser = userRepository.findById(userId);
//       var optionalEmployee = employeeRepository.findById(employeeId);
//       if (optionalUser.isPresent() && optionalEmployee.isPresent()) {
//           var user = optionalUser.get();
//           var employee = optionalEmployee.get();
//           employee.setUser(user);
//           employeeRepository.save(employee);
//       } else {
//           throw new RecordNotFoundException();
//       }
//    }
//
//}
