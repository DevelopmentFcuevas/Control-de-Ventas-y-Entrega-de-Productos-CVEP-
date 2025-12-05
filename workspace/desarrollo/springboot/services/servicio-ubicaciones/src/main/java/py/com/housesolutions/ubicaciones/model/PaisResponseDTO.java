package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO utilizado para enviar información detallada de un país al cliente.
 * Representa la vista final que recibe el front-end.
 */
@Getter
@Setter
public class PaisResponseDTO {
    /** Identificador único del país. */
    private Long id;

    /** Nombre del país. */
    private String name;

    /** Código ISO de dos letras. */
    private String codigoIso2;

    /** Código ISO de tres letras. */
    private String codigoIso3;

    /** Capital del país. */
    private String capital;

    /** Población estimada. */
    private Integer poblacion;

    /** Área total en km². */
    private BigDecimal area;

    /** Idioma principal. */
    private String idioma;

    /** Nombre de la moneda principal (si existe). */
    private String moneda;//ESTABA COMENTADO
    //MonedaResponseDTO moneda;

    /** Dominio TLD del país. */
    private String dominioTld;

    /** Huso horario. */
    private String husoHorario;

    /** Continente asociado. */
    private Continente continente;

    /** Estado del país (ACTIVO / INACTIVO). */
    private Estado estado;

    /** Fecha de creación del registro. */
    private LocalDateTime createdAt;

    /** Fecha de la última actualización. */
    private LocalDateTime updatedAt;
}
