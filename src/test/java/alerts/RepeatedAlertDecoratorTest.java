package alerts;

import com.alerts.BloodOxygenAlert;
import com.alerts.RepeatedAlertDecorator;
import com.alerts.IAlert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepeatedAlertDecoratorTest {

    @Test
    void testRepeatAlertDecorator() {
        IAlert base = new BloodOxygenAlert("1", "Low", 12345L);
        IAlert repeated = new RepeatedAlertDecorator(base, 3);

        assertTrue(repeated.getCondition().contains("[Repeated 3 times]"));
        assertEquals("1", repeated.getPatientId());
        assertEquals(12345L, repeated.getTimestamp());
    }
}
