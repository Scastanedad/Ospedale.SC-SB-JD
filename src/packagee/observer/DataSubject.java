package packagee.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject/Observable del patrón Observer.
 * Los repositorios y servicios extienden (o delegan a) esta clase
 * para notificar a las vistas cuando los datos cambian.
 *
 * Uso:
 *   1. La vista llama subject.addObserver(this)
 *   2. El servicio llama subject.notifyObservers("APPOINTMENT")
 *   3. Automáticamente se llama view.onDataChanged("APPOINTMENT")
 *
 * Desacoplado: DataSubject no conoce Swing ni las vistas concretas.
 */
public class DataSubject {

    private final List<DataObserver> observers = new ArrayList<>();

    /**
     * Registra un observer. Las vistas se suscriben aquí al abrirse.
     */
    public void addObserver(DataObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Elimina un observer. Las vistas se desuscriben al cerrarse.
     */
    public void removeObserver(DataObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifica a todos los observers suscritos.
     * @param eventType tipo de evento: "APPOINTMENT", "HOSPITALIZATION", "USER"
     */
    public void notifyObservers(String eventType) {
        for (DataObserver observer : new ArrayList<>(observers)) {
            observer.onDataChanged(eventType);
        }
    }
}
