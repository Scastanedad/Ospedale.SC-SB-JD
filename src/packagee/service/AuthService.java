package packagee.service;

import packagee.*;
import packagee.response.ServiceResponse;
import packagee.util.PasswordUtil;

import java.util.ArrayList;

public class AuthService implements IAuthService {

    @Override
    public ServiceResponse login(String username, String password, ArrayList<User> users) {
        if (username == null || username.isBlank()) {
            return ServiceResponse.badRequest("Username no puede estar vacío.");
        }
        if (password == null || password.isBlank()) {
            return ServiceResponse.badRequest("Contraseña no puede estar vacía.");
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (PasswordUtil.verify(password, user.getPassword())) {
                    if (PasswordUtil.isLegacy(user.getPassword())) {
                        user.setPassword(PasswordUtil.hash(password));
                    }
                    return ServiceResponse.ok(
                            "Login exitoso. Bienvenido, " + user.getFirstname() + ".",
                            user.serialize()
                    );
                } else {
                    return ServiceResponse.badRequest("Contraseña incorrecta.");
                }
            }
        }
        return ServiceResponse.notFound("Usuario '" + username + "' no encontrado.");
    }
}
