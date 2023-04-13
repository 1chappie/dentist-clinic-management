package service;

import domain.Dentist;
import repository.IRepository;


public class DentistService extends IdentifiableService<Dentist> {
    public DentistService(IRepository<String, Dentist> link) {
        super(link);
    }

}
