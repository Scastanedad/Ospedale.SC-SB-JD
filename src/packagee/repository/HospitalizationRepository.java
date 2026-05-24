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
 * Repositorio de hospitalizaciones — carga y guarda hospitalizations.json.
 *
 * Resuelve relaciones Patient/Doctor por ID.
 * Singleton. Notifica observers al guardar.
 */
public class HospitalizationRepository extends DataSubject implements IHospitalizationRepository {

    private static final String JSON_PATH = "json/hospitalizations.json";

    private static HospitalizationRepository instance;

    public static HospitalizationRepository getInstance() {
        if (instance == null) {
            instance = new HospitalizationRepository();
        }
        return instance;
    }

    private HospitalizationRepository() {}

    // ── Carga ─────────────────────────────────────────────────────────────────

    /**
     * Carga todas las hospitalizaciones y resuelve relaciones con usuarios.
     */
    public ArrayList<Hospitalization> loadAll(ArrayList<User> users) {
        ArrayList<Hospitalization> hospitalizations = new ArrayList<>();
        try {
            String content = readFile(JSON_PATH);
            JSONObject root = new JSONObject(content);
            JSONArray arr = root.getJSONArray("hospitalizations");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Hospitalization h = parseHospitalization(obj, users);
                if (h != null) {
                    hospitalizations.add(h);
                }
            }
        } catch (Exception e) {
            System.err.println("[HospitalizationRepository] Error cargando hospitalizations.json: " + e.getMessage());
        }
        return hospitalizations;
    }

    // ── Guardado ──────────────────────────────────────────────────────────────

    /**
     * Persiste la lista completa en hospitalizations.json.
     */
    public void saveAll(ArrayList<Hospitalization> hospitalizations) {
        try {
            JSONArray arr = new JSONArray();
            for (Hospitalization h : hospitalizations) {
                arr.put(serializeHospitalization(h));
            }
            JSONObject root = new JSONObject();
            root.put("hospitalizations", arr);
            writeFile(JSON_PATH, root.toString(2));
            notifyObservers("HOSPITALIZATION");
        } catch (Exception e) {
            System.err.println("[HospitalizationRepository] Error guardando hospitalizations.json: " + e.getMessage());
        }
    }

    // ── Parser ────────────────────────────────────────────────────────────────

    private Hospitalization parseHospitalization(JSONObject obj, ArrayList<User> users) {
        try {
            String id = obj.getString("id");
            long patientId = obj.getLong("patientId");
            long doctorId = obj.getLong("doctorId");

            Patient patient = findPatient(patientId, users);
            Doctor doctor = findDoctor(doctorId, users);
            if (patient == null || doctor == null) return null;

            LocalDate date = LocalDate.parse(obj.getString("date"));
            String reason = obj.getString("reason");
            RoomType roomType = RoomType.valueOf(obj.getString("roomType"));
            String observations = obj.optString("observations", "");
            HospitalizationStatus status = HospitalizationStatus.valueOf(obj.getString("status"));

            return new Hospitalization(id, patient, doctor, date, reason, roomType, observations, status);
        } catch (Exception e) {
            System.err.println("[HospitalizationRepository] Error parseando hospitalización: " + e.getMessage());
            return null;
        }
    }

    // ── Serializer ────────────────────────────────────────────────────────────

    private JSONObject serializeHospitalization(Hospitalization h) {
        JSONObject obj = new JSONObject();
        obj.put("id", h.getId());
        obj.put("patientId", h.getPatient().getId());
        obj.put("doctorId", h.getDoctor().getId());
        obj.put("date", h.getDate() != null ? h.getDate().toString() : LocalDate.now().toString());
        obj.put("reason", h.getReason() != null ? h.getReason() : "");
        obj.put("roomType", h.getRoomType().name());
        obj.put("observations", h.getObservations() != null ? h.getObservations() : "");
        obj.put("status", h.getStatus().name());
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
