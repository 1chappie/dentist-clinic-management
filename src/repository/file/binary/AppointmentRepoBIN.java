package repository.file.binary;

import domain.Appointment;
import repository.file.FileRepository;
import repository.memory.IdentifiableRepoMem;
import utils.Logger;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class AppointmentRepoBIN extends FileRepository<String, Appointment> {
    public AppointmentRepoBIN(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        var tr = new IdentifiableRepoMem<Appointment>();
        try (var in = new ObjectInputStream(new FileInputStream(this.fileName))) {
            var apps = (ArrayList<Appointment>) in.readObject();
            for (Appointment a : apps)
                tr.add(a.getID(), a);
            this.repo = tr.repo;
            Logger.log(this.getClass().getSimpleName(), "Read " + apps.size() + " appointments from file " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            exit(1);
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        var apps = new ArrayList<>(this.repo.values());
        try (var out = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            out.writeObject(apps);
            Logger.log(this.getClass().getSimpleName(), "Wrote " + apps.size() + " appointments to file " + fileName);
        } catch (IOException e) {
            exit(1);
            e.printStackTrace();
        }
    }

}
