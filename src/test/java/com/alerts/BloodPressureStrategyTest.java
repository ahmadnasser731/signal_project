package com.alerts;

import com.alerts.Alert;
import com.alerts.BloodPressureStrategy;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodPressureStrategyTest {

    @Test
    void testCriticalHighBPAlert() {
        Patient patient = new Patient(3);
        patient.addRecord(185.0, "BloodPressure", System.currentTimeMillis());

        BloodPressureStrategy strategy = new BloodPressureStrategy();
        List<Alert> alerts = strategy.checkAlert(patient);

        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).getCondition().contains("Critical BP"));
    }

    @Test
    void testTrendAlertTriggered() {
        Patient patient = new Patient(3);
        patient.addRecord(110.0, "BloodPressure", System.currentTimeMillis() - 6000);
        patient.addRecord(123.0, "BloodPressure", System.currentTimeMillis() - 3000);
        patient.addRecord(137.0, "BloodPressure", System.currentTimeMillis());

        BloodPressureStrategy strategy = new BloodPressureStrategy();
        List<Alert> alerts = strategy.checkAlert(patient);

        assertFalse(alerts.isEmpty());
    }
}
