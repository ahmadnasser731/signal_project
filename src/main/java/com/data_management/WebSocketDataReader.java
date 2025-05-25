package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Reads live patient data from a WebSocket server and adds it to DataStorage.
 */
public class WebSocketDataReader implements DataReader {

    private final String serverUri;
    private WebSocketClient client;

    public WebSocketDataReader(String serverUri) {
        this.serverUri = serverUri;
    }

    @Override
    public void startStreaming(DataStorage dataStorage) throws IOException {
        try {
            client = new WebSocketClient(new URI(serverUri)) {

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to WebSocket server.");
                }

                @Override
                public void onMessage(String message) {
                    try {
                        // Expecting format: patientId,timestamp,label,value
                        String[] parts = message.split(",");
                        if (parts.length != 4) {
                            System.err.println("Bad message: " + message);
                            return;
                        }

                        int patientId = Integer.parseInt(parts[0].trim());
                        long timestamp = Long.parseLong(parts[1].trim());
                        String label = parts[2].trim();
                        double value = Double.parseDouble(parts[3].trim().replace("%", ""));

                        dataStorage.addPatientData(patientId, value, label, timestamp);
                    } catch (Exception e) {
                        System.err.println("Error handling message: " + message + " => " + e.getMessage());
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("WebSocket error: " + ex.getMessage());
                }
            };

            client.connect();

        } catch (URISyntaxException e) {
            throw new IOException("Bad WebSocket URI", e);
        }
    }

    @Override
    public void stopStreaming() throws IOException {
        if (client != null && client.isOpen()) {
            client.close();
        }
    }
}
