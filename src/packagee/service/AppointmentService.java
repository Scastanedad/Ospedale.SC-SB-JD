package packagee.service;

import packagee.*;
import packagee.repository.AppointmentRepository;
import packagee.response.ServiceResponse;
import packagee.util.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Servicio de citas médicas.
 * Contiene toda la lógica de negocio para el ciclo de vida de citas.
 *
 * Las vistas NUNCA manipulan Appointment directamente.
 */
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;

    public AppointmentService(AppointmentRepository appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    // ── Crear cita ────────────────────────────────────────────────────────────

    /**
     * El paciente solicita una nueva cita médica.
     * Estado inicial: REQUESTED.
     *
     * @param patient  paciente que solicita
     * @param doctor   doctor asignado (puede venir por especialidad o por nombre)
     * @param dateStr  fecha AAAA-MM-DD
     * @param timeStr  hora hh:mm (minutos: 00, 15, 30, 45)
     * @param reason   motivo de la cita
     * @param typeStr  "Remote" o "In-person"
     * @param appointments lista en memoria
     */
    public ServiceResponse createAppointment(
            Patient patient, Doctor doctor,
            String dateStr, String timeStr, String reason, String typeStr,
            ArrayList<Appointment> appointments) {

        if (patient == null) return ServiceResponse.badRequest("Paciente no encontrado.");
        if (doctor == null) return ServiceResponse.badRequest("Doctor no encontrado.");
        if (!Validator.isValidDate(dateStr))
            return ServiceResponse.badRequest("Fecha inválida. Formato: AAAA-MM-DD");
        if (!Validator.isValidTime(timeStr))
            return ServiceResponse.badRequest("Hora inválida. Formato: hh:mm — minutos permitidos: 00, 15, 30, 45");
        if (!Validator.notBlank(reason))
            return ServiceResponse.badRequest("El motivo de la cita es obligatorio.");

        LocalDate date = LocalDate.parse(dateStr);
        LocalTime time = LocalTime.parse(timeStr);
        LocalDateTime datetime = LocalDateTime.of(date, time);

        // Verificar disponibilidad del doctor en ese horario
        for (Appointment existing : doctor.getAppointments()) {
            if (existing.getDatetime().equals(datetime)
                    && existing.getStatus() != AppointmentStatus.CANCELED) {
                return ServiceResponse.conflict("El doctor no está disponible en ese horario.");
            }
        }

        boolean type = "In-person".equalsIgnoreCase(typeStr) || "2".equals(typeStr);
        String id = generateId(appointments);

        Appointment appointment = new Appointment(id, patient, doctor, doctor.getSpecialty(), datetime, reason, type);
        appointments.add(appointment);
        patient.addAppointment(appointment);
        doctor.getAppointments().add(appointment);

        appointmentRepo.saveAll(appointments);
        return ServiceResponse.ok("Cita solicitada exitosamente con ID: " + id);
    }

    // ── Aceptar cita ──────────────────────────────────────────────────────────

    /**
     * El doctor acepta una cita → REQUESTED → PENDING.
     */
    public ServiceResponse acceptAppointment(String appointmentId, ArrayList<Appointment> appointments) {
        Appointment appointment = findById(appointmentId, appointments);
        if (appointment == null)
            return ServiceResponse.notFound("Cita no encontrada: " + appointmentId);
        if (appointment.getStatus() != AppointmentStatus.REQUESTED)
            return ServiceResponse.badRequest("Solo se pueden aceptar citas en estado REQUESTED. Estado actual: " + appointment.getStatus());

        appointment.setStatus(AppointmentStatus.PENDING);
        appointmentRepo.saveAll(appointments);
        return ServiceResponse.ok("Cita aceptada. Estado: PENDING");
    }

    // ── Completar cita ────────────────────────────────────────────────────────

    /**
     * El doctor completa una cita → PENDING → COMPLETED.
     * Registra diagnóstico, observaciones, tratamiento y seguimiento.
     */
    public ServiceResponse completeAppointment(
            String appointmentId, String diagnosis, String observations,
            String recommendedTreatment, String followUp,
            ArrayList<Appointment> appointments) {

        Appointment appointment = findById(appointmentId, appointments);
        if (appointment == null)
            return ServiceResponse.notFound("Cita no encontrada: " + appointmentId);
        if (appointment.getStatus() != AppointmentStatus.PENDING)
            return ServiceResponse.badRequest("Solo se pueden completar citas en estado PENDING. Estado actual: " + appointment.getStatus());
        if (!Validator.notBlank(diagnosis))
            return ServiceResponse.badRequest("El diagnóstico es obligatorio.");

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setDiagnosis(diagnosis);
        appointment.setObservations(observations);
        appointment.setRecommendedTreatment(recommendedTreatment);
        appointment.setFollowUp(followUp);

        appointmentRepo.saveAll(appointments);
        return ServiceResponse.ok("Cita completada exitosamente.");
    }

    // ── Cancelar cita ─────────────────────────────────────────────────────────

    /**
     * Cancela una cita. Puede ser solicitado por paciente o doctor.
     * No se pueden cancelar citas ya COMPLETED.
     */
    public ServiceResponse cancelAppointment(String appointmentId, ArrayList<Appointment> appointments) {
        Appointment appointment = findById(appointmentId, appointments);
        if (appointment == null)
            return ServiceResponse.notFound("Cita no encontrada: " + appointmentId);
        if (appointment.getStatus() == AppointmentStatus.COMPLETED)
            return ServiceResponse.badRequest("No se puede cancelar una cita ya completada.");
        if (appointment.getStatus() == AppointmentStatus.CANCELED)
            return ServiceResponse.badRequest("La cita ya está cancelada.");

        appointment.setStatus(AppointmentStatus.CANCELED);
        appointmentRepo.saveAll(appointments);
        return ServiceResponse.ok("Cita cancelada.");
    }

    // ── Reagendar cita ────────────────────────────────────────────────────────

    /**
     * El doctor reagenda una cita (REQUESTED o PENDING) a una nueva fecha/hora.
     * Fix: crea nuevo LocalDateTime (inmutable).
     */
    public ServiceResponse rescheduleAppointment(
            String appointmentId, String newTimeStr, String reason,
            ArrayList<Appointment> appointments) {

        Appointment appointment = findById(appointmentId, appointments);
        if (appointment == null)
            return ServiceResponse.notFound("Cita no encontrada: " + appointmentId);
        if (!Validator.isValidTime(newTimeStr))
            return ServiceResponse.badRequest("Hora inválida. Formato: hh:mm — minutos: 00, 15, 30, 45");
        if (appointment.getStatus() == AppointmentStatus.COMPLETED
                || appointment.getStatus() == AppointmentStatus.CANCELED)
            return ServiceResponse.badRequest("No se puede reagendar una cita en estado " + appointment.getStatus());

        LocalTime newTime = LocalTime.parse(newTimeStr);
        // Crear nuevo LocalDateTime (LocalDateTime es inmutable — fix del bug original)
        LocalDateTime newDatetime = LocalDateTime.of(appointment.getDatetime().toLocalDate(), newTime);
        appointment.setDatetime(newDatetime);
        if (Validator.notBlank(reason)) appointment.setReason(reason);

        appointmentRepo.saveAll(appointments);
        return ServiceResponse.ok("Cita reagendada a " + newDatetime);
    }

    // ── Prescribir medicamento ────────────────────────────────────────────────

    /**
     * Agrega una prescripción a una cita completada o pendiente.
     */
    public ServiceResponse prescribe(
            String appointmentId, String medicationName, String doseStr,
            String administrationRoute, String durationStr,
            String additionalInstructions, String frecuencyStr,
            ArrayList<Appointment> appointments) {

        if (!Validator.notBlank(medicationName))
            return ServiceResponse.badRequest("Nombre de medicamento es obligatorio.");
        if (!Validator.notBlank(administrationRoute))
            return ServiceResponse.badRequest("Vía de administración es obligatoria.");

        Appointment appointment = findById(appointmentId, appointments);
        if (appointment == null)
            return ServiceResponse.notFound("Cita no encontrada: " + appointmentId);

        double dose;
        int duration, frecuency;
        try {
            dose = Double.parseDouble(doseStr);
            duration = Integer.parseInt(durationStr);
            frecuency = Integer.parseInt(frecuencyStr);
        } catch (NumberFormatException e) {
            return ServiceResponse.badRequest("Dosis, duración y frecuencia deben ser valores numéricos.");
        }

        Prescription prescription = new Prescription(
                appointment, medicationName, dose, administrationRoute,
                duration, additionalInstructions, frecuency);

        appointmentRepo.saveAll(appointments);
        return ServiceResponse.ok("Prescripción agregada.");
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    public Appointment findById(String id, ArrayList<Appointment> appointments) {
        for (Appointment a : appointments) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }

    private String generateId(ArrayList<Appointment> appointments) {
        int max = 0;
        for (Appointment a : appointments) {
            if (a.getId().startsWith("APP-")) {
                try {
                    int num = Integer.parseInt(a.getId().substring(4));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("APP-%03d", max + 1);
    }
}
