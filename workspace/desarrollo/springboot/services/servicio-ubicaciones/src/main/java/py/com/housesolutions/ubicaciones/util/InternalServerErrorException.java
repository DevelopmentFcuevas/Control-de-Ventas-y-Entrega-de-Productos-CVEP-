package py.com.housesolutions.ubicaciones.util;

/**
 * Excepción personalizada que representa errores internos controlados.
 * Útil cuando deseas lanzar un 500 manualmente.
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);

    }
}
