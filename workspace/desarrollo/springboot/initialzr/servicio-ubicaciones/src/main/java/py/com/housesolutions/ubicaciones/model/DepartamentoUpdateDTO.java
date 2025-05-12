package py.com.housesolutions.ubicaciones.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepartamentoUpdateDTO {
    private Long id; // Id para identificar qué departamento actualizar
    PaisRequestDTO pais;
    @NotEmpty(message = "El campo Nombre no puede estar vacío.")
    @NotBlank(message = "El campo Nombre no puede estar en blanco.")
    @Size(min = 2, message = "El campo Nombre debe tener entre 2 y 255 caracteres.")
    private String name;
    private String codigoIso;
    private String capital;
    private Integer poblacion = 0;
    private BigDecimal superficie = BigDecimal.ZERO;
    private Region region = Region.SIN_ESPECIFICAR;
}
