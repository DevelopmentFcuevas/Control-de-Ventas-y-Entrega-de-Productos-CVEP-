package py.com.housesolutions.ubicaciones.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//@ControllerAdvice
public class GlobalExceptionHandler {
    // Manejo de excepciones específicas

    /*
     * handleDuplicateNameAlreadyExistsException: Captura la excepción DuplicateNameAlreadyExistsException que
     * lanza el servicio al intentar crear un registro con un nombre ya existente y
     * responde con 409 Conflict.
     * */
    //@ExceptionHandler(DuplicateNameAlreadyExistsException.class)
    public ResponseEntity<String> handleDuplicateNameAlreadyExistsException(DuplicateNameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /*
    * handleDataIntegrityViolationException: Captura cualquier excepción de violación de integridad
    * de datos (DataIntegrityViolationException) y responde con 400 Bad Request.
    * */
    //@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violación de integridad de datos: " + ex.getMessage());
    }


    // Manejo de excepción general en caso de errores no controlados
    /*
    * handleGlobalException: Captura cualquier otra excepción que no esté controlada explícitamente
    * y responde con 500 Internal Server Error.
    * */
    //@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + ex.getMessage());
    }

    //@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad buscada no fue encontrada: " + ex.getMessage());
    }

}
