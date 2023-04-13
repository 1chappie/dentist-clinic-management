package views;

import domain.Appointment;
import domain.Dentist;
import domain.Patient;
import service.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    Controller controller;
    Scanner console;

    public CLI(Controller controller) {
        this.controller = controller;
        this.console = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\n> Dashboard <");
            System.out.println("Available dentists: " + controller.getNumberOfDentists());
            System.out.println("Total appointments: " + controller.getNumberOfAppointments());
            System.out.println("[a] Manage appointments");
            System.out.println("[s] Manage patients");
            System.out.println("[d] Manage dentists");
            System.out.println("[r] Reports");
            System.out.println("[q] Exit");
            System.out.print("Enter option: ");
            String option = this.console.nextLine();
            switch (option.charAt(0)) {
                case 's' -> new PatientsCLI().run();
                case 'a' -> new AppointmentsCLI().run();
                case 'd' -> new DentistsCLI().run();
                case 'r' -> new ReportsCLI().run();
                case 'q' -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    class PatientsCLI {
        public void run() {
            while (true) {
                System.out.println("\n>> Manage patients <<");
                System.out.println("Total patients: " + CLI.this.controller.getNumberOfPatients());
                System.out.println("[c] Create patient entry");
                System.out.println("[r] Read patient entries");
                System.out.println("[u] Update patient entry");
                System.out.println("[d] Delete patient entry");
                System.out.println("[q] Back");
                System.out.print("Enter option: ");
                char option = CLI.this.console.nextLine().charAt(0);
                switch (option) {
                    case 'c' -> {
                        System.out.println("\n>>> Creating patient entry <<<");
                        System.out.print("Enter patient ID: ");
                        String id = CLI.this.console.nextLine();
                        System.out.print("Enter patient name: ");
                        String name = CLI.this.console.nextLine();
                        System.out.print("Enter patient email: ");
                        String email = CLI.this.console.nextLine();
                        System.out.print("Enter patient phone number: ");
                        String phone = CLI.this.console.nextLine();
                        try {
                            CLI.this.controller.addPatients(new Patient(id, name, email, phone));
                            System.out.println("Patient entry created!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'r' -> {
                        System.out.println("\n>>> All patient entries <<<");
                        for (Patient p : CLI.this.controller.getPatients()) {
                            System.out.print(p);
                            ArrayList<Appointment> apps = CLI.this.controller.appointmentsOf(p);
                            for (Appointment app : apps)
                                System.out.print("  \n$ " + app);
                            System.out.println();
                        }
                    }
                    case 'u' -> {
                        System.out.println("\n>>> Updating patient entry <<<");
                        System.out.print("Enter patient ID: ");
                        String id = CLI.this.console.nextLine();
                        System.out.print("Enter patient name: ");
                        String name = CLI.this.console.nextLine();
                        System.out.print("Enter patient email: ");
                        String email = CLI.this.console.nextLine();
                        System.out.print("Enter patient phone number: ");
                        String phone = CLI.this.console.nextLine();
                        try {
                            CLI.this.controller.updatePatient(new Patient(id, name, email, phone));
                            System.out.println("Patient entry updated!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'd' -> {
                        System.out.println("\n>>> Deleting patient entry <<<");
                        System.out.print("Enter patient ID: ");
                        String id = CLI.this.console.nextLine();
                        try {
                            CLI.this.controller.removePatients(new Patient(id, null, null, null));
                            System.out.println("Patient entry deleted!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'q' -> {
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }
            }
        }
    }

    class AppointmentsCLI {
        public void run() {
            while (true) {
                System.out.println("\n>> Manage appointments <<");
                System.out.println("Total appointments: " + CLI.this.controller.getNumberOfAppointments());
                System.out.println("[c] Create appointment");
                System.out.println("[r] Read appointments");
                System.out.println("[u] Update appointmen time");
                System.out.println("[d] Cancel appointment");
                System.out.println("[q] Back");
                System.out.print("Enter option: ");
                char option = CLI.this.console.nextLine().charAt(0);
                switch (option) {
                    case 'c' -> new MakeAppointmentCLI().run();
                    case 'r' -> {
                        System.out.println("\n>>> Stored appointments <<<");
                        for (Appointment a : CLI.this.controller.getAppointments())
                            System.out.println(a);
                    }
                    case 'u' -> {
                        System.out.println("\n>>> Update appointment time <<<");
                        for (int i = 0; i < CLI.this.controller.getNumberOfAppointments(); i++)
                            System.out.println("[" + (i + 1) + "] " + CLI.this.controller.getAppointments().get(i));
                        System.out.print("Choose the appointment (index) ");
                        int index;
                        try {
                            index = Integer.parseInt(CLI.this.console.nextLine());
                            if (index < 1 || index > CLI.this.controller.getNumberOfAppointments())
                                throw new Exception("Invalid index");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            return;
                        }
                        System.out.print("Enter new year: ");
                        int year;
                        try {
                            year = Integer.parseInt(CLI.this.console.nextLine());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            return;
                        }
                        System.out.print("Enter new month: ");
                        int month;
                        try {
                            month = Integer.parseInt(CLI.this.console.nextLine());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            return;
                        }
                        System.out.print("Enter new day: ");
                        int day;
                        try {
                            day = Integer.parseInt(CLI.this.console.nextLine());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            return;
                        }
                        Appointment a = CLI.this.controller.getAppointments().get(index - 1);
                        a.setDate(LocalDate.of(year, month, day));
                        try {
                            CLI.this.controller.updateAppointment(a);
                            System.out.println("Appointment updated!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'd' -> {
                        System.out.println("\n>>> Delete appointment <<<");
                        for (int i = 0; i < CLI.this.controller.getNumberOfAppointments(); i++)
                            System.out.println("[" + (i + 1) + "] " + CLI.this.controller.getAppointments().get(i));
                        System.out.print("Choose the appointment (index) ");
                        int index;
                        try {
                            index = Integer.parseInt(CLI.this.console.nextLine());
                            if (index < 1 || index > CLI.this.controller.getNumberOfAppointments()) {
                                throw new Exception("Invalid index");
                            }
                            CLI.this.controller.cancelAppointment(CLI.this.controller.getAppointments().get(index - 1));
                            System.out.println("Appointment deleted!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            return;
                        }
                    }
                    case 'q' -> {
                        return;
                    }
                }
            }
        }
    }

    class MakeAppointmentCLI {
        public void run() {
            for (int i = 0; i < CLI.this.controller.getNumberOfPatients(); i++)
                System.out.println("[" + (i + 1) + "] " + CLI.this.controller.getPatients().get(i).getName());
            System.out.print("Who is the patient? (index) ");
            int patientIndex;
            try {
                patientIndex = Integer.parseInt(CLI.this.console.nextLine());
                if (patientIndex < 1 || patientIndex > CLI.this.controller.getNumberOfPatients())
                    throw new Exception("Invalid patient index");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
            Patient patient = CLI.this.controller.getPatients().get(patientIndex - 1);
            System.out.println("\nAvailable dentists: " + CLI.this.controller.getNumberOfDentists());
            for (int i = 0; i < CLI.this.controller.getNumberOfDentists(); i++)
                System.out.println("[" + (i + 1) + "] " + CLI.this.controller.getDentists().get(i).getName());
            System.out.print("Who is the dentist? (index) ");
            int dentistIndex;
            try {
                dentistIndex = Integer.parseInt(CLI.this.console.nextLine());
                if (dentistIndex < 1 || dentistIndex > CLI.this.controller.getNumberOfDentists())
                    throw new Exception("Invalid dentist index");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
            Dentist dentist = CLI.this.controller.getDentists().get(dentistIndex - 1);
            int year, month, day;
            System.out.print("\nAppointment year: ");
            try {
                year = Integer.parseInt(CLI.this.console.nextLine());
                if (year < 0)
                    throw new Exception("Invalid year");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
            System.out.print("Appointment month: ");
            try {
                month = Integer.parseInt(CLI.this.console.nextLine());
                if (month < 1 || month > 12)
                    throw new Exception("Invalid month");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
            System.out.print("Appointment day: ");
            try {
                day = Integer.parseInt(CLI.this.console.nextLine());
                if (day < 1 || day > 31)
                    throw new Exception("Invalid day");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
            System.out.print("Addtional comments: ");
            String comments = CLI.this.console.nextLine();
            try {
                CLI.this.controller.bookAppointment(dentist, patient, LocalDate.of(year, month, day), comments);
                System.out.println("\nAppointment made!");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    class ReportsCLI {
        public void run() {
            System.out.println("\n>> Generate reports <<");
            System.out.println("Total appointments: " + CLI.this.controller.getNumberOfAppointments());
            System.out.println("Total appointments per dentist:");
            for (Dentist dentist : CLI.this.controller.getDentists())
                System.out.println("Dr. " + dentist.getName() + ": " + CLI.this.controller.appointmentsOf(dentist.getID()).size());
            while (true) {
                System.out.println("[1] Appointments on date");
                System.out.println("[2] Appointments of entity");
                System.out.println("[3] Comments on patient");
                System.out.println("[4] Phone of patient");
                System.out.println("[5] All patient emails of a given dentist");
                System.out.println("[q] Back");
                System.out.print("Enter option: ");
                char option = CLI.this.console.nextLine().charAt(0);
                switch (option) {
                    case '1' -> {
                        System.out.print("Enter date: ");
                        String dateString = CLI.this.console.nextLine();
                        try {
                            LocalDate date = LocalDate.parse(dateString);
                            System.out.println("Appointments on " + date);
                            for (Appointment appointment : CLI.this.controller.appointmentsOn(date))
                                System.out.println(appointment);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case '2' -> {
                        System.out.print("Enter entity ID: ");
                        String id = CLI.this.console.nextLine();
                        try {
                            System.out.println("Appointments of entity " + id);
                            for (Appointment appointment : CLI.this.controller.appointmentsOf(id))
                                System.out.println(appointment);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case '3' -> {
                        System.out.print("Enter patient ID: ");
                        String id = CLI.this.console.nextLine();
                        try {
                            System.out.println("Comments on patient " + id);
                            for (String comment : CLI.this.controller.commentsOf(id))
                                System.out.println(comment);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case '4' -> {
                        System.out.print("Enter patient ID: ");
                        String id = CLI.this.console.nextLine();
                        try {
                            System.out.println("Phone of patient " + id + ": " + CLI.this.controller.phoneOf(id));
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case '5' -> {
                        System.out.print("Enter dentist ID: ");
                        String id = CLI.this.console.nextLine();
                        try {
                            System.out.println("All patient emails of dentist " + id);
                            for (String email : CLI.this.controller.patientEmailsFor(id))
                                System.out.println(email);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'q' -> {
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }
            }
        }
    }

    class DentistsCLI {
        public void run() {
            while (true) {
                System.out.println("\n>> Manage dentists <<");
                System.out.println("Total patients: " + CLI.this.controller.getNumberOfAppointments());
                System.out.println("[c] Create dentist entry");
                System.out.println("[r] Read dentist entries");
                System.out.println("[u] Update dentist entry");
                System.out.println("[d] Delete dentist entry");
                System.out.println("[q] Back");
                System.out.print("Enter option: ");
                char option = CLI.this.console.nextLine().charAt(0);
                switch (option) {
                    case 'c' -> {
                        System.out.println("\n>>> Creating dentist entry <<<");
                        System.out.print("Enter dentist ID: ");
                        String id = CLI.this.console.nextLine();
                        System.out.print("Enter dentist name: ");
                        String name = CLI.this.console.nextLine();
                        System.out.print("Enter dentist email: ");
                        String email = CLI.this.console.nextLine();
                        System.out.print("Enter dentist phone number: ");
                        String phone = CLI.this.console.nextLine();
                        try {
                            CLI.this.controller.addDentists(new Dentist(id, name, email, phone));
                            System.out.println("Dentist entry created!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'r' -> {
                        System.out.println("\n>>> All dentist entries <<<");
                        for (Dentist d : CLI.this.controller.getDentists()) {
                            System.out.print(d);
                            ArrayList<Appointment> apps = CLI.this.controller.appointmentsOf(d.toString());
                            for (Appointment app : apps)
                                System.out.print("  \n$ " + app);
                            System.out.println();
                        }
                    }
                    case 'u' -> {
                        System.out.println("\n>>> Updating dentist entry <<<");
                        System.out.print("Enter dentist ID: ");
                        String id = CLI.this.console.nextLine();
                        System.out.print("Enter dentist name: ");
                        String name = CLI.this.console.nextLine();
                        System.out.print("Enter dentist email: ");
                        String email = CLI.this.console.nextLine();
                        System.out.print("Enter dentist phone number: ");
                        String phone = CLI.this.console.nextLine();
                        try {

                            CLI.this.controller.updateDentist(new Dentist(id, name, email, phone));
                            System.out.println("Dentist entry updated!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'd' -> {
                        System.out.println("\n>>> Deleting dentist entry <<<");
                        System.out.print("Enter dentist ID: ");
                        String id = CLI.this.console.nextLine();
                        try {
                            CLI.this.controller.removeDentists(new Dentist(id, null, null, null));
                            System.out.println("Dentist entry deleted!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 'q' -> {
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }
            }
        }
    }
}
