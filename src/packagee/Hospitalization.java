
package packagee;

import java.time.LocalDate;

public class Hospitalization implements Serializable {
    
    private final String id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;

    public String getId() {
        return id;
    }
    private String reason;
    private RoomType roomType;
    private String observations;
    private HospitalizationStatus status;

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getObservations() {
        return observations;
    }

    public HospitalizationStatus getStatus() {
        return status;
    }

    public void setStatus(HospitalizationStatus status) {
        this.status = status;
    }

    public Hospitalization(String id, Patient patient, Doctor doctor, LocalDate date, String reason, RoomType roomType, String observations) {
        this.id = id;
        this.patient = patient;
        patient.setHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = HospitalizationStatus.REQUESTED;
    }
    public Hospitalization(String id, Patient patient, Doctor doctor, LocalDate date, String reason, RoomType roomType, String observations, HospitalizationStatus hopsS) {
        this.id = id;
        this.patient = patient;
        patient.setHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = hopsS;
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
        if(this.date != null) map.put("date", this.date.toString());
        map.put("reason", this.reason);
        if(this.roomType != null) map.put("roomType", this.roomType.name());
        map.put("observations", this.observations);
        if(this.status != null) map.put("status", this.status.name());
        return map;
    }
}
