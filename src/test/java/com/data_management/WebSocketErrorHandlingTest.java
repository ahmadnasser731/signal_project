package com.data_management;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketErrorHandlingTest {

    @Test
    public void testMalformedMessageIgnored() throws Exception {
        String badMessage = "1,InvalidTimestamp,HeartRate,130";
        WebSocketDataReader reader = new WebSocketDataReader("ws://localhost:8080");
        DataStorage storage = DataStorage.getInstance();

        Method onMessage = reader.getClass().getDeclaredField("client").getType().getDeclaredMethod("onMessage", String.class);
        onMessage.setAccessible(true);

        reader.startStreaming(storage); // Start to initialize client
        try {
            onMessage.invoke(reader, badMessage);
        } catch (Exception e) {
            // Fail-safe: no crash expected
            fail("Malformed message should not throw exception.");
        }
    }
}
