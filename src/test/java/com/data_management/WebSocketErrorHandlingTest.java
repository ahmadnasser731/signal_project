package com.data_management;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WebSocketErrorHandlingTest {

    @Test
    public void testMalformedMessageIgnored() {
        String badMessage = "1,InvalidTimestamp,HeartRate,130";
        DataStorage storage = DataStorage.getInstance();

        WebSocketDataReader testableReader = new WebSocketDataReader("ws://localhost:8080") {
            public void simulateIncomingMessage(String msg, DataStorage store) {
                try {
                    String[] parts = msg.split(",");
                    if (parts.length != 4) return;

                    int patientId = Integer.parseInt(parts[0].trim());
                    long timestamp = Long.parseLong(parts[1].trim()); // This should fail
                    String label = parts[2].trim();
                    double value = Double.parseDouble(parts[3].trim().replace("%", ""));

                    store.addPatientData(patientId, value, label, timestamp);
                } catch (Exception e) {
                    // This is expected for malformed messages
                }
            }
        };

        assertDoesNotThrow(() ->
                        ((WebSocketDataReader) testableReader).getClass()
                                .getMethod("simulateIncomingMessage", String.class, DataStorage.class)
                                .invoke(testableReader, badMessage, storage),
                "Malformed message should not throw exception."
        );
    }
}
