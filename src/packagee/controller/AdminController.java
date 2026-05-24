package packagee.controller;

import packagee.*;
import packagee.response.ServiceResponse;

import java.util.ArrayList;

/**
 * Controlador del panel de Administrador.
 * Coordina registro de doctores, navegación a vistas de doctor/paciente.
 * Obtiene datos del LoginController singleton.
 */
public class AdminController {

    private final LoginController loginCtrl;

    public AdminController() {
        this.loginCtrl = LoginController.getInstance();
    }

    /**
     * Registrar un nuevo doctor.
     */
    public ServiceResponse registerDoctor(
            String idStr, String username, String firstname, String lastname,
            String password, String confirm, String specialtyStr,
            String licenseNumber, String assignedOffice) {
        return loginCtrl.getUserService().registerDoctor(
                idStr, username, firstname, lastname,
                password, confirm, specialtyStr, licenseNumber, assignedOffice,
                loginCtrl.getUsers());
    }

    /**
     * Retorna la lista serializada de todos los doctores.
     */
    public ArrayList<java.util.HashMap<String, Object>> getSerializedDoctors() {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Doctor d) list.add(d.serialize());
        }
        return list;
    }

    /**
     * Retorna la lista serializada de todos los pacientes.
     */
    public ArrayList<java.util.HashMap<String, Object>> getSerializedPatients() {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Patient p) list.add(p.serialize());
        }
        return list;
    }

    /**
     * Busca un doctor por ID (ahora retorna mapa serializado).
     */
    public java.util.HashMap<String, Object> findDoctorDataById(long id) {
        Doctor doc = loginCtrl.findDoctorById(id);
        if (doc != null) return doc.serialize();
        return null;
    }

    /**
     * Busca un paciente por ID (ahora retorna mapa serializado).
     */
    public java.util.HashMap<String, Object> findPatientDataById(long id) {
        Patient pat = loginCtrl.findPatientById(id);
        if (pat != null) return pat.serialize();
        return null;
    }

    public ArrayList<java.util.HashMap<String, Object>> getSerializedUsers() {
        ArrayList<java.util.HashMap<String, Object>> list = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            list.add(u.serialize());
        }
        return list;
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
}
