package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class HeartRateStrategy implements AlertStrategy {

    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> alerts = new ArrayList<>();
        List<PatientRecord> records = patient.getRecords(System.currentTimeMillis() - 600000, System.currentTimeMillis());

        for (PatientRecord record : records) {
            if (record.getRecordType().toLowerCase().contains("heartrate")) {
                if (record.getMeasurementValue() > 130) {
                    alerts.add(new ECGAlert(patient.getId() + "", "High heart rate: " + record.getMeasurementValue(), record.getTimestamp()));
                }
            }
        }

        return alerts;
    }
}
