package com.data_readers;

import com.data_management.PatientData;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileDataListener implements DataListener {

    private final String filePath;
    private final DataSourceAdapter adapter;
    private final DataParser parser;

    public FileDataListener(String filePath, DataSourceAdapter adapter, DataParser parser) {
        this.filePath = filePath;
        this.adapter = adapter;
        this.parser = parser;
    }

    @Override
    public void startListening() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                PatientData data = parser.parse(line);
                adapter.handleData(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
