package packagee.repository;

import java.util.ArrayList;
import packagee.User;
import packagee.observer.DataObserver;

public interface IUserRepository {
    ArrayList<User> loadAll();
    void saveAll(ArrayList<User> users);
    void addObserver(DataObserver observer);
    void removeObserver(DataObserver observer);
}
