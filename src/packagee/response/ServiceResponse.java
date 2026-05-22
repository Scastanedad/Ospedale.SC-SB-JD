package packagee.response;

/**
 * Respuesta serializada estándar que retornan todos los servicios.
 * Las vistas NUNCA reciben modelos directamente — siempre ServiceResponse.
 *
 * statusCode:
 *   200 = éxito
 *   400 = datos inválidos / validación fallida
 *   404 = no encontrado
 *   409 = conflicto (e.g. ID o username ya existe)
 *   500 = error interno
 */
public class ServiceResponse {

    private final boolean success;
    private final String message;
    private final int statusCode;
    private final Object data;

    public ServiceResponse(boolean success, String message, int statusCode, Object data) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    // ── Constructores de fábrica ───────────────────────────────────────────────

    public static ServiceResponse ok(String message, Object data) {
        return new ServiceResponse(true, message, 200, data);
    }

    public static ServiceResponse ok(String message) {
        return new ServiceResponse(true, message, 200, null);
    }

    public static ServiceResponse badRequest(String message) {
        return new ServiceResponse(false, message, 400, null);
    }

    public static ServiceResponse notFound(String message) {
        return new ServiceResponse(false, message, 404, null);
    }

    public static ServiceResponse conflict(String message) {
        return new ServiceResponse(false, message, 409, null);
    }

    public static ServiceResponse error(String message) {
        return new ServiceResponse(false, message, 500, null);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ServiceResponse{success=" + success
                + ", statusCode=" + statusCode
                + ", message='" + message + "'}";
    }
}
