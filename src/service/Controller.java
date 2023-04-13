package service;

import domain.Appointment;
import domain.Dentist;
import domain.Identifiable;
import domain.Patient;
import exception.InvalidParametersException;
import exception.RepoException;
import validators.EntityValidator;

import java.time.LocalDate;
import java.util.ArrayList;

final public class Controller {

    public final AppointmentService appService;
    public final DentistService dentistService;
    public final PatientService patientService;
    public final ReportService reportService;

    public Controller(AppointmentService appointmentService, DentistService dentistService, PatientService patientService) {
        this.appService = appointmentService;
        this.dentistService = dentistService;
        this.patientService = patientService;
        this.reportService = new ReportService(this.appService, this.dentistService, this.patientService);
    }

    public Appointment bookAppointment(Dentist dentist, Patient patient, LocalDate date, String comments) throws RepoException, InvalidParametersException {
        if (!dentistService.contains(dentist.getID()) || !patientService.contains(patient.getID()))
            throw new RepoException("Dentist or patient not found");
        Appointment appointment = new Appointment(dentist, patient, date, comments);
        EntityValidator.check(appointment);
        appService.add(appointment);
        return appointment;
    }

    public void cancelAppointment(Appointment appointment) throws RepoException {
        appService.remove(appointment);
    }

    public void addDentists(Dentist... dentists) throws RepoException, InvalidParametersException {
        for (Dentist dentist : dentists) {
            EntityValidator.check(dentist);
            dentistService.add(dentist);
        }
    }

    public void addPatients(Patient... patients) throws RepoException, InvalidParametersException {
        for (Patient patient : patients) {
            EntityValidator.check(patient);
            patientService.add(patient);
        }
    }

    public void removeDentists(Dentist... dentists) throws RepoException {
        for (Dentist d : dentists) {
            dentistService.remove(d);
            for (Appointment a : reportService.appointmentsOf(d))
                appService.remove(a);
        }
    }

    public void removePatients(Patient... patients) throws RepoException {
        for (Patient p : patients) {
            patientService.remove(p);
            for (Appointment a : reportService.appointmentsOf(p))
                appService.remove(a);
        }
    }

    public void updateDentist(Dentist dentist) throws RepoException, InvalidParametersException {
        EntityValidator.check(dentist);
        dentistService.update(dentist);
    }

    public void updatePatient(Patient patient) throws RepoException, InvalidParametersException {
        EntityValidator.check(patient);
        patientService.update(patient);
    }

    public void updateAppointment(Appointment appointment) throws RepoException, InvalidParametersException {
        EntityValidator.check(appointment);
        appService.update(appointment);
    }

    public Dentist getDentist(String id) throws RepoException {
        return dentistService.get(id);
    }

    public ArrayList<Dentist> getDentists() {
        return dentistService.get();
    }

    public ArrayList<Patient> getPatients() {
        return patientService.get();
    }

    public ArrayList<Appointment> getAppointments() {
        return appService.get();
    }

    public Patient getPatient(String id) throws RepoException {
        return patientService.get(id);
    }

    public Appointment getAppointment(String id) throws RepoException {
        return appService.get(id);
    }

    public boolean exists(String id) {
        return appService.contains(id) || dentistService.contains(id) || patientService.contains(id);
    }

    public Integer getNumberOfAppointments() {
        return appService.size();
    }

    public Integer getNumberOfDentists() {
        return dentistService.size();
    }

    public Integer getNumberOfPatients() {
        return patientService.size();
    }

    public ArrayList<Appointment> appointmentsOn(LocalDate date) {
        return reportService.appointmentsOn(date);
    }

    public ArrayList<Appointment> appointmentsOf(Patient patient) {
        return reportService.appointmentsOf(patient);
    }

    public ArrayList<Appointment> appointmentsOf(String id) {
        return reportService.appointmentsOf(id);
    }

    public ArrayList<String> commentsOf(String id) {
        return reportService.commentsOf(id);
    }

    public ArrayList<String> commentsOf(Patient patient) {
        return reportService.commentsOf(patient);
    }

    public String phoneOf(String id) {
        return reportService.phoneOf(id);
    }

    public String phoneOf(Identifiable<String> patient) {
        return reportService.phoneOf(patient);
    }

    public ArrayList<String> patientEmailsFor(String id) {
        return reportService.patientEmailsFor(id);
    }

}
