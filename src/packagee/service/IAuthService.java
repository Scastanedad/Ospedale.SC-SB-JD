package packagee.service;

import java.util.ArrayList;
import packagee.User;
import packagee.response.ServiceResponse;

/**
 * Contrato para el servicio de autenticación.
 * Principio SOLID: Inversión de Dependencias (DIP).
 */
public interface IAuthService {
    ServiceResponse login(String username, String password, ArrayList<User> users);
}
