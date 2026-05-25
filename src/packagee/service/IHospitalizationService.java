package packagee.service;

import packagee.*;
import packagee.response.ServiceResponse;
import java.util.ArrayList;

public interface IHospitalizationService {
    ServiceResponse createHospitalization(
            Patient patient, Doctor doctor,
            String dateStr, String reason, String roomTypeStr, String observations,
            ArrayList<Hospitalization> hospitalizations);

    ServiceResponse cancelHospitalization(String hospitalizationId, ArrayList<Hospitalization> hospitalizations);
    ServiceResponse approveHospitalization(String hospitalizationId, ArrayList<Hospitalization> hospitalizations);
    ServiceResponse createDoctorHospitalization(Patient patient, Doctor doctor, String dateStr, String reason, String roomTypeStr, String observations, ArrayList<Hospitalization> hospitalizations);

    Hospitalization findById(String id, ArrayList<Hospitalization> hospitalizations);
}
