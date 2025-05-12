package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.time.LocalDateTime;

@Entity
@Table(name = "barrios", schema = "ubicaciones",
        indexes = {
            @Index(name = "idx_name_barrio", columnList = "name"),
            @Index(name = "idx_ciudad_barrio", columnList = "ciudad_id")
        },
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name", "ciudad_id"})
        })
@Getter
@Setter
@Comment("Barrio/Localidad Cada ciudad o distrito tiene diferentes barrios o localidades (ej. \"Sajonia\", \"San Vicente\").")
public class Barrio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Identificador único del registro.")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    @Comment("Identificador de la ciudad a la que pertenece (FK). Referencia a la ciudad al que pertenece el barrio/localidad")
    Ciudad ciudad;

    @Column(name = "name", nullable = false)
    @Comment("Nombre del barrio o localidad.")
    private String name;

    @Column(name = "estado", nullable = true, columnDefinition = "varchar(255) default 'ACTIVO'")
    @Enumerated(EnumType.STRING)
    @Comment("Estado actual del registro.")
    private Estado estado; // Marcado como activo por defecto

    @Column(name = "created_by", nullable = true)
    //@CreatedBy
    @Comment("Campos para la seguridad. (Creado por) Registro del usuario que creó el registro.")
    private String createdBy;

    @Column(name = "created_at", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Comment("Campos para la seguridad. (Fecha de creación) Registro de la fecha y hora en que se creó el registro.")
    private LocalDateTime createdAt;

    @Column(name = "updated_by", nullable = true)
    //@LastModifiedBy
    @Comment("Campos para la seguridad. (Actualizado por) Registro del usuario que realizó la última actualización en el registro.")
    private String updatedBy;

    //@LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    @Comment("Campos para la seguridad. (Fecha de actualización) Registro de la fecha y hora de la última actualización en el registro.")
    private LocalDateTime updatedAt;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    @Comment("Campos para la parte de auditoría. (Eliminado) Registro de si el registro ha sido eliminado o no.")
    private boolean deleted;

    @Column(name = "deleted_by", nullable = true)
    @Comment("Campos para la parte de auditoría. (Eliminado por) Registro del usuario que eliminó el registro.")
    private String deletedBy;

    @Column(name = "deleted_at", nullable = true)
    @Comment("Campos para la parte de auditoría. (Fecha de eliminación): Registro de la fecha y hora en que el registro fue eliminado.")
    private LocalDateTime deletedAt;
}
