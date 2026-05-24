package packagee.service;

import packagee.*;
import packagee.response.ServiceResponse;
import java.util.ArrayList;

/**
 * Contrato para el servicio de hospitalizaciones.
 * Principio SOLID: Inversión de Dependencias (DIP).
 */
public interface IHospitalizationService {
    ServiceResponse createHospitalization(
            Patient patient, Doctor doctor,
            String dateStr, String reason, String roomTypeStr, String observations,
            ArrayList<Hospitalization> hospitalizations);

    ServiceResponse cancelHospitalization(
            String hospitalizationId,
            ArrayList<Hospitalization> hospitalizations);

    Hospitalization findById(String id, ArrayList<Hospitalization> hospitalizations);
}
