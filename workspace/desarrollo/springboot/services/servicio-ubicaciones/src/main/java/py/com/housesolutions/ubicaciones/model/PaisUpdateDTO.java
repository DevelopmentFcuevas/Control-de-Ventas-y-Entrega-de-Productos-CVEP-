package py.com.housesolutions.ubicaciones.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO utilizado para actualizar los datos de un país existente.
 */
@Getter
@Setter
public class PaisUpdateDTO {
    /**
     * Identificador del país que se desea actualizar.
     * Se usa únicamente para validación interna.
     */
    private Long id; // Id para identificar qué país actualizar

    /**
     * Nombre del país.
     * Reglas igual que en creación.
     */
    @NotEmpty(message = "El campo Nombre no puede estar vacío.")
    @NotBlank(message = "El campo Nombre no puede estar en blanco.")
    @Size(min = 2, message = "El campo Nombre debe tener entre 2 y 255 caracteres.")
    private String name;

    /** Código ISO2 del país. */
    @Size(max = 2, message = "El campo CódigoIso2 debe tener hasta 2 caracteres.")
    private String codigoIso2;

    /** Código ISO3 del país. */
    @Size(max = 3, message = "El campo CódigoIso3 debe tener hasta 3 caracteres.")
    private String codigoIso3;

    /** Capital del país. */
    private String capital;

    /** Población del país. */
    private Integer poblacion;

    /** Área (km²). */
    private BigDecimal area = BigDecimal.ZERO;

    /** Idioma del país. */
    private String idioma;

    /** Moneda principal asociada. */
    //private String moneda;
    private Long monedaId;

    /** Dominio TLD. */
    private String dominioTld;

    /** Huso horario. */
    private String husoHorario;

    /** Continente asociado. */
    private Continente continente = Continente.SIN_ESPECIFICAR;
}
