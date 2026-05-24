package packagee.service;

import packagee.*;
import packagee.response.ServiceResponse;

import java.util.ArrayList;

/**
 * Servicio de autenticación.
 * Valida credenciales y retorna respuesta serializada con el usuario encontrado.
 * Las vistas NUNCA reciben el User directamente — reciben ServiceResponse.
 */
public class AuthService implements IAuthService {

    /**
     * Intenta autenticar al usuario.
     *
     * @param username username ingresado
     * @param password contraseña ingresada
     * @param users    lista de usuarios en memoria
     * @return ServiceResponse con data = User encontrado, o error
     */
    public ServiceResponse login(String username, String password, ArrayList<User> users) {
        if (username == null || username.isBlank()) {
            return ServiceResponse.badRequest("Username no puede estar vacío.");
        }
        if (password == null || password.isBlank()) {
            return ServiceResponse.badRequest("Contraseña no puede estar vacía.");
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    String role = resolveRole(user);
                    return ServiceResponse.ok("Login exitoso. Bienvenido, " + user.getFirstname() + ".", user.getId());
                } else {
                    return ServiceResponse.badRequest("Contraseña incorrecta.");
                }
            }
        }
        return ServiceResponse.notFound("Usuario '" + username + "' no encontrado.");
    }

    private String resolveRole(User user) {
        if (user instanceof Administrator) return "admin";
        if (user instanceof Doctor) return "doctor";
        return "patient";
    }
}
