package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaisMonedaUpdateDTO {
    private Long id; // Id para identificar qué país actualizar
    PaisDTO pais;
    MonedaDTO moneda;
    private Boolean esOficial;
    private Boolean esPrimaria;
    private LocalDate validoDesde;
    private LocalDate validoHasta;
}
