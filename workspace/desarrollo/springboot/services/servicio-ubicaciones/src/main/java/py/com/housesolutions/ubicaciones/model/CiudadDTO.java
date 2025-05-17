package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CiudadDTO {
    private Long id;
    DepartamentoDTO departamento;
    private String name;
    private String codigoPostal;
    private Estado estado;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private boolean deleted;
    private String deletedBy;
    private LocalDateTime deletedAt;
}
