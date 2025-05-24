package com.data_management;

public class HospitalPatient {

    private final int patientId;
    private final String name;
    private final int age;
    private final String medicalHistory;

    public HospitalPatient(int patientId, String name, int age, String medicalHistory) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.medicalHistory = medicalHistory;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }
}
