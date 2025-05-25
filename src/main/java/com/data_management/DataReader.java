package com.data_management;

import java.io.IOException;

/**
 * Represents a source of patient data. Designed to support real-time ingestion,
 * such as from a WebSocket server.
 */
public interface DataReader {

    /**
     * Connects to a live data source and continuously receives data into the given DataStorage.
     * This method is expected to run asynchronously or block while receiving data.
     *
     * @param dataStorage the storage where incoming data will be saved
     * @throws IOException if the connection or data transfer fails
     */
    void startStreaming(DataStorage dataStorage) throws IOException;

    /**
     * Stops the real-time data stream and releases any associated resources.
     * Useful for clean shutdown.
     *
     * @throws IOException if an error occurs during disconnect
     */
    void stopStreaming() throws IOException;
}
