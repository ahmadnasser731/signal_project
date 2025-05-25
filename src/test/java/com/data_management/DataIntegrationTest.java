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
        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:8080");

        DataStorage storage = DataStorage.getInstance();

        Method onMessage = reader.getClass().getDeclaredField("client").getType().getDeclaredMethod("onMessage", String.class);
        onMessage.setAccessible(true);

        // Use reflection to simulate the internal message handler
        reader.startStreaming(storage); // Start to initialize client
        onMessage.invoke(reader, message);

        List<PatientRecord> records = storage.getRecords(1, 1716699999999L, 1716700000001L);
        assertFalse(records.isEmpty());
        assertEquals(145.0, records.get(0).getMeasurementValue());
    }
}
