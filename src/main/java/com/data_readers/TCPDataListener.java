package com.data_readers;

import com.data_management.PatientData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPDataListener implements DataListener {

    private final int port;
    private final DataSourceAdapter adapter;
    private final DataParser parser;

    public TCPDataListener(int port, DataSourceAdapter adapter, DataParser parser) {
        this.port = port;
        this.adapter = adapter;
        this.parser = parser;
    }

    @Override
    public void startListening() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Listener running on port " + port);
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                PatientData data = parser.parse(line);
                adapter.handleData(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
