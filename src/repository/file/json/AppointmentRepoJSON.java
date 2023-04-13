package repository.file.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Appointment;
import domain.Dentist;
import domain.Identifiable;
import exception.RepoException;
import repository.file.FileRepository;
import repository.memory.IdentifiableRepoMem;
import utils.Logger;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.System.exit;

public class AppointmentRepoJSON extends FileRepository<String, Appointment> {
    public AppointmentRepoJSON(String fileName) {
        super(fileName);
    }


    @Override
    public void readFromFile() {
        var tr = new IdentifiableRepoMem<Appointment>();
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            var ar = mapper.readValue(
                    Paths.get(fileName).toFile(),
                    new TypeReference<ArrayList<Appointment>>() {
                    }
            );
            for (var d : ar) {
                if (!tr.add(d.getID(), d)) {
                    throw new RepoException("Bad JSON");
                }
                ;
            }
            this.repo = tr.repo;
            Logger.log(this.getClass().getName(), "Read from file" + fileName);
        } catch (IOException | RepoException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    @Override
    public void writeToFile() {
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            mapper.writeValue(Paths.get(fileName).toFile(), this.getAll());
            Logger.log(this.getClass().getName(), "Wrote to file" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }

    }
}
