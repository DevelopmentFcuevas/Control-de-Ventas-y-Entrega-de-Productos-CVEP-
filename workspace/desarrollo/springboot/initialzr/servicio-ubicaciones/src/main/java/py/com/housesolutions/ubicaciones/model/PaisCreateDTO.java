package py.com.housesolutions.ubicaciones.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaisCreateDTO {
    @NotEmpty(message = "El campo Nombre no puede estar vacío.")
    @NotBlank(message = "El campo Nombre no puede estar en blanco.")
    @Size(min = 2, message = "El campo Nombre debe tener entre 2 y 255 caracteres.")
    private String name;
    @Size(max = 2, message = "El campo CódigoIso2 debe tener hasta 2 caracteres.")
    private String codigoIso2;
    @Size(max = 3, message = "El campo CódigoIso3 debe tener hasta 3 caracteres.")
    private String codigoIso3;
    private String capital;
    private Integer poblacion;
    private BigDecimal area = BigDecimal.ZERO;
    private String idioma;
    private String moneda;
    private String dominioTld;
    private String husoHorario;
    private Continente continente = Continente.SIN_ESPECIFICAR;
}
