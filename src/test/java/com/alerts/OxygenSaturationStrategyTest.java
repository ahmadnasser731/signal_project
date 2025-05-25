package com.alerts;

import com.alerts.Alert;
import com.alerts.OxygenSaturationStrategy;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OxygenSaturationStrategyTest {

    @Test
    void testLowOxygenTriggersAlert() {
        Patient patient = new Patient(2);
        patient.addRecord(88.0, "Saturation", System.currentTimeMillis());

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        List<Alert> alerts = strategy.checkAlert(patient);

        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).getCondition().contains("Low O2"));
    }

    @Test
    void testRapidDropTriggersAlert() {
        Patient patient = new Patient(2);
        patient.addRecord(98.0, "Saturation", System.currentTimeMillis() - 5000);
        patient.addRecord(91.0, "Saturation", System.currentTimeMillis());

        OxygenSaturationStrategy strategy = new OxygenSaturationStrategy();
        List<Alert> alerts = strategy.checkAlert(patient);

        assertFalse(alerts.isEmpty());
    }
}
