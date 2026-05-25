package packagee.service;

import packagee.*;
import packagee.response.ServiceResponse;
import java.util.ArrayList;

public interface IAppointmentService {
    ServiceResponse createAppointment(
            Patient patient, Doctor doctor,
            String dateStr, String timeStr, String reason, String typeStr,
            ArrayList<Appointment> appointments);

    ServiceResponse acceptAppointment(String appointmentId, ArrayList<Appointment> appointments);

    ServiceResponse completeAppointment(
            String appointmentId, String diagnosis, String observations,
            String recommendedTreatment, String followUp,
            ArrayList<Appointment> appointments);

    ServiceResponse cancelAppointment(String appointmentId, ArrayList<Appointment> appointments);

    ServiceResponse rescheduleAppointment(
            String appointmentId, String newTimeStr, String reason,
            ArrayList<Appointment> appointments);

    ServiceResponse prescribe(
            String appointmentId, String medicationName, String doseStr,
            String administrationRoute, String durationStr,
            String additionalInstructions, String frecuencyStr,
            ArrayList<Appointment> appointments);

    Appointment findById(String id, ArrayList<Appointment> appointments);
}
