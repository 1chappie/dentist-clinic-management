package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Appointment implements Identifiable<String>, Serializable {

    public static final long serialVersionUID = 1L;
    public String dentistID;
    public String patientID;

    public String id;
    public LocalDate date;
    public String comments;

    public Appointment(Dentist dentist, Patient patient, LocalDate date, String comments) {
        this.id = dentist.getID() + patient.getID() + date.getMonthValue() + date.getDayOfMonth();
        this.dentistID = dentist.getID();
        this.patientID = patient.getID();
        this.date = date;
        this.comments = comments;
    }

    public Appointment() {
        this.id = null;
        this.dentistID = null;
        this.patientID = null;
        this.date = null;
        this.comments = null;
    }

    public static Appointment build(String id, String dentistID, String patientID, String date, String comments) {
        Appointment a = new Appointment();
        a.id = id;
        a.dentistID = dentistID;
        a.patientID = patientID;
        a.date = LocalDate.parse(date);
        a.comments = comments;
        return a;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public String getDentistID() {
        return dentistID;
    }

    public String getPatientID() {
        return patientID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "[" + this.getID() + "] " + this.dentistID + " " + this.patientID + " " + this.date + " " + this.comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment app = (Appointment) o;
        return Objects.equals(id, app.id);
    }
}
