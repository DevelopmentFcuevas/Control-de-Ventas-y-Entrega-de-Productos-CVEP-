package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditoriaDTO {
    private Long id;
    private Action action; // E.g., CREATE, UPDATE, DELETE
    private String entity; // E.g., Nombre de la entidad afectada
    private Long entityId; // ID de la entidad afectada
    private String performedBy;
    private LocalDateTime timestamp;
    private String details;
}
