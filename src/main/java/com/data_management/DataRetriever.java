package com.data_management;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataRetriever {
    private final DataStorage storage;
    private long keepDataFor = Long.MAX_VALUE;

    public DataRetriever(DataStorage storage) {
        this.storage = storage;
    }

    public void setRetentionTime(long millis) {
        this.keepDataFor = millis;
    }

    public void cleanOldData() {
        long cutoffTime = System.currentTimeMillis() - keepDataFor;
        long now = System.currentTimeMillis();

        for (Patient p : storage.getAllPatients()) {
            List<PatientRecord> records = p.getRecords(0, now);
            Iterator<PatientRecord> it = records.iterator();
            while (it.hasNext()) {
                if (it.next().getTimestamp() < cutoffTime) {
                    it.remove();
                }
            }
        }
    }

    public List<PatientRecord> fetchByTime(int id, long start, long end) {
        return storage.getRecords(id, start, end);
    }

    public List<PatientRecord> fetchByType(int id, String type) {
        List<PatientRecord> all = storage.getRecords(id, 0, System.currentTimeMillis());
        List<PatientRecord> filtered = new ArrayList<>();
        for (PatientRecord r : all) {
            if (r.getRecordType().equalsIgnoreCase(type)) {
                filtered.add(r);
            }
        }
        return filtered;
    }
}
