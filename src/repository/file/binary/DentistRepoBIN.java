package repository.file.binary;

import domain.Dentist;
import repository.file.FileRepository;
import repository.memory.IdentifiableRepoMem;
import utils.Logger;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class DentistRepoBIN extends FileRepository<String, Dentist> {
    public DentistRepoBIN(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        var tr = new IdentifiableRepoMem<Dentist>();
        try (var in = new ObjectInputStream(new FileInputStream(this.fileName))) {
            var dens = (ArrayList<Dentist>) in.readObject();
            for (Dentist a : dens)
                tr.add(a.getID(), a);
            this.repo = tr.repo;
            Logger.log(this.getClass().getSimpleName(), "Read " + dens.size() + " dentists from file " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            exit(1);
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        var dens = new ArrayList<>(this.repo.values());
        try (var out = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            out.writeObject(dens);
            Logger.log(this.getClass().getSimpleName(), "Wrote " + dens.size() + " dentists to file " + fileName);
        } catch (IOException e) {
            exit(1);
            e.printStackTrace();
        }
    }

}
