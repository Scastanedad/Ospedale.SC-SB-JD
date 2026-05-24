/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package packagee;

/**
 * Especialidades médicas disponibles en el sistema.
 *
 * @author edangulo
 */
public enum Specialty {

    GENERAL_MEDICINE,
    CARDIOLOGY,
    PEDIATRICS,
    NEUROLOGY,
    TRAUMATOLOGY_ORTHOPEDICS,
    GYNECOLOGY_OBSTETRICS,
    DERMATOLOGY,
    PSYCHIATRY,
    ONCOLOGY,
    OPHTHALMOLOGY,
    INTERNAL_MEDICINE;

    /**
     * Convierte un String (nombre del ComboBox o nombre del enum) a Specialty.
     * Compatible con los valores de los ComboBox de las vistas existentes.
     *
     * @param specStr texto que representa la especialidad
     * @return Specialty correspondiente
     * @throws IllegalArgumentException si el texto no corresponde a ninguna especialidad
     */
    public static Specialty parse(String specStr) {
        String normalized = specStr.toUpperCase()
                .replaceAll("\\s*&\\s*", "_")
                .replaceAll("\\s+", "_");
        return switch (normalized) {
            case "GENERAL_MEDICINE"          -> Specialty.GENERAL_MEDICINE;
            case "CARDIOLOGY"                -> Specialty.CARDIOLOGY;
            case "PEDIATRICS"                -> Specialty.PEDIATRICS;
            case "NEUROLOGY"                 -> Specialty.NEUROLOGY;
            case "TRAUMATOLOGY_ORTHOPEDICS"  -> Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY_OBSTETRICS"     -> Specialty.GYNECOLOGY_OBSTETRICS;
            case "DERMATOLOGY"               -> Specialty.DERMATOLOGY;
            case "PSYCHIATRY"                -> Specialty.PSYCHIATRY;
            case "ONCOLOGY"                  -> Specialty.ONCOLOGY;
            case "OPHTHALMOLOGY"             -> Specialty.OPHTHALMOLOGY;
            case "INTERNAL_MEDICINE"         -> Specialty.INTERNAL_MEDICINE;
            default -> throw new IllegalArgumentException("Especialidad desconocida: " + specStr);
        };
    }
}
