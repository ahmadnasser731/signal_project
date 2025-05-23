package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * This is a basic setup for all the data generator classes.
 * Anything that generates patient data (like ECG, blood pressure, etc.)
 * has to follow this format.
 */
public interface PatientDataGenerator {
    /**
     * Makes and sends some fake data for a patient.
     *
     * @param patientId The ID of the patient we're generating data for.
     * @param outputStrategy Where to send the data (like console, file, TCP, etc).
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
