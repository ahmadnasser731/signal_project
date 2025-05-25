package com.data_management;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IdentityManager {

    private final PatientIdentifier patientIdentifier;

    public IdentityManager(PatientIdentifier patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public HospitalPatient verifyPatient(int patientId) {
        if (patientIdentifier.exists(patientId)) {
            return patientIdentifier.identify(patientId);
        } else {
            handleMissingPatient(patientId);
            return null;
        }
    }

    private void handleMissingPatient(int patientId) {
        System.err.println("!!Warning!!: Patient ID " + patientId + " is not in the hospital records.");

        try (FileWriter fw = new FileWriter("missing_patients.log", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("Missing patient ID: " + patientId + " at " + System.currentTimeMillis());
        } catch (IOException e) {
            System.err.println("Failed to log missing patient ID: " + patientId);
        }
    }

}
