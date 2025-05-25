package com.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketDataReaderTest {

    private WebSocketDataReader reader;
    private DataStorage mockStorage;

    @BeforeEach
    public void setup() {
        reader = new WebSocketDataReader("ws://localhost:8080");
        mockStorage = new DataStorage();
    }

    @Test
    public void testStartStreamingHandlesBadURI() {
        WebSocketDataReader badReader = new WebSocketDataReader("bad_uri");
        assertThrows(IOException.class, () -> badReader.startStreaming(mockStorage));
    }

    // ✅ Standalone fake object with matching behavior, not a subclass
    static class MockStorage {

        boolean called = false;

        public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
            called = true;
            System.out.println("Mock: addPatientData called with " + patientId);
        }
    }
}
