package com.alerts;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertGeneratorTest {

    private DataStorage storage;
    private AlertGenerator generator;
    private long now;

    @BeforeEach
    void setUp() {
        storage = new DataStorage();
        generator = new AlertGenerator(storage);
        now = System.currentTimeMillis();
    }

    @Test
    void testHighHeartRateAlert() {
        storage.addPatientData(1, 135.0, "HeartRate", now);
        generator.evaluateData(storage.getAllPatients().get(0));
        // Manually verify output (or inject a logger if needed)
    }

    @Test
    void testLowSaturationAlert() {
        storage.addPatientData(2, 89.0, "Saturation", now);
        generator.evaluateData(storage.getAllPatients().get(0));
    }

    @Test
    void testRapidDropInSaturation() {
        storage.addPatientData(3, 97.0, "Saturation", now - 600000);
        storage.addPatientData(3, 91.0, "Saturation", now);
        generator.evaluateData(storage.getAllPatients().get(0));
    }

    @Test
    void testHypotensiveHypoxemiaAlert() {
        storage.addPatientData(4, 88.0, "Systolic", now);
        storage.addPatientData(4, 91.0, "Saturation", now);
        generator.evaluateData(storage.getAllPatients().get(0));
    }

    @Test
    void testAbnormalPeakAlert() {
        storage.addPatientData(5, 100.0, "HeartRate", now - 30000);
        storage.addPatientData(5, 102.0, "HeartRate", now - 20000);
        storage.addPatientData(5, 160.0, "HeartRate", now - 10000); // abnormal
        generator.evaluateData(storage.getAllPatients().get(0));
    }

    @Test
    void testButtonTriggeredAlert() {
        storage.addPatientData(6, 1.0, "Alert", now); // simulated "triggered"
        generator.evaluateData(storage.getAllPatients().get(0));
    }

    @Test
    void testCriticalHighBloodPressure() {
        storage.addPatientData(7, 190.0, "BloodPressure", now);
        generator.evaluateData(storage.getAllPatients().get(0));
    }

    @Test
    void testNoAlertsWhenValuesNormal() {
        storage.addPatientData(8, 120.0, "HeartRate", now);
        storage.addPatientData(8, 96.0, "Saturation", now);
        storage.addPatientData(8, 110.0, "BloodPressure", now);
        generator.evaluateData(storage.getAllPatients().get(0));
    }
}
