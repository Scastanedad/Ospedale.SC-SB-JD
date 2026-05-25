package packagee.service;

import packagee.*;
import packagee.repository.IHospitalizationRepository;
import packagee.response.ServiceResponse;
import packagee.util.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class HospitalizationService implements IHospitalizationService {

    private final IHospitalizationRepository hospitalizationRepo;

    public HospitalizationService(IHospitalizationRepository hospitalizationRepo) {
        this.hospitalizationRepo = hospitalizationRepo;
    }

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
        String id = generateId(hospitalizations, patient.getId());

        Hospitalization hospitalization = new Hospitalization(
                id, patient, doctor, date, reason, roomType, observations);

        hospitalizations.add(hospitalization);
        hospitalizationRepo.saveAll(hospitalizations);

        return ServiceResponse.ok("Hospitalización solicitada con ID: " + id);
    }

    public ServiceResponse createDoctorHospitalization(
            Patient patient, Doctor doctor,
            String dateStr, String reason, String roomTypeStr, String observations,
            ArrayList<Hospitalization> hospitalizations) {

        ServiceResponse response = createHospitalization(patient, doctor, dateStr, reason, roomTypeStr, observations, hospitalizations);
        if (response.isSuccess()) {
            Hospitalization h = hospitalizations.get(hospitalizations.size() - 1);
            h.setStatus(HospitalizationStatus.ONGOING);
            hospitalizationRepo.saveAll(hospitalizations);
        }
        return response;
    }

    public ServiceResponse approveHospitalization(
            String hospitalizationId,
            ArrayList<Hospitalization> hospitalizations) {

        Hospitalization hosp = findById(hospitalizationId, hospitalizations);
        if (hosp == null)
            return ServiceResponse.notFound("Hospitalización no encontrada: " + hospitalizationId);
        if (hosp.getStatus() != HospitalizationStatus.REQUESTED)
            return ServiceResponse.badRequest("Solo se pueden aprobar hospitalizaciones en estado REQUESTED.");

        hosp.setStatus(HospitalizationStatus.ONGOING);

        hospitalizationRepo.saveAll(hospitalizations);
        return ServiceResponse.ok("Hospitalización aprobada (ONGOING).");
    }

    public ServiceResponse cancelHospitalization(
            String hospitalizationId,
            ArrayList<Hospitalization> hospitalizations) {

        Hospitalization hosp = findById(hospitalizationId, hospitalizations);
        if (hosp == null)
            return ServiceResponse.notFound("Hospitalización no encontrada: " + hospitalizationId);
        if (hosp.getStatus() == HospitalizationStatus.CANCELED)
            return ServiceResponse.badRequest("La hospitalización ya está cancelada.");

        hosp.setStatus(HospitalizationStatus.CANCELED);
        if (hosp.getPatient() != null) {
            hosp.getPatient().setHospitalization(null);
        }

        hospitalizationRepo.saveAll(hospitalizations);
        return ServiceResponse.ok("Hospitalización cancelada.");
    }

    public Hospitalization findById(String id, ArrayList<Hospitalization> hospitalizations) {
        for (Hospitalization h : hospitalizations) {
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    private String generateId(ArrayList<Hospitalization> hospitalizations, long patientId) {
        int max = 0;
        String prefix = "H-" + patientId + "-";
        for (Hospitalization h : hospitalizations) {
            if (h.getId().startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(h.getId().substring(prefix.length()));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("%s%04d", prefix, max + 1);
    }
}
