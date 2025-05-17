package py.com.housesolutions.ubicaciones.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import py.com.housesolutions.ubicaciones.model.BarrioCreateDTO;
import py.com.housesolutions.ubicaciones.model.BarrioResponseDTO;
import py.com.housesolutions.ubicaciones.model.BarrioUpdateDTO;
import py.com.housesolutions.ubicaciones.service.BarrioService;
import py.com.housesolutions.ubicaciones.util.MissingParameterException;
import py.com.housesolutions.ubicaciones.util.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/barrios", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(
        origins = "http://localhost:3000", // Origen permitido
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT,
                RequestMethod.PATCH}, // Métodos permitidos
        allowedHeaders = {"Content-Type", "Authorization"}, // Encabezados permitidos
        allowCredentials = "true" // Permitir cookies y credenciales
)
@Tag(name = "Barrios", description = "Gestión de barrios")
@Slf4j
public class BarrioResource {
    private final BarrioService service;

    // Constructor inyecta el servicio `BarrioService`.
    public BarrioResource(BarrioService service) {
        this.service = service;
    }

    /*
     * Para lograr que la respuesta incluya los mensajes de error detallados cuando falla una
     * validación, se usa la anotación @ExceptionHandler para capturar la excepción
     * MethodArgumentNotValidException y devolver una estructura de respuesta con los errores
     * específicos de cada campo.
     */
    // Manejador de excepciones para errores de validación en métodos que reciben input del usuario.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
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

    // GET: Obtiene todos los barrios activos.
    @Operation(summary = "Obtener todos los barrios activos",
            description = "Devuelve una lista de todos los barrios activos registrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de barrios obtenida exitosamente"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        try {
            log.info("BarrioResource-getAll::obteniendo el listado de Barrios");
            return ResponseEntity.ok(service.findAll());
        } catch (DataAccessException e) {
            log.error("BarrioResource-getAll-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("BarrioResource-getAll::Error al obtener la lista de Barrios", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET: Obtiene un barrio por su ID.
    @Operation(
            summary = "Obtener un barrio por ID",
            description = "Devuelve los detalles de un barrio específico por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Barrio obtenido exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID es obligatorio.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Barrio no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        try {
            log.info("BarrioResource-getById::Obteniendo barrio por código identificador");
            BarrioResponseDTO dto = service.get(id);
            return ResponseEntity.ok(dto);
        } catch (MissingParameterException e) {
            log.error("BarrioResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("BarrioResource-getById-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            log.error("BarrioResource-getById-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("BarrioResource-getById-Exception::Error Interno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // POST: Crea un nuevo barrio.
    @Operation(
            summary = "Crear un nuevo barrio",
            description = "Permite registrar un nuevo barrio en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Barrio creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos del barrio", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BarrioCreateDTO request) throws Exception {
        try {
            log.info("BarrioResource-create::Creando un nuevo recurso");
            BarrioResponseDTO response = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            log.error("BarrioResource-create-DataIntegrityViolationException::Error al enviar datos para crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("BarrioResource-create-Exception::Error al crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT: Actualiza un barrio por ID.
    @Operation(
            summary = "Actualizar un barrio por ID",
            description = "Permite actualizar los datos de un barrio específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Barrio actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos enviados", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Barrio no encontrado.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BarrioUpdateDTO request) {
        try {
            log.info("BarrioResource-update::actualizando Barrio con ID: {}", id);
            BarrioResponseDTO updatedBarrio = service.update(id, request);
            return ResponseEntity.ok(updatedBarrio);
        } catch (MissingParameterException e) {
            log.error("BarrioResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("BarrioResource-update-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            log.error("BarrioResource-update-DataIntegrityViolationException::Error al enviar datos para actualizar registro de Barrio", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("BarrioResource-update-Exception::Error al actualizar la Barrio con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    // DELETE: Marca un barrio como eliminado.
    @Operation(
            summary = "Eliminar un barrio por ID",
            description = "Marca un barrio como eliminado sin borrarlo físicamente de la base de datos",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Barrio eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Barrio no encontrado", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            log.info("BarrioResource-delete::eliminando Barrio con ID: {}", id);
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("BarrioResource-delete::Error al eliminar el    Barrio con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: "+e.getMessage());
        }
    }

}
