package packagee.controller;

import packagee.*;
import packagee.response.ServiceResponse;
import java.util.ArrayList;

public class DoctorController {

    private final LoginController loginCtrl;

    public DoctorController() {
        this.loginCtrl = LoginController.getInstance();
    }

    public ServiceResponse updateDoctor(
            long doctorId, String firstname, String lastname,
            String specialtyStr, String licenseNumber, String assignedOffice,
            String username, String password, String confirm) {
        Doctor doctor = loginCtrl.findDoctorById(doctorId);
        return loginCtrl.getUserService().updateDoctor(
                doctor, firstname, lastname, specialtyStr, licenseNumber, assignedOffice,
                username, password, confirm, loginCtrl.getUsers());
    }

    public ServiceResponse acceptAppointment(String appointmentId) {
        return loginCtrl.getAppointmentService().acceptAppointment(
                appointmentId, loginCtrl.getAppointments());
    }

    public ServiceResponse completeAppointment(
            String appointmentId, String diagnosis, String observations,
            String recommendedTreatment, String followUp) {
        return loginCtrl.getAppointmentService().completeAppointment(
                appointmentId, diagnosis, observations, recommendedTreatment, followUp,
                loginCtrl.getAppointments());
    }

    public ServiceResponse cancelAppointment(String appointmentId) {
        return loginCtrl.getAppointmentService().cancelAppointment(
                appointmentId, loginCtrl.getAppointments());
    }

    public ServiceResponse rescheduleAppointment(String appointmentId, String newTimeStr, String reason) {
        return loginCtrl.getAppointmentService().rescheduleAppointment(
                appointmentId, newTimeStr, reason, loginCtrl.getAppointments());
    }

    public ServiceResponse prescribe(
            String appointmentId, String medicationName, String doseStr,
            String administrationRoute, String durationStr,
            String additionalInstructions, String frecuencyStr) {
        return loginCtrl.getAppointmentService().prescribe(
                appointmentId, medicationName, doseStr, administrationRoute,
                durationStr, additionalInstructions, frecuencyStr,
                loginCtrl.getAppointments());
    }

    public ServiceResponse createHospitalization(
            long patientId, long doctorId,
            String dateStr, String reason, String roomTypeStr, String observations) {
        Patient patient = loginCtrl.findPatientById(patientId);
        Doctor doctor = loginCtrl.findDoctorById(doctorId);
        return loginCtrl.getHospitalizationService().createHospitalization(
                patient, doctor, dateStr, reason, roomTypeStr, observations,
                loginCtrl.getHospitalizations());
    }

    public ServiceResponse cancelHospitalization(String hospitalizationId) {
        return loginCtrl.getHospitalizationService().cancelHospitalization(
                hospitalizationId, loginCtrl.getHospitalizations());
    }

    public java.util.HashMap<String, Object> findPatientDataById(long id) {
        Patient pat = loginCtrl.findPatientById(id);
        if(pat != null) return pat.serialize();
        return null;
    }

    public ArrayList<java.util.HashMap<String, Object>> getSerializedAllPatients() {
        ArrayList<java.util.HashMap<String, Object>> patients = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Patient p) patients.add(p.serialize());
        }
        return patients;
    }

    public ArrayList<java.util.HashMap<String, Object>> getSerializedAppointments() {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        for (Appointment a : loginCtrl.getAppointments()) {
            list.add(a.serialize());
        }
        return list;
    }

    public ArrayList<java.util.HashMap<String, Object>> getSerializedHospitalizations() {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        for (Hospitalization h : loginCtrl.getHospitalizations()) {
            list.add(h.serialize());
        }
        return list;
    }

    public ArrayList<java.util.HashMap<String, Object>> getSerializedUsers() {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            list.add(u.serialize());
        }
        return list;
    }

    public ArrayList<java.util.HashMap<String, Object>> getDoctorAppointments(long doctorId) {
        Doctor doc = loginCtrl.findDoctorById(doctorId);
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        if (doc != null) {
            for (Appointment a : doc.getAppointments()) {
                list.add(a.serialize());
            }
        }
        return list;
    }

    public ArrayList<java.util.HashMap<String, Object>> getDoctorHospitalizations(long doctorId) {
        Doctor doc = loginCtrl.findDoctorById(doctorId);
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        if (doc != null) {
            for (Hospitalization h : doc.getHospitalizations()) {
                list.add(h.serialize());
            }
        }
        return list;
    }

    public ArrayList<java.util.HashMap<String, Object>> getPatientAppointments(long patientId) {
        Patient pat = loginCtrl.findPatientById(patientId);
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        if(pat != null) {
            for (Appointment a : pat.getAppointments()) {
                list.add(a.serialize());
            }
        }
        return list;
    }
}
