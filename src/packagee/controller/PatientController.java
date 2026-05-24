package packagee.controller;

import packagee.*;
import packagee.response.ServiceResponse;

import java.util.ArrayList;

/**
 * Controlador del panel de Paciente.
 * Coordina actualización de datos, solicitar/cancelar citas y hospitalizaciones.
 */
public class PatientController {

    private final LoginController loginCtrl;

    public PatientController() {
        this.loginCtrl = LoginController.getInstance();
    }

    /**
     * Actualiza datos del paciente actual.
     */
    public ServiceResponse updatePatient(
            Patient patient, String firstname, String lastname,
            String email, String birthdateStr, String genderStr,
            String phoneStr, String address, String username,
            String password, String confirm) {
        return loginCtrl.getUserService().updatePatient(
                patient, firstname, lastname, email, birthdateStr, genderStr,
                phoneStr, address, username, password, confirm,
                loginCtrl.getUsers());
    }

    /**
     * Solicita una cita médica para el paciente.
     */
    public ServiceResponse requestAppointment(
            Patient patient, Doctor doctor,
            String dateStr, String timeStr, String reason, String typeStr) {
        return loginCtrl.getAppointmentService().createAppointment(
                patient, doctor, dateStr, timeStr, reason, typeStr,
                loginCtrl.getAppointments());
    }

    /**
     * Cancela una cita del paciente.
     */
    public ServiceResponse cancelAppointment(String appointmentId) {
        return loginCtrl.getAppointmentService().cancelAppointment(
                appointmentId, loginCtrl.getAppointments());
    }

    /**
     * Solicita una hospitalización para el paciente.
     */
    public ServiceResponse requestHospitalization(
            Patient patient, Doctor doctor,
            String dateStr, String reason, String roomTypeStr, String observations) {
        return loginCtrl.getHospitalizationService().createHospitalization(
                patient, doctor, dateStr, reason, roomTypeStr, observations,
                loginCtrl.getHospitalizations());
    }

    /**
     * Retorna todas las citas del paciente.
     */
    public ArrayList<Appointment> getPatientAppointments(Patient patient) {
        return patient.getAppointments();
    }

    /**
     * Retorna la lista de todos los doctores (para poblar ComboBox).
     */
    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Doctor d) doctors.add(d);
        }
        return doctors;
    }

    /**
     * Busca un doctor por ID. Delega al LoginController centralizado.
     */
    public Doctor findDoctorById(long id) {
        return loginCtrl.findDoctorById(id);
    }

    public ArrayList<Appointment> getAppointments() {
        return loginCtrl.getAppointments();
    }
}
