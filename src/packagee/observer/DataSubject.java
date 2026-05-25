package packagee.observer;

import java.util.ArrayList;
import java.util.List;

public class DataSubject {

    private final List<DataObserver> observers = new ArrayList<>();

    public void addObserver(DataObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(DataObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String eventType) {
        for (DataObserver observer : new ArrayList<>(observers)) {
            observer.onDataChanged(eventType);
        }
    }
}
