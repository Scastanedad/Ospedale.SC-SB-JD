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
            long patientId, String firstname, String lastname,
            String email, String birthdateStr, String genderStr,
            String phoneStr, String address, String username,
            String password, String confirm) {
        Patient patient = loginCtrl.findPatientById(patientId);
        if (patient == null) {
            return packagee.response.ServiceResponse.notFound(
                    "Paciente no encontrado con ID: " + patientId);
        }
        return loginCtrl.getUserService().updatePatient(
                patient, firstname, lastname, email, birthdateStr, genderStr,
                phoneStr, address, username, password, confirm,
                loginCtrl.getUsers());
    }

    /**
     * Solicita una cita médica para el paciente.
     */
    public ServiceResponse requestAppointment(
            long patientId, long doctorId,
            String dateStr, String timeStr, String reason, String typeStr) {
        Patient patient = loginCtrl.findPatientById(patientId);
        Doctor doctor = loginCtrl.findDoctorById(doctorId);
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
            long patientId, long doctorId,
            String dateStr, String reason, String roomTypeStr, String observations) {
        Patient patient = loginCtrl.findPatientById(patientId);
        Doctor doctor = loginCtrl.findDoctorById(doctorId);
        return loginCtrl.getHospitalizationService().createHospitalization(
                patient, doctor, dateStr, reason, roomTypeStr, observations,
                loginCtrl.getHospitalizations());
    }

    /**
     * Retorna todas las citas del paciente de forma serializada.
     */
    public ArrayList<java.util.HashMap<String, Object>> getSerializedPatientAppointments(long patientId) {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        Patient patient = loginCtrl.findPatientById(patientId);
        if(patient != null) {
            for (Appointment a : patient.getAppointments()) {
                list.add(a.serialize());
            }
        }
        return list;
    }

    /**
     * Retorna la lista de todos los doctores serializada (para poblar ComboBox).
     */
    public ArrayList<java.util.HashMap<String, Object>> getSerializedAllDoctors() {
        ArrayList<java.util.HashMap<String, Object>> doctors = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Doctor d) doctors.add(d.serialize());
        }
        return doctors;
    }

    /**
     * Busca un doctor por ID y devuelve datos serializados.
     */
    public java.util.HashMap<String, Object> findDoctorDataById(long id) {
        Doctor doc = loginCtrl.findDoctorById(id);
        if (doc != null) return doc.serialize();
        return null;
    }

    /**
     * Busca un paciente por ID y devuelve datos serializados.
     */
    public java.util.HashMap<String, Object> findPatientDataById(long id) {
        Patient pat = loginCtrl.findPatientById(id);
        if (pat != null) return pat.serialize();
        return null;
    }

    public ArrayList<java.util.HashMap<String, Object>> getSerializedAppointments() {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        for (Appointment a : loginCtrl.getAppointments()) {
            list.add(a.serialize());
        }
        return list;
    }
}
