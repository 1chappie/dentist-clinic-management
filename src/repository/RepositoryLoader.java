package repository;

import domain.Appointment;
import domain.Dentist;
import domain.Patient;
import exception.ConfigurationException;
import repository.db.AppointmentRepoDB;
import repository.db.DentistRepoDB;
import repository.db.PatientRepoDB;
import repository.file.binary.AppointmentRepoBIN;
import repository.file.binary.DentistRepoBIN;
import repository.file.binary.PatientRepoBIN;
import repository.file.csv.AppointmentRepoCSV;
import repository.file.csv.DentistRepoCSV;
import repository.file.csv.PatientRepoCSV;
import repository.file.json.AppointmentRepoJSON;
import repository.file.json.DentistRepoJSON;
import repository.file.json.PatientRepoJSON;
import repository.memory.IdentifiableRepoMem;
import utils.Logger;
import validators.PathValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.exit;

final public class RepositoryLoader {
    private static final String SETTINGS_FILE = "config/settings.properties";
    private static final String PATHS_FILE = "config/paths.properties";
    private String repoMode;
    private String dentistsPath;
    private String patientsPath;
    private String appointmentsPath;

    private IRepository<String, Patient> patientRepo;

    private IRepository<String, Dentist> dentistRepo;

    private IRepository<String, Appointment> appointmentRepo;

    public RepositoryLoader() {
        loadProperties();
        instantiateRepositories();
    }

    public void loadProperties() {
        Properties properties = new Properties();
        try (InputStream pf = new FileInputStream(SETTINGS_FILE)) {
            properties.load(pf);
            repoMode = properties.getProperty("RepositoryMode");
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
        Logger.log(this.getClass().getSimpleName(), "Repository mode property: " + repoMode);
        try (InputStream pf = new FileInputStream(PATHS_FILE)) {
            properties.load(pf);
            switch (repoMode) {
                case "memory" -> {
                    dentistsPath = null;
                    patientsPath = null;
                    appointmentsPath = null;
                }
                case "csv" -> {
                    dentistsPath = properties.getProperty("DentistsCSV");
                    patientsPath = properties.getProperty("PatientsCSV");
                    appointmentsPath = properties.getProperty("AppointmentsCSV");
                }
                case "binary" -> {
                    dentistsPath = properties.getProperty("DentistsBIN");
                    patientsPath = properties.getProperty("PatientsBIN");
                    appointmentsPath = properties.getProperty("AppointmentsBIN");
                }
                case "database" -> {
                    dentistsPath = properties.getProperty("DB");
                    patientsPath = properties.getProperty("DB");
                    appointmentsPath = properties.getProperty("DB");
                }
                case "json" ->{
                    dentistsPath = properties.getProperty("DentistsJSON");
                    patientsPath = properties.getProperty("PatientsJSON");
                    appointmentsPath = properties.getProperty("AppointmentsJSON");
                }
                default -> throw new ConfigurationException("Invalid repository mode");
            }
            Logger.log(this.getClass().getSimpleName(), "Paths: " + dentistsPath + ", " + patientsPath + ", " + appointmentsPath);

        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void instantiateRepositories() {
        try {
            switch (repoMode) {
                case "memory" -> {
                    patientRepo = new IdentifiableRepoMem<>();
                    dentistRepo = new IdentifiableRepoMem<>();
                    appointmentRepo = new IdentifiableRepoMem<>();
                }
                case "csv" -> {
                    if (!PathValidator.checkCSV(dentistsPath, patientsPath, appointmentsPath))
                        throw new ConfigurationException("Invalid CSV paths");
                    patientRepo = new PatientRepoCSV(patientsPath);
                    dentistRepo = new DentistRepoCSV(dentistsPath);
                    appointmentRepo = new AppointmentRepoCSV(appointmentsPath);
                }
                case "binary" -> {
                    if (!PathValidator.checkBIN(dentistsPath, patientsPath, appointmentsPath))
                        throw new ConfigurationException("Invalid BIN paths");
                    patientRepo = new PatientRepoBIN(patientsPath);
                    dentistRepo = new DentistRepoBIN(dentistsPath);
                    appointmentRepo = new AppointmentRepoBIN(appointmentsPath);
                }
                case "database" -> {
                    if (!PathValidator.checkURL(dentistsPath, patientsPath, appointmentsPath))
                        throw new ConfigurationException("Invalid DB paths");
                    patientRepo = new PatientRepoDB(patientsPath);
                    dentistRepo = new DentistRepoDB(dentistsPath);
                    appointmentRepo = new AppointmentRepoDB(appointmentsPath);
                }
                case "json" -> {
                    if (!PathValidator.checkJSON(dentistsPath, patientsPath, appointmentsPath))
                        throw new ConfigurationException("Invalid JSON paths");
                    patientRepo = new PatientRepoJSON(patientsPath);
                    dentistRepo = new DentistRepoJSON(dentistsPath);
                    appointmentRepo = new AppointmentRepoJSON(appointmentsPath);
                }
            }
            Logger.log(this.getClass().getSimpleName(), "Repositories instantiated");
        } catch (ConfigurationException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public IRepository<String, Patient> getPatientRepo() {
        return patientRepo;
    }

    public IRepository<String, Dentist> getDentistRepo() {
        return dentistRepo;
    }

    public IRepository<String, Appointment> getAppointmentRepo() {
        return appointmentRepo;
    }
}
