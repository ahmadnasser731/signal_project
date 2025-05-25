package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * Sends patient data over WebSocket to connected clients.
 */
public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;

    public WebSocketOutputStrategy(int port) {
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port);
        server.start();
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        // Making a simple CSV message
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
        for (WebSocket conn : server.getConnections()) {
            conn.send(message); // send to whoever's connected
        }
    }

    private static class SimpleWebSocketServer extends WebSocketServer {

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("Client joined: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Client left: " + conn.getRemoteSocketAddress());
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Server doesn't need incoming messages, so do nothing
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace(); // print the problem
        }

        @Override
        public void onStart() {
            System.out.println("WebSocket server running");
        }
    }
}
