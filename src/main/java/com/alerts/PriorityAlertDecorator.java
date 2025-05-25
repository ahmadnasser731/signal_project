package com.alerts;

public class PriorityAlertDecorator extends AlertDecorator {
    private final String priority;

    public PriorityAlertDecorator(IAlert decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    @Override
    public String getPriority() {
        return priority;
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition() + " [PRIORITY: " + priority + "]";
    }
}
