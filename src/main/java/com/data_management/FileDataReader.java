package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileDataReader implements DataReader {

    private final String directoryPath;

    public FileDataReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void startStreaming(DataStorage dataStorage) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 4) {
                            int patientId = Integer.parseInt(parts[0]);
                            long timestamp = Long.parseLong(parts[1]);
                            String label = parts[2];
                            String data = parts[3].replace("%", "");

                            double value = Double.parseDouble(data);
                            dataStorage.addPatientData(patientId, value, label, timestamp);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Failed to read file: " + path + " - " + e.getMessage());
                }
            });
        }
    }

    @Override
    public void stopStreaming() {
    }
}
