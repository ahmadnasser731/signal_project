package com.alerts;

import com.alerts.Alert;
import com.alerts.HeartRateStrategy;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeartRateStrategyTest {

    @Test
    void testHeartRateAboveThresholdTriggersAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(135.0, "HeartRate", System.currentTimeMillis());

        HeartRateStrategy strategy = new HeartRateStrategy();
        List<Alert> alerts = strategy.checkAlert(patient);

        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).getCondition().contains("High heart rate"));
    }

    @Test
    void testHeartRateBelowThresholdDoesNotTriggerAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(85.0, "HeartRate", System.currentTimeMillis());

        HeartRateStrategy strategy = new HeartRateStrategy();
        List<Alert> alerts = strategy.checkAlert(patient);

        assertTrue(alerts.isEmpty());
    }
}
