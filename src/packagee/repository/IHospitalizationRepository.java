package packagee.repository;

import java.util.ArrayList;
import packagee.Hospitalization;
import packagee.User;
import packagee.observer.DataObserver;

/**
 * Contrato para el repositorio de hospitalizaciones.
 * Principio SOLID: Inversión de Dependencias (DIP).
 */
public interface IHospitalizationRepository {
    ArrayList<Hospitalization> loadAll(ArrayList<User> users);
    void saveAll(ArrayList<Hospitalization> hospitalizations);
    void addObserver(DataObserver observer);
    void removeObserver(DataObserver observer);
}
