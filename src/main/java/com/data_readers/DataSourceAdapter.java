package com.data_readers;

import com.data_management.DataStorage;
import com.data_management.PatientData;

public class DataSourceAdapter {

    private final DataStorage storage;

    public DataSourceAdapter(DataStorage storage) {
        this.storage = storage;
    }

    public void handleData(PatientData data) {
        if (data == null) return;

        storage.addPatientData(data.getPatientId(), data.getValue(), data.getVitalType(), data.getTimestamp());
    }
}
