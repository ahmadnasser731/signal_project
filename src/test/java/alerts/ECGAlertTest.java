package alerts;

import com.alerts.Alert;
import com.alerts.ECGAlert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ECGAlertTest {

    @Test
    void testCreateAlert() {
        ECGAlert alert = new ECGAlert("1", "High HR", 54321L);
        Alert result = alert.createAlert("1", "High HR", 54321L);
        assertEquals("High HR", result.getCondition());
    }
}
