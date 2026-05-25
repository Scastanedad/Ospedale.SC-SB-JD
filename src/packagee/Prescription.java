
package packagee;

public class Prescription implements Serializable {
    private Appointment appointment;
    private String medicationName;
    private double dose;
    private String administrationRoute;
    private int treatmentDuration;
    private String additionalInstructions;
    private int frecuency;

    public Prescription(Appointment appointment, String medicationName, double dose, String administrationRoute, int treatmentDuration, String additionalInstructions, int frecuency) {
        this.appointment = appointment;
        appointment.addPrescription(this);
        this.medicationName = medicationName;
        this.dose = dose;
        this.administrationRoute = administrationRoute;
        this.treatmentDuration = treatmentDuration;
        this.additionalInstructions = additionalInstructions;
        this.frecuency = frecuency;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public double getDose() {
        return dose;
    }

    public String getAdministrationRoute() {
        return administrationRoute;
    }

    public int getTreatmentDuration() {
        return treatmentDuration;
    }

    public String getAdditionalInstructions() {
        return additionalInstructions;
    }

    public int getFrecuency() {
        return frecuency;
    }
    
    @Override
    public java.util.HashMap<String, Object> serialize() {
        java.util.HashMap<String, Object> map = new java.util.HashMap<>();
        if(this.appointment != null) map.put("appointmentId", this.appointment.getId());
        map.put("medicationName", this.medicationName);
        map.put("dose", String.valueOf(this.dose));
        map.put("administrationRoute", this.administrationRoute);
        map.put("treatmentDuration", String.valueOf(this.treatmentDuration));
        map.put("additionalInstructions", this.additionalInstructions);
        map.put("frecuency", String.valueOf(this.frecuency));
        return map;
    }
}
