package py.com.housesolutions.ubicaciones.util;

public class DuplicateNameAlreadyExistsException extends RuntimeException {
    public DuplicateNameAlreadyExistsException(String message) {
        super(message);
    }
}
