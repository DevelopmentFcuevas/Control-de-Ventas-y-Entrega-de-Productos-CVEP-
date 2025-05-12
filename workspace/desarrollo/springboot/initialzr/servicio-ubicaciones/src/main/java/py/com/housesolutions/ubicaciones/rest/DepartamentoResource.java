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
import py.com.housesolutions.ubicaciones.model.DepartamentoCreateDTO;
import py.com.housesolutions.ubicaciones.model.DepartamentoResponseDTO;
import py.com.housesolutions.ubicaciones.model.DepartamentoUpdateDTO;
import py.com.housesolutions.ubicaciones.service.DepartamentoService;
import py.com.housesolutions.ubicaciones.util.MissingParameterException;
import py.com.housesolutions.ubicaciones.util.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/departamentos", produces = MediaType.APPLICATION_JSON_VALUE)
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
@Tag(name = "Departamentos", description = "Gestión de departamentos")
@Slf4j
public class DepartamentoResource {
    private final DepartamentoService service;

    // Constructor inyecta el servicio `DepartamentoService`.
    public DepartamentoResource(DepartamentoService service) {
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

    // GET: Obtiene todos los departamentos activos.
    @Operation(summary = "Obtener todos los departamentos activos",
            description = "Devuelve una lista de todos los departamentos activos registrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de departamentos obtenida exitosamente"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        try {
            log.info("DepartamentoResource-getAll::obteniendo el listado de Departamentos");
            return ResponseEntity.ok(service.findAll());
        } catch (DataAccessException e) {
            log.error("DepartamentoResource-getAll-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("DepartamentoResource-getAll::Error al obtener la lista de Departamentos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET: Obtiene un departamento por su ID.
    @Operation(
            summary = "Obtener un departamento por ID",
            description = "Devuelve los detalles de un departamento específico por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departamento obtenido exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID es obligatorio.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        try {
            log.info("DepartamentoResource-getById::Obteniendo departamento por código identificador");
            DepartamentoResponseDTO dto = service.get(id);
            return ResponseEntity.ok(dto);
        } catch (MissingParameterException e) {
            log.error("DepartamentoResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("DepartamentoResource-getById-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            log.error("DepartamentoResource-getById-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("DepartamentoResource-getById-Exception::Error Interno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // POST: Crea un nuevo departamento.
    @Operation(
            summary = "Crear un nuevo departamento",
            description = "Permite registrar un nuevo departamento en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Departamento creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos del departamento", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DepartamentoCreateDTO request) throws Exception {
        try {
            log.info("DepartamentoResource-create::Creando un nuevo recurso {}", request);
            DepartamentoResponseDTO response = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            log.error("DepartamentoResource-create-DataIntegrityViolationException::Error al enviar datos para crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("DepartamentoResource-create-Exception::Error al crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT: Actualiza un departamento por ID.
    @Operation(
            summary = "Actualizar un departamento por ID",
            description = "Permite actualizar los datos de un departamento específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departamento actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos enviados", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DepartamentoUpdateDTO request) {
        try {
            log.info("DepartamentoResource-update::actualizando Departamento con ID: {}", id);
            DepartamentoResponseDTO updatedDepartamento = service.update(id, request);
            return ResponseEntity.ok(updatedDepartamento);
        } catch (MissingParameterException e) {
            log.error("DepartamentoResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("DepartamentoResource-update-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            log.error("DepartamentoResource-update-DataIntegrityViolationException::Error al enviar datos para actualizar registro de Departamento", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("DepartamentoResource-update-Exception::Error al actualizar el Departamento con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE: Marca un departamento como eliminado.
    @Operation(
            summary = "Eliminar un departamento por ID",
            description = "Marca un departamento como eliminado sin borrarlo físicamente de la base de datos",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Departamento eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            log.info("DepartamentoResource-delete::eliminando Departamento con ID: {}", id);
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("DepartamentoResource-delete::Error al eliminar el Departamento con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: "+e.getMessage());
        }
    }


}
