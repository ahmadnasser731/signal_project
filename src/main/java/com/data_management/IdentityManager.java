package com.data_management;

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
        // TODO: Add logging, alerting, or recovery strategies here
    }
}
