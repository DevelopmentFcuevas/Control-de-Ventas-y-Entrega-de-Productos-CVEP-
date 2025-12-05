package py.com.housesolutions.ubicaciones.model;

/**
 * Representa el estado de un país dentro del sistema.
 * Un país puede estar activo o inactivo (soft-delete).
 */
public enum Estado {
    /** El país está disponible y visible para consultas. */
    ACTIVO,

    /** El país fue deshabilitado mediante soft-delete. */
    INACTIVO
}
