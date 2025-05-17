package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaisDTO {
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
    //private String imagePath;
    private Estado estado;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
