package com.cardio_generator.outputs;
/**
 * This is the basic setup for all output types.
 * Anything that wants to send out data (console, file, TCP, etc.)
 * needs to follow this format.
 */
public interface OutputStrategy {
    /**
     * Sends out some data for a patient.
     *
     * @param patientId The ID of the patient.
     * @param timestamp When the data was created (in milliseconds).
     * @param label What kind of data it is (like "ecg", "alert", etc).
     * @param data The actual data value (like a number or message).
     */
    void output(int patientId, long timestamp, String label, String data);
}
