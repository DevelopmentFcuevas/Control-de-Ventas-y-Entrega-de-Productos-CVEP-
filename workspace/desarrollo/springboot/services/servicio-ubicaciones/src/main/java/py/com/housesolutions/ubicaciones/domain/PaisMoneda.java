package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pais_monedas", schema = "ubicaciones",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"pais_id", "moneda_id"})}
)
@Getter
@Setter
@Comment("Tabla que almacena las monedas por país disponibles en el sistema.")
public class PaisMoneda {
    //@EmbeddedId
    //private PaisMonedaId id = new PaisMonedaId();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    //@MapsId("paisId")
    @JoinColumn(name = "pais_id", nullable = false)
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    //@MapsId("monedaId")
    @JoinColumn(name = "moneda_id", nullable = false)
    private Moneda moneda;

    // ✅ Atributos extra de la relación
    @Column(name = "es_oficial")
    private Boolean esOficial = Boolean.TRUE; // valor por defecto

    @Column(name = "es_primaria")
    private Boolean esPrimaria = Boolean.TRUE; // valor por defecto

    @Column(name = "valido_desde")
    private LocalDate validoDesde;

    @Column(name = "valido_hasta")
    private LocalDate validoHasta;

    @Column(name = "estado", nullable = true, columnDefinition = "varchar(255) default 'ACTIVO'")
    @Enumerated(EnumType.STRING)
    @Comment("Estado actual del registro.")
    private Estado estado;

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
