package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaisMonedaDTO {
    private Long id;
    PaisDTO pais;
    MonedaDTO moneda;
    private Boolean esOficial;
    private Boolean esPrimaria;
    private LocalDate validoDesde;
    private LocalDate validoHasta;
    private Estado estado;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
