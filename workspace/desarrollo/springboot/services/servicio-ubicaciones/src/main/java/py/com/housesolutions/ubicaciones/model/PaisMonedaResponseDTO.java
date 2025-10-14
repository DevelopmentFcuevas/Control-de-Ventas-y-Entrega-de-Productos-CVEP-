package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaisMonedaResponseDTO {
    private Long id;
    PaisResponseDTO pais;
    MonedaResponseDTO moneda;
    private Boolean esOficial;
    private Boolean esPrimaria;
    private LocalDate validoDesde;
    private LocalDate validoHasta;
    private Estado estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
