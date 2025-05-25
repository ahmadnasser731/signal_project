package com.data_management;

import com.alerts.AlertGenerator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataIntegrationTest {

    @Test
    public void testMessageParsedAndStored() throws Exception {
        String message = "1,1716700000000,HeartRate,145.0";

        WebSocketDataReader testableReader = new WebSocketDataReader("ws://localhost:8080") {
            public void simulateIncomingMessage(String msg, DataStorage storage) {
                try {
                    String[] parts = msg.split(",");
                    if (parts.length != 4) return;

                    int patientId = Integer.parseInt(parts[0].trim());
                    long timestamp = Long.parseLong(parts[1].trim());
                    String label = parts[2].trim();
                    double value = Double.parseDouble(parts[3].trim().replace("%", ""));

                    storage.addPatientData(patientId, value, label, timestamp);
                } catch (Exception e) {
                    System.err.println("Simulated onMessage error: " + e.getMessage());
                }
            }
        };

        DataStorage storage = DataStorage.getInstance();

        Method simulate = testableReader.getClass()
                .getDeclaredMethod("simulateIncomingMessage", String.class, DataStorage.class);

        simulate.invoke(testableReader, message, storage);

        List<PatientRecord> records = storage.getRecords(1, 1716699999999L, 1716700000001L);
        assertFalse(records.isEmpty());
        assertEquals(145.0, records.get(0).getMeasurementValue());
    }
}
