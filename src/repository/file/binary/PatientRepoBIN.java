package repository.file.binary;

import domain.Patient;
import repository.file.FileRepository;
import repository.memory.IdentifiableRepoMem;
import utils.Logger;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class PatientRepoBIN extends FileRepository<String, Patient> {
    public PatientRepoBIN(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        var tr = new IdentifiableRepoMem<Patient>();
        try (var in = new ObjectInputStream(new FileInputStream(this.fileName))) {
            var pans = (ArrayList<Patient>) in.readObject();
            for (Patient a : pans)
                tr.add(a.getID(), a);
            this.repo = tr.repo;
            Logger.log(this.getClass().getSimpleName(), "Read " + pans.size() + " patients from file " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            exit(1);
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        var pans = new ArrayList<>(this.repo.values());
        try (var out = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            out.writeObject(pans);
            Logger.log(this.getClass().getSimpleName(), "Wrote " + pans.size() + " patients to file " + fileName);
        } catch (IOException e) {
            exit(1);
            e.printStackTrace();
        }
    }

}
