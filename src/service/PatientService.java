package service;

import domain.Patient;
import repository.IRepository;

public class PatientService extends IdentifiableService<Patient> {
    public PatientService(IRepository<String,Patient> link) {
        super(link);
    }
}
