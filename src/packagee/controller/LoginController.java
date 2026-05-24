package packagee.controller;

import packagee.*;
import packagee.repository.*;
import packagee.response.ServiceResponse;
import packagee.service.*;

import java.util.ArrayList;

/**
 * Controlador de Login.
 * Punto de entrada de la aplicación.
 * Carga JSON, crea instancias de servicios y coordina la autenticación.
 *
 * Las vistas NO instancian repositorios ni servicios directamente.
 * Solo llaman métodos del controlador y leen ServiceResponse.
 */
public class LoginController {

    // ── Repositorios (singletons) ──────────────────────────────────────────────
    private final IUserRepository userRepo;
    private final IAppointmentRepository appointmentRepo;
    private final IHospitalizationRepository hospitalizationRepo;

    // ── Datos en memoria ───────────────────────────────────────────────────────
    private ArrayList<User> users;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;

    // ── Servicios ──────────────────────────────────────────────────────────────
    private final IAuthService authService;
    private final IUserService userService;
    private final IAppointmentService appointmentService;
    private final IHospitalizationService hospitalizationService;

    // ── Singleton del controlador ─────────────────────────────────────────────
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

        // Cargar datos desde JSON al iniciar
        loadData();
    }

    /**
     * Carga todos los datos desde JSON.
     * Orden: primero usuarios (dependencia), luego citas y hospitalizaciones.
     */
    public void loadData() {
        this.users = userRepo.loadAll();
        this.appointments = appointmentRepo.loadAll(users);
        this.hospitalizations = hospitalizationRepo.loadAll(users);
    }

    // ── Login ──────────────────────────────────────────────────────────────────

    /**
     * Autenticar usuario.
     * @param username nombre de usuario
     * @param password contraseña
     * @return ServiceResponse con data = User autenticado si éxito
     */
    public ServiceResponse login(String username, String password) {
        ServiceResponse response = authService.login(username, password, users);
        if (response.isSuccess() && response.getData() instanceof User) {
            User u = (User) response.getData();
            return ServiceResponse.ok(response.getMessage(), u.serialize());
        }
        return response;
    }

    public java.util.HashMap<String, Object> findUserDataById(long id) {
        User u = findUserById(id);
        if (u != null) return u.serialize();
        return null;
    }

    // ── Registro de paciente (desde LoginView) ────────────────────────────────

    public ServiceResponse registerPatient(
            String idStr, String username, String firstname, String lastname,
            String password, String confirm, String email,
            String birthdateStr, String genderStr, String phoneStr, String address) {
        return userService.registerPatient(
                idStr, username, firstname, lastname,
                password, confirm, email, birthdateStr, genderStr, phoneStr, address,
                users);
    }

    // ── Acceso a datos compartidos ────────────────────────────────────────────
    // Los demás controladores acceden a estas listas vía LoginController

    public ArrayList<User> getUsers() { return users; }
    public ArrayList<Appointment> getAppointments() { return appointments; }
    public ArrayList<Hospitalization> getHospitalizations() { return hospitalizations; }
    public IUserRepository getUserRepo() { return userRepo; }
    public IAppointmentRepository getAppointmentRepo() { return appointmentRepo; }
    public IHospitalizationRepository getHospitalizationRepo() { return hospitalizationRepo; }
    public IUserService getUserService() { return userService; }
    public IAppointmentService getAppointmentService() { return appointmentService; }
    public IHospitalizationService getHospitalizationService() { return hospitalizationService; }

    // ── Búsqueda centralizada de usuarios ────────────────────────────────────
    // Punto único de búsqueda para evitar duplicación en los controladores hijos.

    /**
     * Busca cualquier tipo de usuario (Admin, Doctor, Patient) por su ID.
     */
    public User findUserById(long id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    /**
     * Busca un doctor por ID numérico entre todos los usuarios cargados.
     * @param id identificador numérico del doctor
     * @return Doctor encontrado, o null si no existe
     */
    public Doctor findDoctorById(long id) {
        for (User u : users) {
            if (u instanceof Doctor d && d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    /**
     * Busca un paciente por ID numérico entre todos los usuarios cargados.
     * @param id identificador numérico del paciente
     * @return Patient encontrado, o null si no existe
     */
    public Patient findPatientById(long id) {
        for (User u : users) {
            if (u instanceof Patient p && p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}


