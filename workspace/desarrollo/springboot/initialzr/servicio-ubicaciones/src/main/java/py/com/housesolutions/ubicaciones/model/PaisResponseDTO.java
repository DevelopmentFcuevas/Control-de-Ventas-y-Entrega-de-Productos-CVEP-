package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaisResponseDTO {
    private Long id;
    private String name;
    private String codigoIso2;
    private String codigoIso3;
    private String capital;
    private Integer poblacion;
    private BigDecimal area;
    private String idioma;
    private String moneda;
    private String dominioTld;
    private String husoHorario;
    private Continente continente;
    private Estado estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
