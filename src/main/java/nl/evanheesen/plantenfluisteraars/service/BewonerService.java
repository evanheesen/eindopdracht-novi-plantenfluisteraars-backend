package nl.evanheesen.plantenfluisteraars.service;

import nl.evanheesen.plantenfluisteraars.model.Bewoner;

import java.util.Map;
import java.util.Optional;

public interface BewonerService {

    public Iterable<Bewoner> getBewoners();

    public Optional<Bewoner> getBewonerById(long id);

    public long createBewoner(Bewoner bewoner);

    public void partialUpdateBewoner(long id, Map<String, String> fields);

    public void deleteBewoner(long id);

}
