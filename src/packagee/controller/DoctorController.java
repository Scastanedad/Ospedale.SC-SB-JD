package packagee.controller;

import packagee.*;
import packagee.response.ServiceResponse;

import java.util.ArrayList;

/**
 * Controlador del panel de Doctor.
 * Coordina: actualizar info, aceptar/completar/reagendar/cancelar citas,
 * prescribir medicamentos y gestionar hospitalizaciones.
 */
public class DoctorController {

    private final LoginController loginCtrl;

    public DoctorController() {
        this.loginCtrl = LoginController.getInstance();
    }

    /**
     * Actualiza datos del doctor actual.
     */
    public ServiceResponse updateDoctor(
            Doctor doctor, String firstname, String lastname,
            String specialtyStr, String licenseNumber, String assignedOffice,
            String username, String password, String confirm) {
        return loginCtrl.getUserService().updateDoctor(
                doctor, firstname, lastname, specialtyStr, licenseNumber, assignedOffice,
                username, password, confirm, loginCtrl.getUsers());
    }

    /**
     * Acepta una cita (REQUESTED → PENDING).
     */
    public ServiceResponse acceptAppointment(String appointmentId) {
        return loginCtrl.getAppointmentService().acceptAppointment(
                appointmentId, loginCtrl.getAppointments());
    }

    /**
     * Completa una cita (PENDING → COMPLETED). Fix del bug original.
     */
    public ServiceResponse completeAppointment(
            String appointmentId, String diagnosis, String observations,
            String recommendedTreatment, String followUp) {
        return loginCtrl.getAppointmentService().completeAppointment(
                appointmentId, diagnosis, observations, recommendedTreatment, followUp,
                loginCtrl.getAppointments());
    }

    /**
     * Cancela una cita.
     */
    public ServiceResponse cancelAppointment(String appointmentId) {
        return loginCtrl.getAppointmentService().cancelAppointment(
                appointmentId, loginCtrl.getAppointments());
    }

    /**
     * Reagenda una cita a nueva hora. Fix del bug de inmutabilidad LocalDateTime.
     */
    public ServiceResponse rescheduleAppointment(String appointmentId, String newTimeStr, String reason) {
        return loginCtrl.getAppointmentService().rescheduleAppointment(
                appointmentId, newTimeStr, reason, loginCtrl.getAppointments());
    }

    /**
     * Prescribe un medicamento para una cita.
     */
    public ServiceResponse prescribe(
            String appointmentId, String medicationName, String doseStr,
            String administrationRoute, String durationStr,
            String additionalInstructions, String frecuencyStr) {
        return loginCtrl.getAppointmentService().prescribe(
                appointmentId, medicationName, doseStr, administrationRoute,
                durationStr, additionalInstructions, frecuencyStr,
                loginCtrl.getAppointments());
    }

    /**
     * Crea una hospitalización para un paciente.
     */
    public ServiceResponse createHospitalization(
            Patient patient, Doctor doctor,
            String dateStr, String reason, String roomTypeStr, String observations) {
        return loginCtrl.getHospitalizationService().createHospitalization(
                patient, doctor, dateStr, reason, roomTypeStr, observations,
                loginCtrl.getHospitalizations());
    }

    /**
     * Cancela una hospitalización.
     */
    public ServiceResponse cancelHospitalization(String hospitalizationId) {
        return loginCtrl.getHospitalizationService().cancelHospitalization(
                hospitalizationId, loginCtrl.getHospitalizations());
    }

    /**
     * Busca paciente por ID (para combo de pacientes).
     */
    public Patient findPatientById(long id) {
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Patient p && p.getId() == id) return p;
        }
        return null;
    }

    /**
     * Retorna todos los pacientes (para ComboBox).
     */
    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Patient p) patients.add(p);
        }
        return patients;
    }

    public ArrayList<Appointment> getAppointments() {
        return loginCtrl.getAppointments();
    }

    public ArrayList<Hospitalization> getHospitalizations() {
        return loginCtrl.getHospitalizations();
    }

    public ArrayList<User> getUsers() {
        return loginCtrl.getUsers();
    }
}
