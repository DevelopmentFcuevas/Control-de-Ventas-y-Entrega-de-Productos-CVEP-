package py.com.housesolutions.ubicaciones.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import py.com.housesolutions.ubicaciones.model.Estado;
import py.com.housesolutions.ubicaciones.model.PaisCreateDTO;
import py.com.housesolutions.ubicaciones.model.PaisResponseDTO;
import py.com.housesolutions.ubicaciones.model.PaisUpdateDTO;
import py.com.housesolutions.ubicaciones.service.PaisService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/paises", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:3000")
/*@CrossOrigin (
        origins = "http://localhost:5173", // Origen permitido
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.DELETE,
                RequestMethod.PUT,
                RequestMethod.PATCH}, // Métodos permitidos
        allowedHeaders = {"Content-Type", "Authorization"}, // Encabezados permitidos
        allowCredentials = "true" // Permitir cookies y credenciales
)*/
@Tag(name = "Países", description = "Gestión de países")
@Slf4j
public class PaisResource {
    /*
    ✔️ El RestController solo debe:
        Recibir.
        Delegar.
        Responder.
    */

    private final PaisService service;
    // Constructor inyecta el servicio `PaisService`.
    public PaisResource(final PaisService service) {
        this.service = service;
    }

    // ===================================================================== //
    //                MANEJO DE ERRORES DE VALIDACIÓN                        //
    // ===================================================================== //
    /**
     * Maneja las excepciones lanzadas por validaciones de Bean Validation.
     * Convierte errores de campos en un mapa JSON: {campo: "mensaje"}.
     */
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

    // ===================================================================== //
    //                          CREAR UN PAÍS                                //
    // ===================================================================== //
    @Operation(
            summary = "Crear un nuevo país",
            description = "Permite registrar un nuevo país en la base de datos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "País creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos del país", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Conflicto: nombre duplicado", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PaisCreateDTO request) throws Exception {
        log.info("PaisResource-create::Creando un nuevo recurso");
        PaisResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =====================================================================
    //                          ELIMINAR UN PAÍS
    // =====================================================================
    @Operation(
            summary = "Eliminar un país por ID",
            description = "Marca un país como eliminado sin borrarlo físicamente de la base de datos",
            responses = {
                    @ApiResponse(responseCode = "204", description = "País eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida (parámetros faltantes)", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        log.info("PaisResource-delete::eliminando País con ID: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204 sin body
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ===================================================================== //
    //                          ACTUALIZAR UN PAÍS                           //
    // ===================================================================== //
    @Operation(
            summary = "Actualizar un país por ID",
            description = "Permite actualizar los datos de un país específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El parámetro ID o los datos enviados son inválidos o faltantes.", content = @Content),
                    @ApiResponse(responseCode = "404", description = "País no encontrado.", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Conflicto por nombre duplicado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PaisUpdateDTO request) throws Exception {
        log.info("PaisResource-update::actualizando Pais con ID: {}", id);
        PaisResponseDTO updatedPais = service.update(id, request);
        return ResponseEntity.ok(updatedPais);
    }

    // ===================================================================== //
    //                       OBTENER LISTADO DE PAÍSES                       //
    // ===================================================================== //
    @Operation(summary = "Obtener todos los países activos",
            description = "Devuelve una lista de todos los países activos registrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de países obtenida exitosamente"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        log.info("PaisResource-getAll::obteniendo el listado de Países");
        return ResponseEntity.ok(service.findAll());
    }

    // ===================================================================== //
    //                     TOTAL DE PAÍSES POR ESTADO                        //
    // ===================================================================== //
    @Operation(
            summary = "Contar países por estado",
            description = "Devuelve el total de países activos filtrados por estado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cantidad devuelta exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Estado inválido", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping("/count/estado/{estado}")
    public ResponseEntity<Long> countByEstado(@PathVariable Estado estado) throws Exception {
        log.info("PaisResource-countByEstado::Contar países por estado {}", estado);
        long count = service.countByEstado(estado);
        return ResponseEntity.ok(count);
    }

    // ===================================================================== //
    //                    TOTAL DE PAÍSES POR FECHA                          //
    // ===================================================================== //
    @Operation(
            summary = "Contar países creados en una fecha",
            description = "Devuelve cuántos países fueron registrados en la fecha indicada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cantidad devuelta exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Fecha con formato inválido", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping("/count/fecha/{fecha}")
    public ResponseEntity<Long> countByFechaCreacion(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) throws Exception {
        log.info("PaisResource-countByFechaCreacion::Contar países por fecha {}", fecha);
        long count = service.countByFechaCreacion(fecha);
        return ResponseEntity.ok(count);
    }

    // ===================================================================== //
    //                        OBTENER PAÍS POR ID                            //
    // ===================================================================== //
    @Operation(
            summary = "Obtener un país por ID",
            description = "Devuelve un país activo correspondiente al ID indicado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País encontrado",
                            content = @Content(schema = @Schema(implementation = PaisResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "ID inválido o faltante", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        log.info("PaisResource-getById::Obteniendo pais por código identificador");
        PaisResponseDTO dto = service.get(id);
        return ResponseEntity.ok(dto);
    }

}
