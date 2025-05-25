package com.alerts;

public abstract class AlertDecorator implements IAlert {
    protected IAlert decoratedAlert;

    public AlertDecorator(IAlert decoratedAlert) {
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    @Override
    public String getPriority() {
        return decoratedAlert.getPriority();
    }
}
