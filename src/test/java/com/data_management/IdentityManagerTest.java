package com.data_management;

import com.data_management.HospitalPatient;
import com.data_management.IdentityManager;
import com.data_management.PatientIdentifier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdentityManagerTest {

    @Test
    void testVerifyPatientExists() {
        PatientIdentifier identifier = new PatientIdentifier();
        HospitalPatient patient = new HospitalPatient(10, "John", 45, "Diabetes");
        identifier.registerPatient(patient);

        IdentityManager manager = new IdentityManager(identifier);
        assertNotNull(manager.verifyPatient(10));
    }

    @Test
    void testVerifyPatientMissing() {
        PatientIdentifier identifier = new PatientIdentifier();
        IdentityManager manager = new IdentityManager(identifier);

        assertNull(manager.verifyPatient(500)); // Should trigger error log
    }
}
