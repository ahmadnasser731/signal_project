package data_management;

import com.data_management.HospitalPatient;
import com.data_management.PatientIdentifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientIdentifierTest {

    @Test
    void testRegisterAndIdentify() {
        PatientIdentifier id = new PatientIdentifier();
        HospitalPatient patient = new HospitalPatient(1, "Alice", 30, "None");
        id.registerPatient(patient);

        assertTrue(id.exists(1));
        assertEquals("Alice", id.identify(1).getName());
    }

    @Test
    void testNonexistentPatient() {
        PatientIdentifier id = new PatientIdentifier();
        assertFalse(id.exists(999));
        assertNull(id.identify(999));
    }
}
