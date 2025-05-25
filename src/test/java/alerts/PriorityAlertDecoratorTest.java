package alerts;

import com.alerts.ECGAlert;
import com.alerts.IAlert;
import com.alerts.PriorityAlertDecorator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityAlertDecoratorTest {

    @Test
    void testPriorityAlertDecorator() {
        IAlert baseAlert = new ECGAlert("10", "Normal", 1000L);
        IAlert decorated = new PriorityAlertDecorator(baseAlert, "HIGH");
        assertTrue(decorated.getCondition().contains("[PRIORITY: HIGH]"));
    }
}
