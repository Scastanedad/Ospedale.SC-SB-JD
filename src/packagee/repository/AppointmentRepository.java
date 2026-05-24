package packagee.repository;

import org.json.JSONArray;
import org.json.JSONObject;
import packagee.*;
import packagee.observer.DataSubject;
import packagee.util.JsonPathUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Repositorio de citas — carga y guarda appointments.json.
 *
 * Las relaciones Patient/Doctor se resuelven por ID usando la lista de usuarios.
 * Se llama notifyObservers("APPOINTMENT") en cada escritura.
 */
public class AppointmentRepository extends DataSubject implements IAppointmentRepository {

    private static final String JSON_PATH = "json/appointments.json";

    private static AppointmentRepository instance;

    public static AppointmentRepository getInstance() {
        if (instance == null) {
            instance = new AppointmentRepository();
        }
        return instance;
    }

    private AppointmentRepository() {}

    // ── Carga ─────────────────────────────────────────────────────────────────

    /**
     * Carga todas las citas y resuelve relaciones con la lista de usuarios.
     * @param users lista ya cargada (para resolver patient/doctor por id)
     */
    public ArrayList<Appointment> loadAll(ArrayList<User> users) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try {
            String content = readFile(JSON_PATH);
            JSONObject root = new JSONObject(content);
            JSONArray arr = root.getJSONArray("appointments");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Appointment a = parseAppointment(obj, users);
                if (a != null) {
                    appointments.add(a);
                    // Sincronizar con listas internas del patient/doctor
                    linkToUsers(a);
                }
            }
        } catch (Exception e) {
            System.err.println("[AppointmentRepository] Error cargando appointments.json: " + e.getMessage());
        }
        return appointments;
    }

    // ── Guardado ──────────────────────────────────────────────────────────────

    /**
     * Persiste la lista completa de citas en appointments.json.
     */
    public void saveAll(ArrayList<Appointment> appointments) {
        try {
            JSONArray arr = new JSONArray();
            for (Appointment a : appointments) {
                arr.put(serializeAppointment(a));
            }
            JSONObject root = new JSONObject();
            root.put("appointments", arr);
            writeFile(JSON_PATH, root.toString(2));
            notifyObservers("APPOINTMENT");
        } catch (Exception e) {
            System.err.println("[AppointmentRepository] Error guardando appointments.json: " + e.getMessage());
        }
    }

    // ── Parser ────────────────────────────────────────────────────────────────

    private Appointment parseAppointment(JSONObject obj, ArrayList<User> users) {
        try {
            String id = obj.getString("id");
            long patientId = obj.getLong("patientId");
            long doctorId = obj.getLong("doctorId");

            Patient patient = findPatient(patientId, users);
            Doctor doctor = findDoctor(doctorId, users);
            if (patient == null || doctor == null) return null;

            Specialty specialty = Specialty.valueOf(obj.getString("specialty"));
            LocalDateTime datetime = LocalDateTime.parse(obj.getString("datetime"));
            String reason = obj.getString("reason");
            boolean type = obj.getBoolean("type");
            AppointmentStatus status = AppointmentStatus.valueOf(obj.getString("status"));

            // Usar constructor sin añadir a listas internas (lo haremos en linkToUsers)
            Appointment a = new Appointment(id, patient, doctor, specialty, datetime, reason, type);
            a.setStatus(status);

            // Campos opcionales de completado
            if (obj.has("diagnosis") && !obj.isNull("diagnosis"))
                a.setDiagnosis(obj.getString("diagnosis"));
            if (obj.has("observations") && !obj.isNull("observations"))
                a.setObservations(obj.getString("observations"));
            if (obj.has("recommendedTreatment") && !obj.isNull("recommendedTreatment"))
                a.setRecommendedTreatment(obj.getString("recommendedTreatment"));
            if (obj.has("followUp") && !obj.isNull("followUp"))
                a.setFollowUp(obj.getString("followUp"));

            return a;
        } catch (Exception e) {
            System.err.println("[AppointmentRepository] Error parseando cita: " + e.getMessage());
            return null;
        }
    }

    private void linkToUsers(Appointment a) {
        // Añadir a la lista interna del paciente solo si no está ya
        Patient p = a.getPatient();
        if (p != null && !p.getAppointments().contains(a)) {
            p.getAppointments().add(a);
        }
        // Añadir a la lista interna del doctor solo si no está ya
        Doctor d = a.getDoctor();
        if (d != null && !d.getAppointments().contains(a)) {
            d.getAppointments().add(a);
        }
    }

    // ── Serializer ────────────────────────────────────────────────────────────

    private JSONObject serializeAppointment(Appointment a) {
        JSONObject obj = new JSONObject();
        obj.put("id", a.getId());
        obj.put("patientId", a.getPatient().getId());
        obj.put("doctorId", a.getDoctor().getId());
        obj.put("specialty", a.getSpecialty().name());
        obj.put("datetime", a.getDatetime().toString());
        obj.put("reason", a.getReason() != null ? a.getReason() : "");
        obj.put("type", a.isType());
        obj.put("status", a.getStatus().name());
        obj.put("diagnosis", a.getDiagnosis() != null ? a.getDiagnosis() : JSONObject.NULL);
        obj.put("observations", a.getObservations() != null ? a.getObservations() : JSONObject.NULL);
        obj.put("recommendedTreatment", a.getRecommendedTreatment() != null ? a.getRecommendedTreatment() : JSONObject.NULL);
        obj.put("followUp", a.getFollowUp() != null ? a.getFollowUp() : JSONObject.NULL);
        return obj;
    }

    // ── Búsqueda ──────────────────────────────────────────────────────────────

    private Patient findPatient(long id, ArrayList<User> users) {
        for (User u : users) {
            if (u instanceof Patient p && p.getId() == id) return p;
        }
        return null;
    }

    private Doctor findDoctor(long id, ArrayList<User> users) {
        for (User u : users) {
            if (u instanceof Doctor d && d.getId() == id) return d;
        }
        return null;
    }

    // ── I/O ───────────────────────────────────────────────────────────────────

    private String readFile(String relativePath) throws IOException {
        Path path = JsonPathUtil.resolve(relativePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private void writeFile(String relativePath, String content) throws IOException {
        Path path = JsonPathUtil.resolve(relativePath);
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
