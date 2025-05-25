package com.data_components;

import com.data_management.PatientData;

public class DataParser {

    public PatientData parse(String input) {
        try {
            String[] parts = input.split(",");
            int id = Integer.parseInt(parts[0].trim());
            String type = parts[1].trim();
            double value = Double.parseDouble(parts[2].trim());
            long time = Long.parseLong(parts[3].trim());

            return new PatientData(id, type, value, time);

        } catch (Exception e) {
            System.err.println("Failed to parse input: " + input);
            return null;
        }
    }
}
