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
     * Retorna la lista de todos los doctores registrados.
     */
    public ArrayList<Doctor> getDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Doctor d) doctors.add(d);
        }
        return doctors;
    }

    /**
     * Retorna la lista de todos los pacientes registrados.
     */
    public ArrayList<Patient> getPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Patient p) patients.add(p);
        }
        return patients;
    }

    /**
     * Busca un doctor por ID.
     */
    public Doctor findDoctorById(long id) {
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Doctor d && d.getId() == id) return d;
        }
        return null;
    }

    /**
     * Busca un paciente por ID.
     */
    public Patient findPatientById(long id) {
        for (User u : loginCtrl.getUsers()) {
            if (u instanceof Patient p && p.getId() == id) return p;
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return loginCtrl.getUsers();
    }

    public ArrayList<Appointment> getAppointments() {
        return loginCtrl.getAppointments();
    }

    public ArrayList<Hospitalization> getHospitalizations() {
        return loginCtrl.getHospitalizations();
    }
}
