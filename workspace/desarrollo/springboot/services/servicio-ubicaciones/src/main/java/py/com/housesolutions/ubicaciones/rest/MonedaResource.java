package py.com.housesolutions.ubicaciones.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.model.MonedaResponseDTO;
import py.com.housesolutions.ubicaciones.service.MonedaService;
import py.com.housesolutions.ubicaciones.util.MissingParameterException;
import py.com.housesolutions.ubicaciones.util.NotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/monedas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Monedas", description = "Gestión de monedas")
@Slf4j
public class MonedaResource {
    private final MonedaService service;
    // Constructor inyecta el servicio `MonedaService`.
    public MonedaResource(MonedaService service) {
        this.service = service;
    }

    // Manejador de excepciones para errores de validación en métodos que reciben input del usuario.
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

    // GET: Obtiene todas las monedas activas.
    @Operation(summary = "Obtener todos las monedas activas",
            description = "Devuelve una lista de todas las monedas activas registradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de monedas obtenida exitosamente"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        try {
            log.info("MonedaResource-getAll::obteniendo el listado de Monedas");
            return ResponseEntity.ok(service.findAll());
        } catch (DataAccessException e) {
            log.error("MonedaResource-getAll-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("MonedaResource-getAll-Exception::Error al obtener la lista de Monedas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET: Obtiene una moneda por su ID.
    @Operation(
            summary = "Obtener una moneda por ID",
            description = "Devuelve los detalles de una moneda específica por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moneda obtenida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID es obligatorio.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Moneda no encontrada", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        try {
            log.info("MonedaResource-getById::Obteniendo moneda por código identificador");
            MonedaResponseDTO dto = service.get(id);
            return ResponseEntity.ok(dto);
        } catch (MissingParameterException e) {
            log.error("MonedaResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("MonedaResource-getById-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            log.error("MonedaResource-getById-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("MonedaResource-getById-Exception::Error Interno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // POST: Crea una nueva moneda.
    @Operation(
            summary = "Crear una nueva moneda",
            description = "Permite registrar una nueva moneda en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Moneda creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos de la moneda", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MonedaCreateDTO request) throws Exception {
        try {
            log.info("MonedaResource-create::Creando un nuevo recurso");
            MonedaResponseDTO response = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            log.error("MonedaResource-create-DataIntegrityViolationException::Error al enviar datos para crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("MonedaResource-create-Exception::Error al crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT: Actualiza una moneda por ID.
    @Operation(
            summary = "Actualizar una moneda por ID",
            description = "Permite actualizar los datos de una moneda específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moneda actualizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID o los datos enviados son inválidos.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Moneda no encontrada.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MonedaUpdateDTO request) {
        try {
            log.info("MonedaResource-update::actualizando Moneda con ID: {}", id);
            MonedaResponseDTO updatedMoneda = service.update(id, request);
            return ResponseEntity.ok(updatedMoneda);
        } catch (MissingParameterException e) {
            log.error("MonedaResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("MonedaResource-update-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            log.error("MonedaResource-update-DataIntegrityViolationException::Error al enviar datos para actualizar registro de Moneda", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("MonedaResource-update-Exception::Error al actualizar la Moneda con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE: Marca una moneda como eliminada.
    @Operation(
            summary = "Eliminar una moneda por ID",
            description = "Marca una moneda como eliminada sin borrarla físicamente de la base de datos",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Moneda eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Moneda no encontrada", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            log.info("MonedaResource-delete::eliminando Moneda con ID: {}", id);
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (MissingParameterException e) {
            log.error("MonedaResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("MonedaResource-delete-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("MonedaResource-delete-Exception::Error al eliminar el recurso con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/count/estado/{estado}")
    public ResponseEntity<Long> countByEstado(@PathVariable Estado estado) throws Exception {
        log.info("MonedaResource-countByEstado::Contar monedas por estado {}", estado);
        long count = service.countByEstado(estado);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/fecha/{fecha}")
    public ResponseEntity<Long> countByFechaCreacion(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws Exception {
        log.info("MonedaResource-countByFechaCreacion::Contar monedas por fecha {}", fecha);
        long count = service.countByFechaCreacion(fecha);
        return ResponseEntity.ok(count);
    }

}
