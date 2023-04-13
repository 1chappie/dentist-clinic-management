package service;

import domain.Appointment;
import domain.Identifiable;
import repository.IRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentService extends IdentifiableService<Appointment> {

    public AppointmentService(IRepository<String, Appointment> link) {
        super(link);
    }

}
