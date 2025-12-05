package py.com.housesolutions.ubicaciones.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO utilizado para la creación de un nuevo país.
 * Contiene todos los campos requeridos y opcionales necesarios para registrar un país.
 *
 * <p>Incluye validaciones con Jakarta Validation para garantizar que los datos
 * ingresados sean correctos antes de llegar a la capa de servicio.</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaisCreateDTO {
    /**
     * Nombre del país.
     * Debe contener mínimo 2 caracteres.
     */
    @NotEmpty(message = "El campo Nombre no puede estar vacío.")
    @NotBlank(message = "El campo Nombre no puede estar en blanco.")
    @Size(min = 2, message = "El campo Nombre debe tener entre 2 y 255 caracteres.")
    private String name;

    /**
     * Código ISO2 del país (ejemplo: "PY", "AR").
     */
    @Size(max = 2, message = "El campo CódigoIso2 debe tener hasta 2 caracteres.")
    private String codigoIso2;

    /**
     * Código ISO3 del país (ejemplo: "PRY", "ARG").
     */
    @Size(max = 3, message = "El campo CódigoIso3 debe tener hasta 3 caracteres.")
    private String codigoIso3;

    /** Capital del país. */
    private String capital;

    /** Población total estimada del país. */
    private Integer poblacion;

    /** Área total (km²). */
    private BigDecimal area = BigDecimal.ZERO;

    /** Idioma principal del país. */
    private String idioma;

    /**
     * ID de la moneda principal asociada.
     * Puede ser nulo si el país no tiene moneda configurada al momento de la creación.
     */
    private Long monedaId;

    /** Dominio TLD del país (ejemplo ".py"). */
    private String dominioTld;

    /** Huso horario principal del país. */
    private String husoHorario;

    /**
     * Continente donde se ubica el país.
     * Valor por defecto: SIN_ESPECIFICAR.
     */
    private Continente continente = Continente.SIN_ESPECIFICAR;
}
