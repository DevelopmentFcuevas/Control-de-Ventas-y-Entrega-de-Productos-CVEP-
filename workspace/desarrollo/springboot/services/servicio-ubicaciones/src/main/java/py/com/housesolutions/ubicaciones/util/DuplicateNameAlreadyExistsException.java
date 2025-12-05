package py.com.housesolutions.ubicaciones.util;

/**
 * Excepción redundante con NameAlreadyExistsException.
 * Se utiliza para indicar conflicto por nombre duplicado.
 */
public class DuplicateNameAlreadyExistsException extends RuntimeException {
    /**
    * ⚠️ Sugerencia: Esta excepción es redundante con NameAlreadyExistsException.
    * Recomiendo eliminarla, pero aquí está su documentación si deseas mantenerla
    */
    public DuplicateNameAlreadyExistsException(String message) {
        super(message);
    }
}
