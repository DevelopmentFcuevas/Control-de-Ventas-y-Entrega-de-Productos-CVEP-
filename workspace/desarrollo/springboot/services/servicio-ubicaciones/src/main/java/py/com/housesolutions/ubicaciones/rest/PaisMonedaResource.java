package py.com.housesolutions.ubicaciones.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import py.com.housesolutions.ubicaciones.model.PaisMonedaResponseDTO;
import py.com.housesolutions.ubicaciones.model.PaisResponseDTO;
import py.com.housesolutions.ubicaciones.service.PaisMonedaService;
import py.com.housesolutions.ubicaciones.util.MissingParameterException;
import py.com.housesolutions.ubicaciones.util.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/pais-monedas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Países-Monedas", description = "Gestión de Países-Monedas")
@Slf4j
public class PaisMonedaResource {
    private final PaisMonedaService service;

    public PaisMonedaResource(PaisMonedaService service) {
        this.service = service;
    }

    // Manejado de excepciones para errores de validación en métodos que reciben input del usuario.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("Ejecutando la validación de campos");
        // Mapeo de errores de validación en los campos del formulario.
        Map<String, String> errores = new HashMap<>();

        // Obtener los errores de cada campo que falló en la validación.
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        // Retornar el mapa de errores como respuesta.
        // Retorno de los errores de validación con código 400.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // GET: Obtiene un País-Moneda por su IdPais y EsPrimaria.
    @Operation(
            summary = "Obtener un país por IdPais y EsPrimaria",
            description = "Devuelve los detalles de un País-Moneda específico por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País-Moneda obtenido exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID es obligatorio.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "País-Moneda no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @GetMapping("/{idPais}/{esPrimaria}")
    public ResponseEntity<?> getByIdPaisAndEsPrimaria(@PathVariable Long idPais, @PathVariable Boolean esPrimaria) throws Exception {
        try {
            log.info("PaisMonedaResource-getByIdPaisAndEsPrimaria::Obteniendo Pais-Moneda por código identificador de Pais: {} y EsPrimaria: {}", idPais, esPrimaria);
            PaisMonedaResponseDTO dto = service.getByPaisIdAndEsPrimaria(idPais, esPrimaria);
            return ResponseEntity.ok(dto);
        } catch (MissingParameterException e) {
            log.error("PaisMonedaResource-getByIdPaisAndEsPrimaria-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("PaisMonedaResource-getByIdPaisAndEsPrimaria-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            log.error("PaisMonedaResource-getByIdPaisAndEsPrimaria-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("PaisMonedaResource-getByIdPaisAndEsPrimaria-Exception::Error Interno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    // GET: Obtiene todos los País-Moneda activos.
    @Operation(summary = "Obtener todos los País-Moneda activos",
            description = "Devuelve una lista de todos los País-Moneda activos registrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de País-Moneda obtenida exitosamente"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        try {
            log.info("PaisMonedaResource-getAll::obteniendo el listado de País-Moneda");
            return ResponseEntity.ok(service.findAll());
        } catch (DataAccessException e) {
            log.error("PaisMonedaResource-getAll-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("PaisMonedaResource-getAll-Exception::Error al obtener la lista de Países", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
