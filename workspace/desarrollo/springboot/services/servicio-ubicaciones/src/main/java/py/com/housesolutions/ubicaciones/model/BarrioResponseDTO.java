package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BarrioResponseDTO {
    private Long id;
    private CiudadResponseDTO ciudad;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
