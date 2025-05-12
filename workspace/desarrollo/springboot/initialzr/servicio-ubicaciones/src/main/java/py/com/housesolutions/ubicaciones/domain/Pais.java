package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import py.com.housesolutions.ubicaciones.model.Continente;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "paises", schema = "ubicaciones")
@Getter
@Setter
@Comment("Tabla que almacena los países disponibles en el sistema.")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Identificador único del registro.")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Comment("Nombre del país")
    private String name;

    @Column(name = "codigo_iso2", nullable = true)
    @Comment("Código ISO de 2 letras del país")
    private String codigoIso2;

    @Column(name = "codigo_iso3", nullable = true)
    @Comment("Código ISO de 3 letras del país")
    private String codigoIso3;

    @Column(name = "capital", nullable = true)
    @Comment("Capital del país")
    private String capital;

    @Column(name = "poblacion", nullable = true)
    @Comment("Cantidad de la población")
    private Integer poblacion;

    @Column(name = "area", nullable = true)
    @Comment("Área total del país (en km²)")
    private BigDecimal area /*= BigDecimal.ZERO*/;

    @Column(name = "idioma", nullable = true)
    @Comment("Idioma oficial del país")
    private String idioma;

    @Column(name = "moneda", nullable = true)
    @Comment("Moneda oficial del país")
    private String moneda;

    @Column(name = "dominio_tld", nullable = true)
    @Comment("Dominio de nivel superior del país")
    private String dominioTld;

    @Column(name = "huso_horario", nullable = true)
    @Comment("Huso horario del país")
    private String husoHorario;

    @Column(name = "continente", nullable = true, columnDefinition = "varchar(255) default 'SIN_ESPECIFICAR'")
    @Enumerated(EnumType.STRING)
    @Comment("Continente en el que se encuentra el país")
    private Continente continente /*= Continente.SIN_ESPECIFICAR*/;

    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Departamento> departamentos;

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
