package packagee.repository;

import org.json.JSONArray;
import org.json.JSONObject;
import packagee.*;
import packagee.observer.DataSubject;
import packagee.util.JsonPathUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Repositorio de usuarios — carga y guarda users.json.
 *
 * Ruta JSON: json/users.json relativa al directorio de trabajo (raíz del proyecto).
 * Usa org.json incluido en lib/.
 *
 * Notifica observers al modificar datos (patrón Observer).
 */
public class UserRepository extends DataSubject implements IUserRepository {

    private static final String JSON_PATH = "json/users.json";

    // ── Singleton simple (una sola instancia en toda la app) ──────────────────
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {}

    // ── Lectura ───────────────────────────────────────────────────────────────

    /**
     * Carga todos los usuarios desde users.json.
     * @return lista de User (Administrator, Doctor, Patient)
     */
    public ArrayList<User> loadAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            String content = readFile(JSON_PATH);
            JSONObject root = new JSONObject(content);
            JSONArray arr = root.getJSONArray("users");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String type = obj.getString("type");

                switch (type) {
                    case "admin" -> users.add(parseAdmin(obj));
                    case "patient" -> users.add(parsePatient(obj));
                    case "doctor" -> users.add(parseDoctor(obj));
                }
            }
        } catch (Exception e) {
            System.err.println("[UserRepository] Error cargando users.json: " + e.getMessage());
        }
        return users;
    }

    // ── Escritura ─────────────────────────────────────────────────────────────

    /**
     * Persiste la lista completa de usuarios en users.json.
     * Llama notifyObservers("USER") al terminar.
     */
    public void saveAll(ArrayList<User> users) {
        try {
            JSONArray arr = new JSONArray();
            for (User u : users) {
                arr.put(serializeUser(u));
            }
            JSONObject root = new JSONObject();
            root.put("users", arr);
            writeFile(JSON_PATH, root.toString(2));
            notifyObservers("USER");
        } catch (Exception e) {
            System.err.println("[UserRepository] Error guardando users.json: " + e.getMessage());
        }
    }

    // ── Parsers ───────────────────────────────────────────────────────────────

    private Administrator parseAdmin(JSONObject obj) {
        return new Administrator(
                obj.getLong("id"),
                obj.getString("username"),
                obj.getString("firstname"),
                obj.getString("lastname"),
                obj.getString("password")
        );
    }

    private Patient parsePatient(JSONObject obj) {
        LocalDate birthdate = LocalDate.parse(obj.getString("birthdate"));
        return new Patient(
                obj.getLong("id"),
                obj.getString("username"),
                obj.getString("firstname"),
                obj.getString("lastname"),
                obj.getString("password"),
                obj.getString("email"),
                birthdate,
                obj.getBoolean("gender"),
                obj.getLong("phone"),
                obj.getString("address")
        );
    }

    private Doctor parseDoctor(JSONObject obj) {
        // Mapear specialty del JSON (e.g. "CARDIOLOGY") al enum
        String specStr = obj.getString("specialty");
        Specialty specialty = mapSpecialty(specStr);

        return new Doctor(
                obj.getLong("id"),
                obj.getString("username"),
                obj.getString("firstname"),
                obj.getString("lastname"),
                obj.getString("password"),
                specialty,
                obj.getString("licenceNumber"),
                obj.getString("assignedOffice")
        );
    }

    // ── Serializers ───────────────────────────────────────────────────────────

    private JSONObject serializeUser(User u) {
        JSONObject obj = new JSONObject();
        obj.put("id", u.getId());
        obj.put("username", u.getUsername());
        obj.put("firstname", u.getFirstname());
        obj.put("lastname", u.getLastname());
        obj.put("password", u.getPassword());

        if (u instanceof Administrator) {
            obj.put("type", "admin");
        } else if (u instanceof Patient p) {
            obj.put("type", "patient");
            obj.put("email", p.getEmail());
            obj.put("birthdate", p.getBirthdate().toString());
            obj.put("gender", p.isGender());
            obj.put("phone", p.getPhone());
            obj.put("address", p.getAddress());
        } else if (u instanceof Doctor d) {
            obj.put("type", "doctor");
            obj.put("specialty", d.getSpecialty().name());
            obj.put("licenceNumber", d.getLicenceNumber());
            obj.put("assignedOffice", d.getAssignedOffice());
        }
        return obj;
    }

    // ── Specialty mapping ─────────────────────────────────────────────────────

    /**
     * Mapea el string del JSON al enum Specialty.
     * Compatible con el formato existente en users.json.
     */
    private Specialty mapSpecialty(String specStr) {
        return switch (specStr.toUpperCase()) {
            case "GENERAL_MEDICINE", "GENERAL MEDICINE" -> Specialty.GENERAL_MEDICINE;
            case "CARDIOLOGY" -> Specialty.CARDIOLOGY;
            case "PEDIATRICS" -> Specialty.PEDIATRICS;
            case "NEUROLOGY" -> Specialty.NEUROLOGY;
            case "TRAUMATOLOGY_ORTHOPEDICS", "TRAUMATOLOGY & ORTHOPEDICS", "ORTHOPEDICS" -> Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY_OBSTETRICS", "GYNECOLOGY & OBSTETRICS", "GYNECOLOGY" -> Specialty.GYNECOLOGY_OBSTETRICS;
            case "DERMATOLOGY" -> Specialty.DERMATOLOGY;
            case "PSYCHIATRY" -> Specialty.PSYCHIATRY;
            case "ONCOLOGY" -> Specialty.ONCOLOGY;
            case "OPHTHALMOLOGY" -> Specialty.OPHTHALMOLOGY;
            case "INTERNAL_MEDICINE", "INTERNAL MEDICINE" -> Specialty.INTERNAL_MEDICINE;
            default -> Specialty.GENERAL_MEDICINE;
        };
    }

    // ── I/O helpers ───────────────────────────────────────────────────────────

    private String readFile(String relativePath) throws IOException {
        Path path = JsonPathUtil.resolve(relativePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private void writeFile(String relativePath, String content) throws IOException {
        Path path = JsonPathUtil.resolve(relativePath);
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
