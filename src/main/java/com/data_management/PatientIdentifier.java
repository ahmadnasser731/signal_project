package com.data_management;

import java.util.HashMap;
import java.util.Map;

public class PatientIdentifier {

    private final Map<Integer, HospitalPatient> hospitalRecords = new HashMap<>();

    public void registerPatient(HospitalPatient patient) {
        hospitalRecords.put(patient.getPatientId(), patient);
    }

    public HospitalPatient identify(int id) {
        return hospitalRecords.get(id);
    }

    public boolean exists(int id) {
        return hospitalRecords.containsKey(id);
    }
}
