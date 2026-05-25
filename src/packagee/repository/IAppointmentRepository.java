package packagee.repository;

import java.util.ArrayList;
import packagee.Appointment;
import packagee.User;
import packagee.observer.DataObserver;

public interface IAppointmentRepository {
    ArrayList<Appointment> loadAll(ArrayList<User> users);
    void saveAll(ArrayList<Appointment> appointments);
    void addObserver(DataObserver observer);
    void removeObserver(DataObserver observer);
}
