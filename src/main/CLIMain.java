package main;

import repository.RepositoryLoader;
import service.*;
import views.CLI;

public class CLIMain {

    public static void main(String[] args) {

        var rl = new RepositoryLoader();

        var patientService = new PatientService(rl.getPatientRepo());
        var dentistService = new DentistService(rl.getDentistRepo());
        var appointmentService = new AppointmentService(rl.getAppointmentRepo());

        var controller = new Controller(appointmentService, dentistService, patientService);

        var cli = new CLI(controller);
        cli.run();

    }
}