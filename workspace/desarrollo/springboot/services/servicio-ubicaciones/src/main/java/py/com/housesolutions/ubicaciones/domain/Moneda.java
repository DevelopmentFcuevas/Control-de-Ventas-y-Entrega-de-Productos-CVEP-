package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monedas", schema = "ubicaciones")
@Getter
@Setter
@Comment("Tabla que almacena las monedas disponibles en el sistema. Un país puede usar más de una moneda (oficial, co-oficial, legal tender, uso local, etc.).")
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Identificador único del registro.")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Comment("Nombre de la moneda")
    private String name;

    @Column(name = "code", nullable = true)
    @Comment("Código abreviado de la moneda por ejemplo: USD, PYG")
    private String code;

    @Column(name = "simbolo", nullable = true)
    @Comment("Símbolo utilizado por la moneda por ejemplo: $, Gs")
    private String simbolo;

    @Column(name = "iso_num", nullable = true)
    @Comment("Código o número ISO por ejemplo: 840, 600,")
    private String isoNum;

    @Column(name = "minor_unit", nullable = true)
    @Comment("Cantidad de decimales/Unidad menor")
    private Float minorUnit;

    @Column(name = "notas", nullable = true)
    @Comment("Alguna descripción, comentario u observación sobre esta moneda.")
    private String notas;

    @OneToMany(mappedBy = "moneda")
    private List<PaisMoneda> paisMonedas = new ArrayList<>();

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
