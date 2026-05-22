package packagee.observer;

/**
 * Interface Observer del patrón Observer (MVC).
 * Las vistas que muestran tablas implementan esta interface.
 * Al recibir update(), recargan su tabla desde el repositorio/servicio.
 *
 * Desacoplado: el Subject solo conoce DataObserver, nunca JFrame/JPanel.
 */
public interface DataObserver {
    /**
     * Llamado automáticamente por el Subject cuando los datos cambiaron.
     * @param eventType tipo de evento: "APPOINTMENT", "HOSPITALIZATION", "USER"
     */
    void onDataChanged(String eventType);
}
