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
import py.com.housesolutions.ubicaciones.model.CiudadCreateDTO;
import py.com.housesolutions.ubicaciones.model.CiudadResponseDTO;
import py.com.housesolutions.ubicaciones.model.CiudadUpdateDTO;
import py.com.housesolutions.ubicaciones.service.CiudadService;
import py.com.housesolutions.ubicaciones.util.MissingParameterException;
import py.com.housesolutions.ubicaciones.util.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/ciudades", produces = MediaType.APPLICATION_JSON_VALUE)
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
@Tag(name = "Ciudades", description = "Gestión de ciudades")
@Slf4j
public class CiudadResource {
    private final CiudadService service;

    // Constructor inyecta el servicio `CiudadService`.
    public CiudadResource(CiudadService service) {
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

    // GET: Obtiene todas las ciudades activas.
    @Operation(summary = "Obtener todas las ciudades activas",
            description = "Devuelve una lista de todas las ciudades activas registradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        try {
            log.info("CiudadResource-getAll::obteniendo el listado de Ciudades");
            return ResponseEntity.ok(service.findAll());
        } catch (DataAccessException e) {
            log.error("CiudadResource-getAll-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("CiudadResource-getAll::Error al obtener la lista de Ciudades", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET: Obtiene una ciudad por su ID.
    @Operation(
            summary = "Obtener una ciudad por ID",
            description = "Devuelve los detalles de una ciudad específica por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ciudad obtenida exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID es obligatorio.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        try {
            log.info("CiudadResource-getById::Obteniendo ciudad por código identificador");
            CiudadResponseDTO dto = service.get(id);
            return ResponseEntity.ok(dto);
        } catch (MissingParameterException e) {
            log.error("CiudadResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("CiudadResource-getById-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            log.error("CiudadResource-getById-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("CiudadResource-getById-Exception::Error Interno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // POST: Crea una nueva ciudad.
    @Operation(
            summary = "Crear una nueva ciudad",
            description = "Permite registrar una nueva ciudad en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ciudad creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos de la ciudad", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CiudadCreateDTO request) throws Exception {
        try {
            log.info("CiudadResource-create::Creando un nuevo recurso");
            CiudadResponseDTO response = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            log.error("CiudadResource-create-DataIntegrityViolationException::Error al enviar datos para crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("CiudadResource-create-Exception::Error al crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT: Actualiza una ciudad por ID.
    @Operation(
            summary = "Actualizar una ciudad por ID",
            description = "Permite actualizar los datos de una ciudad específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ciudad actualizada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos enviados", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Ciudad no encontrada.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CiudadUpdateDTO request) {
        try {
            log.info("CiudadResource-update::actualizando Ciudad con ID: {}", id);
            CiudadResponseDTO updatedCiudad = service.update(id, request);
            return ResponseEntity.ok(updatedCiudad);
        } catch (MissingParameterException e) {
            log.error("CiudadResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("CiudadResource-update-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            log.error("CiudadResource-update-DataIntegrityViolationException::Error al enviar datos para actualizar registro de Ciudad", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("CiudadResource-update-Exception::Error al actualizar la Ciudad con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    // DELETE: Marca una ciudad como eliminada.
    @Operation(
            summary = "Eliminar una ciudad por ID",
            description = "Marca una ciudad como eliminada sin borrarla físicamente de la base de datos",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ciudad eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            log.info("CiudadResource-delete::eliminando Ciudad con ID: {}", id);
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("CiudadResource-delete::Error al eliminar la Ciudad con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: "+e.getMessage());
        }
    }
}
