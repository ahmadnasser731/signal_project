package com.alerts;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BloodOxygenAlertTest {

    @Test
    void testCreateAlert() {
        BloodOxygenAlert alert = new BloodOxygenAlert("1", "Low O2", 12345L);
        Alert created = alert.createAlert("1", "Low O2", 12345L);
        assertNotNull(created);
        assertEquals("1", ((Alert) created).getPatientId());
        assertEquals("Low O2", created.getCondition());
        assertEquals(12345L, created.getTimestamp());
    }
}
