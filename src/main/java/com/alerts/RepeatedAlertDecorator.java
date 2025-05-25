package com.alerts;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;

    public RepeatedAlertDecorator(IAlert decoratedAlert, int repeatCount) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition() + " (Repeated x" + repeatCount + ")";
    }
}
