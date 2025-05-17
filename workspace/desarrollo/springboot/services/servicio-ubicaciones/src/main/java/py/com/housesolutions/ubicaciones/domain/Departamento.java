package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import py.com.housesolutions.ubicaciones.model.Estado;
import py.com.housesolutions.ubicaciones.model.Region;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "departamentos", schema = "ubicaciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "pais_id"})
})
@Getter
@Setter
@Comment("Un departamento es la unidad administrativa más grande en Paraguay (ej. \"Central\", \"Alto Paraná\").")
public class Departamento {
    /*
     * Un departamento es la unidad administrativa más grande en
     * Paraguay (ej. "Central", "Alto Paraná").
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Identificador único del registro.")
    private Long id;

    //@ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    @Comment("Identificador del país al que pertenece el departamento")
    Pais pais;

    @Column(name = "name", nullable = false)
    @Comment("Nombre del departamento")
    private String name;

    @Column(name = "codigo_iso", nullable = true)
    @Comment("Código ISO del departamento (opcional)")
    private String codigoIso;

    @Column(name = "capital", nullable = true)
    @Comment("Capital del departamento")
    private String capital;

    @Column(name = "poblacion", nullable = true)
    @Comment("Población del departamento")
    private Integer poblacion;

    @Column(name = "superficie", nullable = true)
    @Comment("Superficie total del departamento (en km²)")
    private BigDecimal superficie = BigDecimal.ZERO;

    @Column(columnDefinition = "varchar(255) default 'SIN_ESPECIFICAR'")
    @Enumerated(EnumType.STRING)
    private Region region = Region.SIN_ESPECIFICAR;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("Lista de ciudades que pertenecen a este departamento")
    private List<Ciudad> ciudades;

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
