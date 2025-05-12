package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ciudades", schema = "ubicaciones",
        indexes = {
            @Index(name = "idx_name_ciudad", columnList = "name"),
            @Index(name = "idx_departamento_ciudad", columnList = "departamento_id")
        },
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"name", "departamento_id"})
        }
        )
@Getter
@Setter
@Comment("Tabla que almacena las ciudades disponibles en el sistema. Las ciudades o distritos son unidades administrativas dentro de un departamento (ej. \"Asunción\", \"San Lorenzo\").")
public class Ciudad {
    /*
     * Tabla que almacena las ciudades disponibles en el sistema.
     * Las ciudades o distritos son unidades administrativas dentro de
     * un departamento (ej. "Asunción", "San Lorenzo").
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Identificador único del registro.")
    private Long id;

    //@ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    @Comment("Identificador del departamento al que pertenece (FK). Referencia al departamento al que pertenece la ciudad")
    Departamento departamento;

    @Column(name = "name", nullable = false)
    @Comment("Nombre de la ciudad/distrito")
    private String name;

    @Column(name = "codigo_postal", nullable = true)
    @Comment("Código postal de la ciudad. (opcional)")
    private String codigoPostal;

    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Lista de barrios que pertenecen a esta ciudad")
    private List<Barrio> barrios;

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
