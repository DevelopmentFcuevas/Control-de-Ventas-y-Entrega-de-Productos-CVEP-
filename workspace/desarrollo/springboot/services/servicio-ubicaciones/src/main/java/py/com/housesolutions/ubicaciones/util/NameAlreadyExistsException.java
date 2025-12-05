package py.com.housesolutions.ubicaciones.util;

/**
 * Excepción personalizada para indicar que un recurso
 * con el mismo nombre ya existe en la base de datos.
 */
public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
