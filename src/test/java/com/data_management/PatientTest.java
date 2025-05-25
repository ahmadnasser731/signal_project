package com.data_management;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    @Test
    void testAddAndFilterRecords() {
        Patient patient = new Patient(1);
        long now = System.currentTimeMillis();
        patient.addRecord(88.0, "HeartRate", now);

        List<PatientRecord> filtered = patient.getRecords(now - 1000, now + 1000);
        assertEquals(1, filtered.size());
        assertEquals("HeartRate", filtered.get(0).getRecordType());
    }

    @Test
    void testGetId() {
        Patient patient = new Patient(42);
        assertEquals(42, patient.getId());
    }
}
