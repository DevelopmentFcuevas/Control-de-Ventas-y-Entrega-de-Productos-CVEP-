package py.com.housesolutions.ubicaciones.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaisMonedaCreateDTO {
    PaisDTO pais;
    MonedaDTO moneda;
    private Boolean esOficial;
    private Boolean esPrimaria;
    private LocalDate validoDesde;
    private LocalDate validoHasta;
}
