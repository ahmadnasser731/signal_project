package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
/**
 * This class saves patient data into text files.
 * It puts each type of data (like ECG, blood pressure) in its own file.
 */
public class FileOutputStrategy implements OutputStrategy {

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    // Stores which file each label (like "ecg") should go into
    private String baseDirectory;
    /**
     * Sets up the output to save files in a specific folder.
     *
     * @param baseDirectory The folder where all the data files will be saved.
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }
    /**
     * Takes some patient data and writes it into a file.
     * Each label gets its own file (like ecg.txt, alert.txt, etc).
     *
     * @param patientId The ID of the patient sending the data.
     * @param timestamp When the data was recorded (in milliseconds).
     * @param label What kind of data this is (like "ecg", "alert", etc).
     * @param data The actual data value (could be a number, a message, etc).
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String filePath =
                fileMap.computeIfAbsent(label,
                        k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out =
                     new PrintWriter(Files.newBufferedWriter(
                             Paths.get(filePath),
                             StandardOpenOption.CREATE,
                             StandardOpenOption.APPEND))) {
            out.printf(
                    "Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n",
                    patientId, timestamp, label, data);
        } catch (IOException e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
