package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import py.com.housesolutions.ubicaciones.model.Action;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria", schema = "ubicaciones")
@Getter
@Setter
@Comment("Tabla de Auditoría permite registrar eventos clave (creación, modificación y eliminación).")
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Identificador único del registro.")
    private Long id;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action; // E.g., CREATE, UPDATE, DELETE

    @Column(name = "entity", nullable = false)
    private String entity; // E.g., Nombre de la entidad afectada

    @Column(name = "entityId", nullable = false)
    private Long entityId; // ID de la entidad afectada

    @Column(name = "performedBy", nullable = false)
    private String performedBy;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "details", nullable = false)
    private String details;
}
