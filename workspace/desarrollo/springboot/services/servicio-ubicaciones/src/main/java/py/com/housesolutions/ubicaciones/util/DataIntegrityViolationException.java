package py.com.housesolutions.ubicaciones.util;

/**
 * Excepción lanzada cuando se produce una violación de integridad referencial
 * o de restricciones de base de datos (FK, unique, etc.).
 */
public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
