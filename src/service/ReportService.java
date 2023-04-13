package service;

import domain.Appointment;
import domain.Dentist;
import domain.Identifiable;
import domain.Patient;
import exception.RepoException;
import repository.IRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportService {
    private AppointmentService as = null;
    private DentistService ds = null;
    private PatientService ps = null;

    public ReportService(AppointmentService appServ,
                         DentistService dentistServ,
                         PatientService patServ) {
        this.as = appServ;
        this.ds = dentistServ;
        this.ps = patServ;
    }

    // Bullet point 1 from the lab
    // I did not add this to CLI because I would need to refactor a lot
    // 0. Display all entities with a consumer
    public void displayAll() {
        Consumer<Identifiable<String>> display = System.out::println;
        as.get().forEach(display);
        ds.get().forEach(display);
        ps.get().forEach(display);
    }

    // Bullet point 2 from the lab
    // 1. Get all appointments on a given date
    public ArrayList<Appointment> appointmentsOn(LocalDate date) {
        Predicate<Appointment> sameDate = a -> a.getDate().equals(date);
        return (ArrayList<Appointment>) as.get()
                .stream()
                .filter(sameDate)
                .collect(Collectors.toList());
    }

    // Bullet point 3 from the lab
    // 2. Get all appointments of a given entity sorted by date
    public ArrayList<Appointment> appointmentsOf(String id) {
        return (ArrayList<Appointment>) as.get()
                .stream()
                .filter(a -> a.getDentistID().equals(id) || a.getPatientID().equals(id))
                .sorted(Comparator.comparing(Appointment::getDate))
                .collect(Collectors.toList());
    }

    public ArrayList<Appointment> appointmentsOf(Identifiable<String> id) {
        return appointmentsOf(id.getID());
    }

    // 3. Get all comments of a given patient
    public ArrayList<String> commentsOf(String id) {
        return (ArrayList<String>) appointmentsOf(id)
                .stream()
                .map(Appointment::getComments)
                .collect(Collectors.toList());
    }

    public ArrayList<String> commentsOf(Patient id) {
        return commentsOf(id.getID());
    }

    // 4. Get phone number of a given patient
    public String phoneOf(String id) {
        try {
            return ps.get(id).getPhone();
        } catch (Exception e) {
            return "";
        }
    }

    public String phoneOf(Identifiable<String> id) {
        return phoneOf(id.getID());
    }

    // 5. Get the Emails of all patients of a given dentist
    public ArrayList<String> patientEmailsFor(String id) {
        return (ArrayList<String>) appointmentsOf(id)
                .stream()
                .filter(a -> a.getDentistID().equals(id))
                .map(a -> {
                    try {
                        return ps.get(a.getPatientID()).getEmail();
                    } catch (RepoException e) {
                        return "";
                    }
                })
                .collect(Collectors.toList());
    }

}
