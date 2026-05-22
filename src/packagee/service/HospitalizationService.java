package packagee.service;

import packagee.*;
import packagee.repository.HospitalizationRepository;
import packagee.response.ServiceResponse;
import packagee.util.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Servicio de hospitalizaciones.
 * Gestiona solicitar y cancelar hospitalizaciones.
 */
public class HospitalizationService {

    private final HospitalizationRepository hospitalizationRepo;

    public HospitalizationService(HospitalizationRepository hospitalizationRepo) {
        this.hospitalizationRepo = hospitalizationRepo;
    }

    // ── Solicitar hospitalización ─────────────────────────────────────────────

    /**
     * Solicita una hospitalización para un paciente.
     * Estado inicial: REQUESTED.
     *
     * @param patient       paciente a hospitalizar
     * @param doctor        doctor responsable
     * @param dateStr       fecha estimada de ingreso (AAAA-MM-DD)
     * @param reason        motivo
     * @param roomTypeStr   tipo de habitación (enum name)
     * @param observations  observaciones adicionales
     * @param hospitalizations lista en memoria
     */
    public ServiceResponse createHospitalization(
            Patient patient, Doctor doctor,
            String dateStr, String reason, String roomTypeStr, String observations,
            ArrayList<Hospitalization> hospitalizations) {

        if (patient == null) return ServiceResponse.badRequest("Paciente no encontrado.");
        if (doctor == null) return ServiceResponse.badRequest("Doctor no encontrado.");
        if (!Validator.isValidDate(dateStr))
            return ServiceResponse.badRequest("Fecha inválida. Formato: AAAA-MM-DD");
        if (!Validator.notBlank(reason))
            return ServiceResponse.badRequest("El motivo de hospitalización es obligatorio.");

        // Verificar que el paciente no tenga ya una hospitalización activa
        if (patient.getHospitalization() != null) {
            HospitalizationStatus current = patient.getHospitalization().getStatus();
            if (current == HospitalizationStatus.REQUESTED || current == HospitalizationStatus.ONGOING)
                return ServiceResponse.conflict("El paciente ya tiene una hospitalización activa (estado: " + current + ").");
        }

        RoomType roomType;
        try {
            roomType = RoomType.valueOf(roomTypeStr.toUpperCase());
        } catch (Exception e) {
            return ServiceResponse.badRequest("Tipo de habitación inválido: " + roomTypeStr);
        }

        LocalDate date = LocalDate.parse(dateStr);
        String id = generateId();

        Hospitalization hospitalization = new Hospitalization(
                id, patient, doctor, date, reason, roomType, observations);

        hospitalizations.add(hospitalization);
        hospitalizationRepo.saveAll(hospitalizations);

        return ServiceResponse.ok("Hospitalización solicitada con ID: " + id, hospitalization);
    }

    // ── Cancelar hospitalización ──────────────────────────────────────────────

    /**
     * Cancela una hospitalización existente → CANCELED.
     * No se puede cancelar si ya está CANCELED.
     */
    public ServiceResponse cancelHospitalization(
            String hospitalizationId,
            ArrayList<Hospitalization> hospitalizations) {

        Hospitalization hosp = findById(hospitalizationId, hospitalizations);
        if (hosp == null)
            return ServiceResponse.notFound("Hospitalización no encontrada: " + hospitalizationId);
        if (hosp.getStatus() == HospitalizationStatus.CANCELED)
            return ServiceResponse.badRequest("La hospitalización ya está cancelada.");

        hosp.setStatus(HospitalizationStatus.CANCELED);
        // Limpiar referencia del paciente
        if (hosp.getPatient() != null) {
            hosp.getPatient().setHospitalization(null);
        }

        hospitalizationRepo.saveAll(hospitalizations);
        return ServiceResponse.ok("Hospitalización cancelada.", hosp);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    public Hospitalization findById(String id, ArrayList<Hospitalization> hospitalizations) {
        for (Hospitalization h : hospitalizations) {
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    private String generateId() {
        return "HOS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
