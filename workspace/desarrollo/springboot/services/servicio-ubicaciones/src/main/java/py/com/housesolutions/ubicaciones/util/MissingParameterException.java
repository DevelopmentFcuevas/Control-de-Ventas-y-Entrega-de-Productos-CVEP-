package py.com.housesolutions.ubicaciones.util;

/**
 * Excepción lanzada cuando un parámetro requerido no fue enviado
 * o está vacío / nulo.
 */
public class MissingParameterException extends RuntimeException {
    public MissingParameterException(String message) {
        super(message);
    }
}
