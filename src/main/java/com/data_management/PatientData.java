package com.data_management;

public class PatientData {
    private final int patientId;
    private final String vitalType;
    private final double value;
    private final long timestamp;

    public PatientData(int patientId, String vitalType, double value, long timestamp) {
        this.patientId = patientId;
        this.vitalType = vitalType;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getVitalType() {
        return vitalType;
    }

    public double getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
