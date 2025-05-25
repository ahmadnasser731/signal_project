package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class OxygenSaturationStrategy implements AlertStrategy {

    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> alerts = new ArrayList<>();
        List<PatientRecord> records = patient.getRecords(System.currentTimeMillis() - 600000, System.currentTimeMillis());

        double prev = -1;

        for (PatientRecord record : records) {
            if (!record.getRecordType().toLowerCase().contains("saturation")) continue;

            double val = record.getMeasurementValue();

            if (val < 92) {
                alerts.add(new BloodOxygenAlert(patient.getId() + "", "Low O2: " + val, record.getTimestamp()));
            }

            if (prev != -1 && (prev - val) >= 5) {
                alerts.add(new BloodOxygenAlert(patient.getId() + "", "Rapid O2 drop", record.getTimestamp()));
            }

            prev = val;
        }

        return alerts;
    }
}
