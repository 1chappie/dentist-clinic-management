package views.fx;

import domain.Appointment;
import domain.Dentist;
import domain.Patient;
import exception.InvalidParametersException;
import exception.RepoException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import repository.RepositoryLoader;
import service.*;

import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;


public class DashboardGUI implements Initializable {

    @FXML
    private ListView<String> patientList;
    @FXML
    private Button addPatientButton;
    @FXML
    private Button deletePatientButton;
    @FXML
    private Button updatePatientButton;
    @FXML
    private TextField idPatient;
    @FXML
    private TextField namePatient;
    @FXML
    private TextField mailPatient;
    @FXML
    private TextField phonePatient;
    @FXML
    private ListView<String> dentistList;
    @FXML
    private Button addDentistButton;
    @FXML
    private Button deleteDentistButton;
    @FXML
    private Button updateDentistButton;
    @FXML
    private TextField idDentist;
    @FXML
    private TextField nameDentist;
    @FXML
    private TextField mailDentist;
    @FXML
    private TextField phoneDentist;
    @FXML
    private ListView<String> appointmentList;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;
    @FXML
    private Button updateAppointmentButton;
    @FXML
    private TextField idAppointment;
    @FXML
    private TextField patientidAppointment;
    @FXML
    private TextField dentistidAppointment;
    @FXML
    private TextField dateAppointment;
    @FXML
    private TextField commentsAppointment;
    @FXML
    private ListView<String> reportList;
    @FXML
    private TextField report1Field;
    @FXML
    private TextField report2Field;
    @FXML
    private TextField report3Field;
    @FXML
    private TextField report4Field;
    @FXML
    private TextField report5Field;
    @FXML
    private Button report1Button;
    @FXML
    private Button report2Button;
    @FXML
    private Button report3Button;
    @FXML
    private Button report4Button;
    @FXML
    private Button report5Button;

    private Controller controller;

    private ReportService reportService;

    private void reloadLists() {
        patientList.getItems().clear();
        for (var patient : controller.getPatients())
            patientList.getItems().add(patient.toString());

        dentistList.getItems().clear();
        for (var dentist : controller.getDentists())
            dentistList.getItems().add(dentist.toString());

        appointmentList.getItems().clear();
        for (var appointment : controller.getAppointments())
            appointmentList.getItems().add(appointment.toString());

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        var rl = new RepositoryLoader();
        var patientService = new PatientService(rl.getPatientRepo());
        var dentistService = new DentistService(rl.getDentistRepo());
        var appointmentService = new AppointmentService(rl.getAppointmentRepo());
        controller = new Controller(appointmentService, dentistService, patientService);
        reportService = new ReportService(appointmentService, dentistService, patientService);

        reloadLists();
    }

    public void onAddDentist() {
        try {
            var id = idDentist.getText();
            var name = nameDentist.getText();
            var mail = mailDentist.getText();
            var phone = phoneDentist.getText();
            controller.addDentists(new Dentist(id, name, mail, phone));
            reloadLists();
        } catch (RepoException | InvalidParametersException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onDeleteDentist() {
        try {
            var id = idDentist.getText();
            controller.removeDentists(new Dentist(id, null, null, null));
            reloadLists();
        } catch (RepoException | InvalidParametersException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onUpdateDentist() {
        try {
            var id = idDentist.getText();
            var name = nameDentist.getText();
            var mail = mailDentist.getText();
            var phone = phoneDentist.getText();
            controller.updateDentist(new Dentist(id, name, mail, phone));
            reloadLists();
        } catch (RepoException | InvalidParametersException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onAddPatient() {
        try {
            var id = idPatient.getText();
            var name = namePatient.getText();
            var mail = mailPatient.getText();
            var phone = phonePatient.getText();
            controller.addPatients(new Patient(id, name, mail, phone));
            reloadLists();
        } catch (RepoException | InvalidParametersException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onDeletePatient() {
        try {
            var id = idPatient.getText();
            controller.removePatients(new Patient(id, null, null, null));
            reloadLists();
        } catch (RepoException | InvalidParametersException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onUpdatePatient() {
        try {
            var id = idPatient.getText();
            var name = namePatient.getText();
            var mail = mailPatient.getText();
            var phone = phonePatient.getText();
            controller.updatePatient(new Patient(id, name, mail, phone));
            reloadLists();
        } catch (RepoException | InvalidParametersException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onAddAppointment() {
        try {
            var id = idAppointment.getText();
            var patientid = patientidAppointment.getText();
            var dentistid = dentistidAppointment.getText();
            var date = dateAppointment.getText();
            var comments = commentsAppointment.getText();
            controller.bookAppointment(
                    new Dentist(dentistid, null, null, null),
                    new Patient(patientid, null, null, null),
                    LocalDate.parse(date),
                    comments);
            reloadLists();
        } catch (RepoException | InvalidParametersException | DateTimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onDeleteAppointment() {
        try {
            var id = idAppointment.getText();
            controller.cancelAppointment(Appointment.build(id, null, null, null, null));
            reloadLists();
        } catch (RepoException | InvalidParametersException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onUpdateAppointment() {
        try {
            var id = idAppointment.getText();
            var patientid = patientidAppointment.getText();
            var dentistid = dentistidAppointment.getText();
            var date = dateAppointment.getText();
            var comments = commentsAppointment.getText();
            controller.updateAppointment(Appointment.build(id, dentistid, patientid, date, comments));
            reloadLists();
        } catch (RepoException | InvalidParametersException | DateTimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void report1() {
        var date = report1Field.getText();
        if (date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Date is empty");
            alert.showAndWait();
            return;
        }
        var report = reportService.appointmentsOn(LocalDate.parse(date));
        reportList.getItems().clear();
        for (var r : report) {
            reportList.getItems().add(r.toString());
        }
    }

    public void report2() {
        var id = report2Field.getText();
        if (id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID is empty");
            alert.showAndWait();
            return;
        }
        var report = reportService.appointmentsOf(id);
        reportList.getItems().clear();
        for (var r : report) {
            reportList.getItems().add(r.toString());
        }
    }

    public void report3() {
        var id = report3Field.getText();
        if (id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID is empty");
            alert.showAndWait();
            return;
        }
        var report = reportService.commentsOf(id);
        reportList.getItems().clear();
        for (var r : report) {
            reportList.getItems().add(r.toString());
        }
    }

    public void report4() {
        var id = report4Field.getText();
        if (id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID is empty");
            alert.showAndWait();
            return;
        }
        var report = reportService.phoneOf(id);
        reportList.getItems().clear();
        reportList.getItems().add(report);
    }

    public void report5() {
        var id = report5Field.getText();
        if (id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID is empty");
            alert.showAndWait();
            return;
        }
        var report = reportService.patientEmailsFor(id);
        reportList.getItems().clear();
        for (var r : report) {
            reportList.getItems().add(r.toString());
        }
    }

}
