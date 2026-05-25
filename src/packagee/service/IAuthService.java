package packagee.service;

import java.util.ArrayList;
import packagee.User;
import packagee.response.ServiceResponse;

public interface IAuthService {
    ServiceResponse login(String username, String password, ArrayList<User> users);
}
