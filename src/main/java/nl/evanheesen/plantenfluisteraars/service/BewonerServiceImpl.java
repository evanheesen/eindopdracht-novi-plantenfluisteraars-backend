package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.exception.RecordNotFoundException;
import nl.evanheesen.plantenfluisteraars.model.Bewoner;
import nl.evanheesen.plantenfluisteraars.repository.BewonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class BewonerServiceImpl implements BewonerService {

//    @Autowired
    private BewonerRepository bewonerRepository;

//    Moderne manier in plaats van @Autowired:
    @Autowired
    public BewonerServiceImpl(BewonerRepository bewonerRepository) {
        this.bewonerRepository = bewonerRepository;
    }

//    @Autowired
//    private BewonersController bewonersController;

    public Iterable<Bewoner> getBewoners() {
        Iterable<Bewoner> bewoners = bewonerRepository.findAll();
        return bewoners;
    }

    public Optional<Bewoner> getBewonerById(long id) {
    if (!bewonerRepository.existsById(id)) throw new RecordNotFoundException("Persoon met id " + id + " niet gevonden.");
    return bewonerRepository.findById(id);
    }

    public long createBewoner(Bewoner bewoner) {
        Bewoner newBewoner = bewonerRepository.save(bewoner);
        return newBewoner.getId();
    }

    public void partialUpdateBewoner(long id, Map<String, String> fields) {
        if (!bewonerRepository.existsById(id)) { throw new RecordNotFoundException(); }
        Bewoner bewoner = bewonerRepository.findById(id).get();
        for (String field : fields.keySet()) {
            switch (field.toLowerCase()) {
                case "first_name":
                    bewoner.setFirstName((String) fields.get(field));
                    break;
                case "last_name":
                    bewoner.setLastName((String) fields.get(field));
                    break;
                case "street":
                    bewoner.setStreet((String) fields.get(field));
                    break;
                case "housenumber":
                    bewoner.setHouseNumber((String) fields.get(field));
                    break;
                case "city":
                    bewoner.setCity((String) fields.get(field));
                    break;

                    // hoe phone (type long) aan te passen?
//                case "phone":
//                    bewoner.setPhone((long) fields.get(field));
//                    break;
            }
        }
        bewonerRepository.save(bewoner);
    }

    public void deleteBewoner(long id) {
        if (!bewonerRepository.existsById(id)) { throw new RecordNotFoundException(); }
        bewonerRepository.deleteById(id);
    }

    //    @Autowired
//    public class BewonerController(BewonerService bewonerService) {
//        this.bewonerService = bewonerService;
//    }


}
