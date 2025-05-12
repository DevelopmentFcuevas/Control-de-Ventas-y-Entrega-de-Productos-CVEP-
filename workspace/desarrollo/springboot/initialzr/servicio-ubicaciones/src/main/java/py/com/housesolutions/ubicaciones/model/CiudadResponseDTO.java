package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CiudadResponseDTO {
    private Long id;
    DepartamentoResponseDTO departamento;
    private String name;
    private String codigoPostal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
