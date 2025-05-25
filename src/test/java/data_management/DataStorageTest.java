package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);

        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue());
        assertEquals(200.0, records.get(1).getMeasurementValue());
    }

    @Test
    void testGetRecordsForUnknownPatient() {
        DataStorage storage = new DataStorage();
        List<PatientRecord> records = storage.getRecords(999, 0, System.currentTimeMillis());
        assertTrue(records.isEmpty());
    }

    @Test
    void testAddMultipleRecordTypes() {
        DataStorage storage = new DataStorage();
        long time = System.currentTimeMillis();
        storage.addPatientData(2, 75.5, "HeartRate", time);
        storage.addPatientData(2, 88.2, "Saturation", time);

        List<PatientRecord> records = storage.getRecords(2, time - 1000, time + 1000);
        assertEquals(2, records.size());
        assertTrue(records.stream().anyMatch(r -> r.getRecordType().equalsIgnoreCase("HeartRate")));
        assertTrue(records.stream().anyMatch(r -> r.getRecordType().equalsIgnoreCase("Saturation")));
    }

    @Test
    void testTimeRangeFiltering() {
        DataStorage storage = new DataStorage();
        long now = System.currentTimeMillis();
        storage.addPatientData(3, 60, "HeartRate", now - 5000);
        storage.addPatientData(3, 62, "HeartRate", now);
        storage.addPatientData(3, 64, "HeartRate", now + 5000);

        List<PatientRecord> records = storage.getRecords(3, now - 1000, now + 1000);
        assertEquals(1, records.size());
        assertEquals(62, records.get(0).getMeasurementValue());
    }
}
