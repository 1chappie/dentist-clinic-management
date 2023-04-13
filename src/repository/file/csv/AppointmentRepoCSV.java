package repository.file.csv;

import domain.Appointment;
import repository.file.FileRepository;
import repository.memory.IdentifiableRepoMem;
import utils.Logger;

import java.io.*;
import java.util.Map;

import static java.lang.System.exit;

public class AppointmentRepoCSV extends FileRepository<String, Appointment> {
    public AppointmentRepoCSV(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        IdentifiableRepoMem<Appointment> tr = new IdentifiableRepoMem<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = "";
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] elems = line.split(",");
                if (elems.length != 5)
                    continue;
                Appointment a = Appointment.build(elems[0].strip(), elems[1].strip(), elems[2].strip(), elems[3].strip(), elems[4].strip());
                tr.add(a.getID(), a);
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Read " + counter + " appointments from file " + fileName);
            this.repo = tr.repo;
        } catch (IOException e) {
            exit(1);
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error while closing the file " + e);
                }
        }
    }

    @Override
    public void writeToFile() {
        BufferedWriter bw = null;
        try {
            int counter = 0;
            bw = new BufferedWriter(new FileWriter(this.fileName));
            for (Map.Entry<String, Appointment> entry : repo.entrySet()) {
                bw.write(entry.getValue().getID() + " , " + entry.getValue().getDentistID() + " , " +
                        entry.getValue().getPatientID() + " , " + entry.getValue().getDate().toString() + " , " + entry.getValue().getComments());
                bw.newLine();
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Wrote " + counter + " appointments to file " + fileName);
        } catch (IOException e) {
            exit(1);
            e.printStackTrace();
        } finally {
            try {
                assert bw != null;
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

