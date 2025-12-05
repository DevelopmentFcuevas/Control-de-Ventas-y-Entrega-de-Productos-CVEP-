package py.com.housesolutions.ubicaciones.model;

/**
 * Enumeración que representa acciones de auditoría
 * utilizadas en el historial de cambios del sistema.
 */
public enum Action {
    /** Acción de creación de un registro. */
    CREATE,

    /** Acción de actualización. */
    UPDATE,

    /** Acción de eliminación lógica (soft-delete). */
    DELETE,

    /** Acción de reactivación de un registro previamente inactivo. */
    REACTIVATED,
}
