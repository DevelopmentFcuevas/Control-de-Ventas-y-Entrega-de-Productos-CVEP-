package py.com.housesolutions.ubicaciones.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para todos los controladores REST.
 * <p>
 * Esta clase captura excepciones personalizadas y estándar,
 * devolviendo respuestas consistentes en formato JSON con:
 * - timestamp
 * - código HTTP
 * - descripción del error
 * - mensaje específico
 *
 * <p>Permite centralizar el manejo de errores, evitando duplicación
 * de bloques try-catch en los controladores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Manejo de excepciones específicas
    /*
     * handleDuplicateNameAlreadyExistsException: Captura la excepción DuplicateNameAlreadyExistsException que
     * lanza el servicio al intentar crear un registro con un nombre ya existente y
     * responde con 409 Conflict.
     * */
    //@ExceptionHandler(DuplicateNameAlreadyExistsException.class)
    //public ResponseEntity<String> handleDuplicateNameAlreadyExistsException(DuplicateNameAlreadyExistsException ex) {
    //    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    //}

    /*
    * handleDataIntegrityViolationException: Captura cualquier excepción de violación de integridad
    * de datos (DataIntegrityViolationException) y responde con 400 Bad Request.
    * */
    //@ExceptionHandler(DataIntegrityViolationException.class)
    //public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    //    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violación de integridad de datos: " + ex.getMessage());
    //}

    // Manejo de excepción general en caso de errores no controlados
    /*
    * handleGlobalException: Captura cualquier otra excepción que no esté controlada explícitamente
    * y responde con 500 Internal Server Error.
    * */
    //@ExceptionHandler(Exception.class)
    //public ResponseEntity<String> handleGlobalException(Exception ex) {
    //    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + ex.getMessage());
    //}

    //@ExceptionHandler(NotFoundException.class)
    //public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La entidad buscada no fue encontrada: " + ex.getMessage());
    //}
    /* ************* */

    /**
     * Construye una respuesta estándar para todas las excepciones.
     *
     * @param ex    excepción lanzada
     * @param status código HTTP asociado
     * @param error descripción del error
     * @return mapa JSON con la estructura común de respuestas de error
     */
    private ResponseEntity<Map<String, Object>> buildResponse(Exception ex, HttpStatus status, String error) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", ex.getMessage());
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Maneja NameAlreadyExistsException.
     * Se lanza cuando se intenta crear o actualizar un recurso
     * con un nombre que ya existe.
     *
     * @param ex excepción personalizada
     * @return respuesta HTTP 409 (Conflict)
     */
    @ExceptionHandler(NameAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleNameAlreadyExists(NameAlreadyExistsException ex) {
        //return buildResponse(ex, HttpStatus.CONFLICT, "Conflict");

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Maneja MissingParameterException.
     * Indica que uno o más parámetros obligatorios no fueron enviados.
     *
     * @param ex excepción personalizada
     * @return respuesta HTTP 400 (Bad Request)
     */
    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParameter(MissingParameterException ex) {
        //return buildResponse(ex, HttpStatus.BAD_REQUEST, "Bad Request");

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Maneja NotFoundException.
     * Se lanza cuando un recurso solicitado no existe.
     *
     * @param ex excepción personalizada
     * @return respuesta HTTP 404 (Not Found)
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        //return buildResponse(ex, HttpStatus.NOT_FOUND, "Not Found");

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Maneja cualquier excepción no controlada.
     *
     * @param ex excepción genérica
     * @return respuesta HTTP 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        //return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
