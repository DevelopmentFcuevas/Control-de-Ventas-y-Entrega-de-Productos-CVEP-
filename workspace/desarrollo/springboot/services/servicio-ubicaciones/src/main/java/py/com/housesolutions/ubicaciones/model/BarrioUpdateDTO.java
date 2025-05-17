package py.com.housesolutions.ubicaciones.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarrioUpdateDTO {
    private Long id;
    private CiudadResponseDTO ciudad;
    @NotEmpty(message = "El campo Nombre no puede estar vacío.")
    @NotBlank(message = "El campo Nombre no puede estar en blanco.")
    @Size(min = 2, message = "El campo Nombre debe tener entre 2 y 255 caracteres.")
    private String name;
}
