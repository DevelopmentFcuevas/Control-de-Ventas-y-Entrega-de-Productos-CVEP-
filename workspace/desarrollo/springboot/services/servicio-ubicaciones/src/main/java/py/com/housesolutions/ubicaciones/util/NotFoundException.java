package py.com.housesolutions.ubicaciones.util;

/**
 * Excepción lanzada cuando un recurso solicitado no existe.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
