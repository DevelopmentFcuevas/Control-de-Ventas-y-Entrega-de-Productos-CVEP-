package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DepartamentoDTO {
    private Long id;
    PaisDTO pais;
    private String name;
    private String codigoIso;
    private String capital;
    private Integer poblacion;
    private BigDecimal superficie;
    private Region region;
    private Estado estado;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
