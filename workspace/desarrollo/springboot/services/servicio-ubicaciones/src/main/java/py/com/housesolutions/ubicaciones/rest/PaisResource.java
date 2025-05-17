package py.com.housesolutions.ubicaciones.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;
import py.com.housesolutions.ubicaciones.model.Estado;
import py.com.housesolutions.ubicaciones.model.PaisCreateDTO;
import py.com.housesolutions.ubicaciones.model.PaisResponseDTO;
import py.com.housesolutions.ubicaciones.model.PaisUpdateDTO;
import py.com.housesolutions.ubicaciones.service.PaisService;
import py.com.housesolutions.ubicaciones.util.MissingParameterException;
import py.com.housesolutions.ubicaciones.util.NotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/paises", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin (
        origins = "http://localhost:5173", // Origen permitido
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT,
                RequestMethod.PATCH}, // Métodos permitidos
        allowedHeaders = {"Content-Type", "Authorization"}, // Encabezados permitidos
        allowCredentials = "true" // Permitir cookies y credenciales
)
@Tag(name = "Países", description = "Gestión de países")
@Slf4j
public class PaisResource {
    private final PaisService service;
    // Constructor inyecta el servicio `PaisService`.
    public PaisResource(final PaisService service) {
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


    // GET: Obtiene todos los países activos.
    @Operation(summary = "Obtener todos los países activos",
            description = "Devuelve una lista de todos los países activos registrados",
            responses = {
                @ApiResponse(responseCode = "200", description = "Lista de países obtenida exitosamente"),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        try {
            log.info("PaisResource-getAll::obteniendo el listado de Países");
            return ResponseEntity.ok(service.findAll());
        } catch (DataAccessException e) {
            log.error("PaisResource-getAll-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("PaisResource-getAll-Exception::Error al obtener la lista de Países", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GET: Obtiene un país por su ID.
    @Operation(
            summary = "Obtener un país por ID",
            description = "Devuelve los detalles de un país específico por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País obtenido exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID es obligatorio.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        try {
            log.info("PaisResource-getById::Obteniendo pais por código identificador");
            PaisResponseDTO dto = service.get(id);
            return ResponseEntity.ok(dto);
        } catch (MissingParameterException e) {
            log.error("PaisResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("PaisResource-getById-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            log.error("PaisResource-getById-DataAccessException::Error de acceso a la BD", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("PaisResource-getById-Exception::Error Interno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // POST: Crea un nuevo país.
    @Operation(
            summary = "Crear un nuevo país",
            description = "Permite registrar un nuevo país en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "País creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos del país", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PaisCreateDTO request) throws Exception {
    //@PostMapping(/*consumes = MediaType.MULTIPART_FORM_DATA_VALUE,*/
    //                /*consumes = "multipart/form-data;charset=UTF-8",*/
    //                consumes = {
    //                        MediaType.MULTIPART_FORM_DATA_VALUE,
    //                        "multipart/form-data;charset=UTF-8"
    //                },
    //                produces = MediaType.APPLICATION_JSON_VALUE)
    //public ResponseEntity<?> create(/*@RequestPart("data") @Valid PaisCreateDTO request,*/
    //                                @RequestPart("data") String dataJson,
    //                                @RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {
        try {
            log.info("PaisResource-create::Creando un nuevo recurso");

            // 1) Convertir JSON a DTO
            //PaisCreateDTO request = new ObjectMapper().readValue(dataJson, PaisCreateDTO.class);

            PaisResponseDTO response = service.create(request);
            //PaisResponseDTO response = service.create(request, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            log.error("PaisResource-create-DataIntegrityViolationException::Error al enviar datos para crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("PaisResource-create-Exception::Error al crear nuevo recurso", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PUT: Actualiza un país por ID.
    @Operation(
            summary = "Actualizar un país por ID",
            description = "Permite actualizar los datos de un país específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID o los datos enviados son inválidos.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "País no encontrado.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PaisUpdateDTO request) {
        try {
            log.info("PaisResource-update::actualizando Pais con ID: {}", id);
            PaisResponseDTO updatedPais = service.update(id, request);
            return ResponseEntity.ok(updatedPais);
        } catch (MissingParameterException e) {
            log.error("PaisResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("PaisResource-update-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            log.error("PaisResource-update-DataIntegrityViolationException::Error al enviar datos para actualizar registro de Pais", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            log.error("PaisResource-update-Exception::Error al actualizar el País con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // DELETE: Marca un país como eliminado.
    @Operation(
            summary = "Eliminar un país por ID",
            description = "Marca un país como eliminado sin borrarlo físicamente de la base de datos",
            responses = {
                    @ApiResponse(responseCode = "204", description = "País eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            log.info("PaisResource-delete::eliminando Pais con ID: {}", id);
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (MissingParameterException e) {
            log.error("PaisResource-getById-MissingParameterException::No se envío el parámetro ID.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            log.error("PaisResource-delete-NotFoundException::No se encontró el recurso solicitado.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("PaisResource-delete-Exception::Error al eliminar el recurso con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/count/estado/{estado}")
    public ResponseEntity<Long> countByEstado(@PathVariable Estado estado) throws Exception {
        log.info("PaisResource-countByEstado::Contar países por estado {}", estado);
        long count = service.countByEstado(estado);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/fecha/{fecha}")
    public ResponseEntity<Long> countByFechaCreacion(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws Exception {
        log.info("PaisResource-countByFechaCreacion::Contar países por fecha {}", fecha);
        long count = service.countByFechaCreacion(fecha);
        return ResponseEntity.ok(count);
    }

}
