package com.alerts;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class AlertManager {

    private List<Alert> alertLog;
    private Map<String, Map<String, Double>> thresholds;

    public AlertManager() {
        this.alertLog = new ArrayList<>();
        this.thresholds = new HashMap<>();
    }

    public void setThreshold(String patientId, String vitalType, double threshold) {
        thresholds
                .computeIfAbsent(patientId, k -> new HashMap<>())
                .put(vitalType, threshold);
    }

    public void evaluateAndPrint(String patientId, String vitalType, double value, long timestamp) {
        double threshold = thresholds
                .getOrDefault(patientId, new HashMap<>())
                .getOrDefault(vitalType, Double.MAX_VALUE);

        if (value > threshold || (vitalType.equalsIgnoreCase("saturation") && value < 92)) {
            Alert alert = new Alert(patientId, vitalType + " out of range: " + value, timestamp) {
                @Override
                public String getPriority() {
                    return "";
                }

                @Override
                public Alert createAlert(String patientId, String condition, long timestamp) {
                    return null;
                }
            };
            printAlert(alert);
        }
    }

    public void printAlert(Alert alert) {
        alertLog.add(alert);
        System.out.println("[ALERT] Patient ID: " + alert.getPatientId()
                + " | Condition: " + alert.getCondition()
                + " | Timestamp: " + alert.getTimestamp());
    }

    public List<Alert> getAlertLog() {
        return alertLog;
    }
}
