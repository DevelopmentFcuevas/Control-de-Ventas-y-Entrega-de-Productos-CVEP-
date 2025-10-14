package py.com.housesolutions.ubicaciones.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonedaUpdateDTO {
    private Long id; // Id para identificar qué moneda actualizar
    @NotEmpty(message = "El campo Nombre no puede estar vacío.")
    @NotBlank(message = "El campo Nombre no puede estar en blanco.")
    @Size(min = 2, message = "El campo Nombre debe tener entre 2 y 255 caracteres.")
    private String name;
    @Size(max = 3, message = "El campo Código debe tener hasta 3 caracteres.")
    private String code;
    private String simbolo;
    private String isoNum;
    private Float minorUnit;
    private String notas;
}
