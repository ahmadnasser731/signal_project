package alerts;

import com.alerts.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlertFactoryTest {

    @Test
    void testBloodOxygenFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("1", "Low O2", 111L);
        assertTrue(alert instanceof BloodOxygenAlert);
    }

    @Test
    void testBloodPressureFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("2", "High BP", 222L);
        assertTrue(alert instanceof BloodPressureAlert);
    }

    @Test
    void testECGFactory() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("3", "ECG Spike", 333L);
        assertTrue(alert instanceof ECGAlert);
    }
}
