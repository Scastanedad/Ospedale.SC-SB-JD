
package packagee;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Appointment implements Serializable {

    private final String id;
    private Patient patient;
    private Doctor doctor;
    private Specialty specialty;
    private LocalDateTime datetime;
    private String reason;
    private boolean type;
    private ArrayList<Prescription> prescriptions;
    private AppointmentStatus status;
    private String diagnosis;
    private String observations;
    private String recommendedTreatment;
    private String followUp;

    public Appointment(String id, Patient patient, Doctor doctor, Specialty specialty, LocalDateTime datetime, String reason, boolean type) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.specialty = specialty;
        this.datetime = datetime;
        this.reason = reason;
        this.type = type;
        this.status = AppointmentStatus.REQUESTED;
        this.prescriptions = new ArrayList<>();
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setRecommendedTreatment(String recommendedTreatment) {
        this.recommendedTreatment = recommendedTreatment;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public boolean isType() {
        return type;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getObservations() {
        return observations;
    }

    public String getRecommendedTreatment() {
        return recommendedTreatment;
    }

    public String getFollowUp() {
        return followUp;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public boolean addPrescription(Prescription prescrip) {
        return this.prescriptions.add(prescrip);
    }

    @Override
    public java.util.HashMap<String, Object> serialize() {
        java.util.HashMap<String, Object> map = new java.util.HashMap<>();
        map.put("id", this.id);
        if(this.patient != null) {
            map.put("patientId", String.valueOf(this.patient.getId()));
            map.put("patientName", this.patient.getFirstname() + " " + this.patient.getLastname());
        }
        if(this.doctor != null) {
            map.put("doctorId", String.valueOf(this.doctor.getId()));
            map.put("doctorName", this.doctor.getFirstname() + " " + this.doctor.getLastname());
        }
        if(this.specialty != null) map.put("specialty", this.specialty.name());
        if(this.datetime != null) map.put("datetime", this.datetime.toString());
        map.put("reason", this.reason);
        map.put("type", this.type ? "In person" : "Virtual");
        if(this.status != null) map.put("status", this.status.name());
        map.put("diagnosis", this.diagnosis);
        map.put("observations", this.observations);
        map.put("recommendedTreatment", this.recommendedTreatment);
        map.put("followUp", this.followUp);
        return map;
    }

}
