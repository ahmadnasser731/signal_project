package com.alerts;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * The {@code AlertManager} handles and dispatches alerts to medical staff.
 * It evaluates patient vitals against personalized thresholds and routes alerts
 * when conditions are exceeded.
 */
public class AlertManager {

    private List<Alert> alertLog;

    // Nested map: patientId -> (vitalType -> threshold)
    private Map<String, Map<String, Double>> thresholds;

    /**
     * Constructs an AlertManager with an empty log and threshold set.
     */
    public AlertManager() {
        this.alertLog = new ArrayList<>();
        this.thresholds = new HashMap<>();
    }

    /**
     * Sets a custom threshold for a given patient and vital sign type.
     *
     * @param patientId the patient's ID
     * @param vitalType the type of vital (e.g. "HeartRate")
     * @param threshold the threshold value that triggers an alert
     */
    public void setThreshold(String patientId, String vitalType, double threshold) {
        thresholds
                .computeIfAbsent(patientId, k -> new HashMap<>())
                .put(vitalType, threshold);
    }

    /**
     * Evaluates a data point against a patient's thresholds.
     * If the threshold is exceeded, an alert is triggered.
     *
     * @param patientId the patient's ID
     * @param vitalType the type of data (e.g., "HeartRate")
     * @param value     the new measurement value
     * @param timestamp time of the measurement
     */
    public void evaluateAndDispatch(String patientId, String vitalType, double value, long timestamp) {
        double threshold = thresholds
                .getOrDefault(patientId, new HashMap<>())
                .getOrDefault(vitalType, Double.MAX_VALUE);

        if (value > threshold) {
            Alert alert = new Alert(patientId, vitalType + " exceeded: " + value, timestamp);
            dispatchAlert(alert);
        }
    }

    /**
     * Logs and "sends" the alert to staff (console output for now).
     *
     * @param alert the alert to dispatch
     */
    public void dispatchAlert(Alert alert) {
        alertLog.add(alert);
        System.out.println("[ALERT] Patient ID: " + alert.getPatientId()
                + " | Condition: " + alert.getCondition()
                + " | Timestamp: " + alert.getTimestamp());

        // Placeholder: Extend with real alerting logic (email, SMS, etc.)
    }

    /**
     * Returns a log of all dispatched alerts.
     *
     * @return list of alerts
     */
    public List<Alert> getAlertLog() {
        return alertLog;
    }
}
