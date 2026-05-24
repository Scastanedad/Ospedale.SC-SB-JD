package packagee.service;

import packagee.*;
import packagee.response.ServiceResponse;
import packagee.util.PasswordUtil;

import java.util.ArrayList;

/**
 * Servicio de autenticación.
 *
 * Verifica credenciales usando hashing seguro (SHA-256) con soporte de
 * migración transparente desde contraseñas legacy en texto plano.
 * Retorna un ServiceResponse cuyo campo data contiene el mapa serializado
 * del usuario (HashMap<String, Object>) — compatible con LoginView y MVC.
 *
 * Principio SOLID: Responsabilidad Única (SRP) — solo autentica.
 * Principio SOLID: Inversión de Dependencias (DIP) — implementa IAuthService.
 */
public class AuthService implements IAuthService {

    /**
     * Intenta autenticar al usuario contra la lista en memoria.
     *
     * Flujo:
     *  1. Valida que username y password no estén vacíos.
     *  2. Busca el usuario por username.
     *  3. Verifica la contraseña usando PasswordUtil.verify() (soporta legacy).
     *  4. Si la contraseña era legacy (texto plano), la migra al hash SHA-256
     *     actualizando el objeto User en memoria (el llamador debe persistir).
     *  5. Retorna ServiceResponse con data = user.serialize() (HashMap).
     *
     * @param username username ingresado
     * @param password contraseña ingresada
     * @param users    lista de usuarios en memoria
     * @return ServiceResponse con data = HashMap serializado del User si éxito
     */
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
                    // Migración transparente: si la contraseña era texto plano,
                    // actualizar el objeto en memoria con el hash seguro.
                    // LoginController persiste el cambio con saveAll().
                    if (PasswordUtil.isLegacy(user.getPassword())) {
                        user.setPassword(PasswordUtil.hash(password));
                    }
                    // Retornar el mapa serializado del usuario.
                    // LoginView lo recibe como HashMap<String, Object> y lee "type"
                    // para redirigir al dashboard correcto (Admin/Doctor/Patient).
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
