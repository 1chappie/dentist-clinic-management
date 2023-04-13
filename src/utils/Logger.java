package utils;

import domain.Patient;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

public class Logger {
    // Very simple logger, just writes to a file
    // Thought it'd be easier than using java.util.logging or a library for the scope of this

    static String PATH;

    static { // Static initializer, runs once before main

        // Get the path from the properties file
        try (InputStream pf = new FileInputStream("config/paths.properties")) {
            var properties = new Properties();
            properties.load(pf);
            PATH = properties.getProperty("LOG");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the file if it doesn't exist
        try {
            File file = new File(PATH);
            if (!file.createNewFile()) {
                // Clear the file if it already exists
                var writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Error creating log file");
        }
    }

    // Will be making use of reflection for the location
    public static void log(String location, String message) {
        try {
            var bw = new BufferedWriter(new FileWriter(PATH, true));
            bw.write("[" + LocalDateTime.now() + "] \n" + location + ": " + message);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
