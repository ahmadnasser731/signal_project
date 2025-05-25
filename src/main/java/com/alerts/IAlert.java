package com.alerts;

public interface IAlert {
    String getPatientId();
    String getCondition();
    long getTimestamp();
    String getPriority();
}
