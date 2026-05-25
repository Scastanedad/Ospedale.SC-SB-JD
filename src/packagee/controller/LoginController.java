package packagee.controller;

import packagee.*;
import packagee.repository.*;
import packagee.response.ServiceResponse;
import packagee.service.*;

import java.util.ArrayList;

public class LoginController {

    private final IUserRepository userRepo;
    private final IAppointmentRepository appointmentRepo;
    private final IHospitalizationRepository hospitalizationRepo;

    private ArrayList<User> users;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;

    private final IAuthService authService;
    private final IUserService userService;
    private final IAppointmentService appointmentService;
    private final IHospitalizationService hospitalizationService;

    private static LoginController instance;

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    private LoginController() {
        this.userRepo = UserRepository.getInstance();
        this.appointmentRepo = AppointmentRepository.getInstance();
        this.hospitalizationRepo = HospitalizationRepository.getInstance();

        this.authService = new AuthService();
        this.userService = new UserService(userRepo);
        this.appointmentService = new AppointmentService(appointmentRepo);
        this.hospitalizationService = new HospitalizationService(hospitalizationRepo);

        loadData();
    }

    public void loadData() {
        this.users = userRepo.loadAll();
        this.appointments = appointmentRepo.loadAll(users);
        this.hospitalizations = hospitalizationRepo.loadAll(users);
    }

    public ServiceResponse login(String username, String password) {
        ServiceResponse response = authService.login(username, password, users);
        if (response.isSuccess()) {
            userRepo.saveAll(users);
        }
        return response;
    }

    public java.util.HashMap<String, Object> findUserDataById(long id) {
        User u = findUserById(id);
        if (u != null) return u.serialize();
        return null;
    }

    public ServiceResponse registerPatient(
            String idStr, String username, String firstname, String lastname,
            String password, String confirm, String email,
            String birthdateStr, String genderStr, String phoneStr, String address) {
        return userService.registerPatient(
                idStr, username, firstname, lastname,
                password, confirm, email, birthdateStr, genderStr, phoneStr, address,
                users);
    }

    public ArrayList<User> getUsers() { return users; }
    public ArrayList<Appointment> getAppointments() { return appointments; }
    public ArrayList<Hospitalization> getHospitalizations() { return hospitalizations; }
    public IUserRepository getUserRepo() { return userRepo; }
    public IAppointmentRepository getAppointmentRepo() { return appointmentRepo; }
    public IHospitalizationRepository getHospitalizationRepo() { return hospitalizationRepo; }
    public IUserService getUserService() { return userService; }
    public IAppointmentService getAppointmentService() { return appointmentService; }
    public IHospitalizationService getHospitalizationService() { return hospitalizationService; }

    public User findUserById(long id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public Doctor findDoctorById(long id) {
        for (User u : users) {
            if (u instanceof Doctor d && d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    public Patient findPatientById(long id) {
        for (User u : users) {
            if (u instanceof Patient p && p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}

