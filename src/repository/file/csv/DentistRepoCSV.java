package repository.file.csv;

import domain.Dentist;
import repository.file.FileRepository;
import repository.memory.IdentifiableRepoMem;
import utils.Logger;

import java.io.*;
import java.util.Map;

import static java.lang.System.exit;

public class DentistRepoCSV extends FileRepository<String, Dentist> {
    public DentistRepoCSV(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        IdentifiableRepoMem<Dentist> tr = new IdentifiableRepoMem<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = "";
            int counter = 0;
            while ((line = br.readLine()) != null) {
                String[] elems = line.split(",");
                if (elems.length != 4)
                    continue;
                Dentist d = new Dentist(elems[0].strip(), elems[1].strip(), elems[2].strip(), elems[3].strip());
                tr.add(d.getID(), d);
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Read " + counter + " dentists from file " + fileName);
            this.repo = tr.repo;
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
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
            bw = new BufferedWriter(new FileWriter(this.fileName));
            int counter = 0;
            for (Map.Entry<String, Dentist> entry : repo.entrySet()) {
                bw.write(entry.getValue().getID() + " , " + entry.getValue().getName() + " , " +
                        entry.getValue().getEmail() + " , " + entry.getValue().getPhone());
                bw.newLine();
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Wrote " + counter + " dentists to file " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
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

