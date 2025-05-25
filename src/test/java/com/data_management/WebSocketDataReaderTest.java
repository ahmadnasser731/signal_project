package com.data_management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketDataReaderTest {

    private WebSocketDataReader reader;
    private TestableDataStorage mockStorage;

    @BeforeEach
    public void setup() {
        reader = new WebSocketDataReader("ws://localhost:8080");
        mockStorage = new TestableDataStorage();
    }

    @Test
    public void testStartStreamingHandlesBadURI() {
        WebSocketDataReader badReader = new WebSocketDataReader("bad_uri");
        assertThrows(IOException.class, () -> badReader.startStreaming(mockStorage));
    }

    // Custom testable mock, does NOT extend DataStorage
    static class TestableDataStorage extends DataStorage {

        private boolean called = false;

        public boolean wasCalled() {
            return called;
        }

        @Override
        public synchronized void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
            this.called = true;
            System.out.println("Mock addPatientData called");
        }
    }
}
