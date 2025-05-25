package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureStrategy implements AlertStrategy {

    @Override
    public List<Alert> checkAlert(Patient patient) {
        List<Alert> alerts = new ArrayList<>();
        List<PatientRecord> records = patient.getRecords(System.currentTimeMillis() - 600000, System.currentTimeMillis());

        double prev = -1;
        int trend = 0;

        for (PatientRecord record : records) {
            if (!record.getRecordType().toLowerCase().contains("bloodpressure")) continue;

            double val = record.getMeasurementValue();

            // Critical thresholds
            if (val > 180 || val < 90) {
                alerts.add(new BloodPressureAlert(patient.getId() + "", "Critical BP: " + val, record.getTimestamp()));
            }

            // Trend check
            if (prev != -1) {
                if (Math.abs(val - prev) > 10) {
                    trend = (val > prev) ? trend + 1 : trend - 1;
                } else {
                    trend = 0;
                }

                if (Math.abs(trend) >= 2) {
                    alerts.add(new BloodPressureAlert(patient.getId() + "", "Trend BP Alert", record.getTimestamp()));
                    trend = 0;
                }
            }
            prev = val;
        }

        return alerts;
    }
}
