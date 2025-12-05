package py.com.housesolutions.ubicaciones.util;

/**
 * Excepción lanzada cuando ocurre un error relacionado con la base de datos
 * que no se puede clasificar como violación de integridad.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
