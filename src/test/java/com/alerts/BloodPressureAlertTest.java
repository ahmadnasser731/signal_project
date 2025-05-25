package com.alerts;

import com.alerts.Alert;
import com.alerts.BloodPressureAlert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BloodPressureAlertTest {

    @Test
    void testCreateAlert() {
        BloodPressureAlert alert = new BloodPressureAlert("1", "High BP", 99999L);
        Alert result = alert.createAlert("1", "High BP", 99999L);
        assertEquals("High BP", result.getCondition());
    }
}
