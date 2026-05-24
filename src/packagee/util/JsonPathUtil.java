package packagee.util;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utilidad para resolución de rutas de archivos JSON.
 *
 * Resuelve rutas relativas (ej. "json/users.json") de forma portable:
 *   1. Relativo al directorio de trabajo (CWD) — funciona en NetBeans/IntelliJ.
 *   2. Relativo al directorio del JAR — funciona al distribuir la aplicación.
 *
 * Principio SOLID: Responsabilidad Única (SRP) — solo resuelve rutas.
 * Clase final con constructor privado — no instanciable (utility class).
 */
public final class JsonPathUtil {

    // Prevenir instanciación
    private JsonPathUtil() {}

    /**
     * Resuelve una ruta relativa a una ruta absoluta portable.
     *
     * Estrategia de resolución:
     *  1. Verifica si el directorio padre de la ruta existe relativo al CWD.
     *     Esto funciona cuando el CWD es la raíz del proyecto (IDE estándar).
     *  2. Si no, resuelve relativo al directorio del JAR/clases compiladas.
     *     Esto funciona al ejecutar el JAR distribuido desde cualquier directorio.
     *
     * @param relativePath ruta relativa, ej. "json/users.json"
     * @return Path absoluto resuelto
     */
    public static Path resolve(String relativePath) {
        // ── Estrategia 1: relativo al CWD ────────────────────────────────────
        // Funciona en NetBeans e IntelliJ cuando el proyecto es el working dir.
        Path cwdPath = Paths.get(relativePath);
        Path parentDir = cwdPath.getParent();
        if (parentDir != null && Files.isDirectory(parentDir.toAbsolutePath())) {
            return cwdPath.toAbsolutePath();
        }

        // ── Estrategia 2: relativo al JAR / directorio de clases ─────────────
        // Funciona cuando se ejecuta el JAR distribuido desde otro directorio.
        try {
            URI codeUri = JsonPathUtil.class
                    .getProtectionDomain().getCodeSource().getLocation().toURI();
            Path codePath = Paths.get(codeUri);
            // Si es un JAR, su padre es el directorio de distribución.
            // Si es un directorio de clases (IDE), también usamos el padre.
            Path baseDir = Files.isDirectory(codePath) ? codePath : codePath.getParent();
            return baseDir.resolve(relativePath).toAbsolutePath();
        } catch (Exception e) {
            // Fallback: CWD — el error se reportará al intentar leer/escribir
            return Paths.get(relativePath).toAbsolutePath();
        }
    }
}
