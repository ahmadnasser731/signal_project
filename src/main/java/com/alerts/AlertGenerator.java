package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        long now = System.currentTimeMillis();
        long tenMinutesAgo = now - (10 * 60 * 1000);

        List<PatientRecord> records = patient.getRecords(tenMinutesAgo, now);

        for (PatientRecord record : records) {
            String type = record.getRecordType().toLowerCase();
            double value = record.getMeasurementValue();

            if (type.contains("heartrate") && value > 130) {
                triggerAlert(new Alert(String.valueOf(patient.getId()), "High heart rate: " + value, record.getTimestamp()));
            } else if (type.contains("saturation") && value < 92) {
                triggerAlert(new Alert(String.valueOf(patient.getId()), "Low oxygen saturation: " + value, record.getTimestamp()));
            } else if (type.contains("bloodpressure") && value > 180) {
                triggerAlert(new Alert(String.valueOf(patient.getId()), "High blood pressure: " + value, record.getTimestamp()));
            } else if (type.equals("alerts")) {
                String status = String.valueOf(value);
                if (status.equals("triggered") || status.equals("resolved")) {
                    triggerAlert(new Alert(String.valueOf(patient.getId()), "Button alert: " + status, record.getTimestamp()));
                }
            }
        }

        List<PatientRecord> saturationRecords = records.stream()
                .filter(r -> r.getRecordType().toLowerCase().contains("saturation"))
                .sorted(Comparator.comparingLong(PatientRecord::getTimestamp))
                .collect(Collectors.toList());

        for (int i = 1; i < saturationRecords.size(); i++) {
            double previous = saturationRecords.get(i - 1).getMeasurementValue();
            double current = saturationRecords.get(i).getMeasurementValue();
            if (previous - current >= 5.0) {
                triggerAlert(new Alert(
                        String.valueOf(patient.getId()),
                        "Rapid drop in saturation: from " + previous + "% to " + current + "%",
                        saturationRecords.get(i).getTimestamp()));
            }
        }

        boolean hasLowSystolic = records.stream()
                .anyMatch(r -> r.getRecordType().toLowerCase().contains("systolic") && r.getMeasurementValue() < 90);

        boolean hasLowSaturation = records.stream()
                .anyMatch(r -> r.getRecordType().toLowerCase().contains("saturation") && r.getMeasurementValue() < 92);

        if (hasLowSystolic && hasLowSaturation) {
            triggerAlert(new Alert(
                    String.valueOf(patient.getId()),
                    "Hypotensive Hypoxemia Alert: Systolic BP < 90 and Saturation < 92%",
                    now));
        }

        detectAbnormalPeaks(patient, records);
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        System.out.println("[ALERT] Patient ID: " + alert.getPatientId()
                + " | Condition: " + alert.getCondition()
                + " | Time: " + alert.getTimestamp());
    }

    private void detectAbnormalPeaks(Patient patient, List<PatientRecord> records) {
        records.stream()
                .collect(Collectors.groupingBy(PatientRecord::getRecordType))
                .forEach((type, grouped) -> {
                    if (grouped.size() < 3) return;

                    double average = grouped.stream()
                            .mapToDouble(PatientRecord::getMeasurementValue)
                            .average()
                            .orElse(0);

                    for (PatientRecord r : grouped) {
                        if (Math.abs(r.getMeasurementValue() - average) > (average * 0.3)) {
                            triggerAlert(new Alert(
                                    String.valueOf(patient.getId()),
                                    "Abnormal " + type + " value: " + r.getMeasurementValue() + " (avg: " + average + ")",
                                    r.getTimestamp()));
                        }
                    }
                });
    }
}
