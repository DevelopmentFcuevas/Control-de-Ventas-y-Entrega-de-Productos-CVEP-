/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     18/5/2023 22:36:54                           */
/*==============================================================*/


drop table auditoria;

drop table barrios;

drop table categorias;

drop table ciudades;

drop table clientes;

drop table colores;

drop table compras;

drop table contactos;

drop table correos_electronicos;

drop table departamentos;

drop table detallescompras;

drop table direcciones;

drop table documentos;

drop table marcas;

drop table paises;

drop table permisos;

drop table personas;

drop table productos;

drop table productos_unidadmedida;

drop table proveedores;

drop table roles;

drop table roles_permisos;

drop table tipos;

drop table unidad_medida;

drop table usuarios;

drop table usuarios_roles;

drop domain identificador;

/*==============================================================*/
/* Domain: identificador                                        */
/*==============================================================*/
create domain identificador as char(10);

/*==============================================================*/
/* Table: auditoria                                             */
/*==============================================================*/
create table auditoria (
   id_                  serial               not null,
   fecha_               date                 not null,
   usuario              varchar(255)         not null,
   tabla                varchar(255)         not null,
   accion               varchar(255)         not null,
   valores_anteriores   text                 not null,
   valores_nuevos       text                 not null,
   constraint pk_auditoria primary key (id_)
);

/*==============================================================*/
/* Table: barrios                                               */
/*==============================================================*/
create table barrios (
   id                   int4                 not null,
   ciudad_id            int8                 not null,
   nombre               varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_barrios primary key (id)
);

/*==============================================================*/
/* Table: categorias                                            */
/*==============================================================*/
create table categorias (
   id                   serial               not null,
   nombre               varchar(255)         not null,
   descripcion          varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_categorias primary key (id)
);

/*==============================================================*/
/* Table: ciudades                                              */
/*==============================================================*/
create table ciudades (
   id                   serial               not null,
   departamento_id      int8                 not null,
   nombre               varchar(255)         not null,
   codigo_postal        varchar(255)         null,
   poblacion            int8                 null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_ciudades primary key (id)
);

/*==============================================================*/
/* Table: clientes                                              */
/*==============================================================*/
create table clientes (
   id                   serial               not null,
   direccion_id         int8                 not null,
   numero_cliente       varchar(255)         not null,
   fecha_registro       date                 null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_clientes primary key (id)
);

/*==============================================================*/
/* Table: colores                                               */
/*==============================================================*/
create table colores (
   id                   serial               not null,
   nombre               varchar(255)         not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_colores primary key (id)
);

/*==============================================================*/
/* Table: compras                                               */
/*==============================================================*/
create table compras (
   id                   int4                 not null,
   proveedor_id         int8                 not null,
   factura_numero       varchar(255)         not null,
   fecha                date                 not null,
   fecha_vencimiento    date                 null,
   total                decimal              not null,
   total_exenta         decimal              null,
   total_iva5           decimal              null,
   total_iva10          decimal              null,
   total_iva            decimal              null,
   detalles             varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_compras primary key (id)
);

/*==============================================================*/
/* Table: contactos                                             */
/*==============================================================*/
create table contactos (
   id                   int4                 not null,
   persona_id           int8                 not null,
   tipo_id              int8                 null,
   nombre               varchar(255)         null,
   telefono             varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_contactos primary key (id)
);

/*==============================================================*/
/* Table: correos_electronicos                                  */
/*==============================================================*/
create table correos_electronicos (
   id                   int4                 not null,
   persona_id           int8                 not null,
   tipo_id              int8                 not null,
   descripcion          varchar(255)         not null,
   principal            bool                 null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_correos_electronicos primary key (id)
);

/*==============================================================*/
/* Table: departamentos                                         */
/*==============================================================*/
create table departamentos (
   id                   int4                 not null,
   pais_id              int8                 not null,
   nombre               varchar(255)         not null,
   codigo_iso           varchar(255)         null,
   capital              varchar(255)         null,
   poblacion            int8                 null,
   estado               varchar(255)         null,
   superficie           decimal              null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_departamentos primary key (id)
);

/*==============================================================*/
/* Table: detallescompras                                       */
/*==============================================================*/
create table detallescompras (
   id                   serial               not null,
   producto_unidadmedida_id int8                 not null,
   compra_id            int8                 not null,
   cantidad             int4                 not null,
   precio_costo_unitario decimal              not null,
   subtotal             decimal              null,
   iva_exenta           decimal              null,
   iva5                 decimal              null,
   iva10                decimal              null,
   total                decimal              not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_detallescompras primary key (id)
);

/*==============================================================*/
/* Table: direcciones                                           */
/*==============================================================*/
create table direcciones (
   id                   int8                 not null,
   persona_id           int8                 not null,
   barrio_id            int8                 not null,
   tipo_id              int8                 null,
   calle_principal      varchar(255)         not null,
   calle_secundaria     varchar(255)         null,
   numero_casa          varchar(255)         null,
   informacion_adicional_ varchar(255)         null,
   piso                 varchar(255)         null,
   apartamento          varchar(255)         null,
   codigo_postal        varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_direcciones primary key (id)
);

/*==============================================================*/
/* Table: documentos                                            */
/*==============================================================*/
create table documentos (
   id                   int8                 not null,
   persona_id           int8                 not null,
   tipo_id              int8                 null,
   numero               varchar(255)         not null,
   fecha_emision        date                 null,
   fecha_expiracion     date                 null,
   lugar_emision        int8                 null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_documentos primary key (id)
);

/*==============================================================*/
/* Table: marcas                                                */
/*==============================================================*/
create table marcas (
   id                   serial               not null,
   nombre               varchar(255)         not null,
   descripcion          varchar(255)         null,
   pais_origen          int8                 null,
   fecha_fundacion      date                 null,
   sitio_web            varchar(255)         null,
   contacto             varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_marcas primary key (id)
);

/*==============================================================*/
/* Table: paises                                                */
/*==============================================================*/
create table paises (
   id                   serial               not null,
   nombre               varchar(255)         not null,
   codigo_iso2          varchar(255)         null,
   codigo_iso3          varchar(255)         null,
   capital              varchar(255)         null,
   poblacion            int8                 null,
   area                 decimal              null,
   idioma               varchar(255)         null,
   moneda               varchar(255)         null,
   dominio_tld          varchar(255)         null,
   huso_horario         varchar(255)         null,
   continente           varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_paises primary key (id)
);

/*==============================================================*/
/* Table: permisos                                              */
/*==============================================================*/
create table permisos (
   id                   serial not null,
   nombre               varchar(255)         not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_permisos primary key (id)
);

/*==============================================================*/
/* Table: personas                                              */
/*==============================================================*/
create table personas (
   id                   serial not null,
   nombre               varchar(255)         not null,
   apellido             varchar(255)         null,
   fecha_nac            date                 null,
   genero               varchar(255)         null 
      constraint ckc_genero_personas check (genero is null or (genero in ('MASCULINO','FEMENINO','OTROS'))),
   lugar_nac            int8                 null,
   estado               varchar(255)         null,
   created_by           varchar(255)         null,
   created_at           date                 null,
   updated_by           varchar(255)         null,
   updated_at           date                 null,
   deleted              bool                 null,
   deleted_by           varchar(255)         null,
   deleted_at           date                 null,
   constraint pk_personas primary key (id)
);

/*==============================================================*/
/* Table: productos                                             */
/*==============================================================*/
create table productos (
   id                   int4                 not null,
   marca_id             int8                 not null,
   color_id             int8                 null,
   nombre               varchar(255)         not null,
   descripcion          varchar(255)         null,
   codigo_producto      varchar(255)         null,
   porcentaje_iva       decimal              null,
   fecha_creacion       date                 null,
   procedencia          int8                 null,
   categoria_id         int8                 null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_productos primary key (id)
);

/*==============================================================*/
/* Table: productos_unidadmedida                                */
/*==============================================================*/
create table productos_unidadmedida (
   id                   int4                 not null,
   producto_id          int8                 not null,
   unidad_medida_id     int8                 null,
   precio               decimal              null,
   stock                int4                 null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_productos_unidadmedida primary key (id)
);

/*==============================================================*/
/* Table: proveedores                                           */
/*==============================================================*/
create table proveedores (
   id                   serial               not null,
   persona_id           int8                 not null,
   sitio_web            varchar(255)         null,
   contacto_nombre      varchar(255)         null,
   contacto_telefono    varchar(255)         null,
   contacto_correo_electronico varchar(255)         null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_proveedores primary key (id)
);

/*==============================================================*/
/* Table: roles                                                 */
/*==============================================================*/
create table roles (
   id                   serial not null,
   nombre               varchar(255)         not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_roles primary key (id)
);

/*==============================================================*/
/* Table: roles_permisos                                        */
/*==============================================================*/
create table roles_permisos (
   id                   int4                 not null,
   rol_id               int8                 not null,
   permiso_id           int8                 not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_roles_permisos primary key (id, rol_id, permiso_id)
);

/*==============================================================*/
/* Table: tipos                                                 */
/*==============================================================*/
create table tipos (
   id                   int8                 not null,
   nombre               varchar(255)         not null,
   tabla_asociada       varchar(255)         not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_tipos primary key (id)
);

/*==============================================================*/
/* Table: unidad_medida                                         */
/*==============================================================*/
create table unidad_medida (
   id                   serial               not null,
   nombre               varchar(255)         not null,
   descripcion          varchar(255)         null,
   tipo_id              int8                 not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_unidad_medida primary key (id)
);

/*==============================================================*/
/* Table: usuarios                                              */
/*==============================================================*/
create table usuarios (
   id                   serial not null,
   persona_id           int8                 not null,
   nombre_usuario       varchar(255)         not null,
   contrasena           varchar(255)         not null,
   correo_electronico   varchar(255)         null,
   ultimo_inicio_sesion date                 null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_usuarios primary key (id)
);

/*==============================================================*/
/* Table: usuarios_roles                                        */
/*==============================================================*/
create table usuarios_roles (
   id                   int4                 not null,
   usuario_id           int8                 not null,
   rol_id               int8                 not null,
   estado               varchar(255)         null,
   created              date                 null,
   updated              date                 null,
   deleted              date                 null,
   constraint pk_usuarios_roles primary key (id, usuario_id, rol_id)
);

alter table barrios
   add constraint fk_barrios_ciudades foreign key (ciudad_id)
      references ciudades (id)
      on delete cascade on update cascade;

alter table ciudades
   add constraint fk_ciudades__departamentos foreign key (departamento_id)
      references departamentos (id)
      on delete cascade on update cascade;

alter table compras
   add constraint fk_compras_proveedores foreign key (proveedor_id)
      references proveedores (id)
      on delete cascade on update cascade;

alter table contactos
   add constraint fk_contactos_personas foreign key (persona_id)
      references personas (id)
      on delete cascade on update cascade;

alter table contactos
   add constraint fk_contactos_tipos foreign key (tipo_id)
      references tipos (id)
      on delete cascade on update cascade;

alter table correos_electronicos
   add constraint fk_correoselectronicos_personas foreign key (persona_id)
      references personas (id)
      on delete cascade on update cascade;

alter table correos_electronicos
   add constraint fk_correoselectronicos_tipos foreign key (tipo_id)
      references tipos (id)
      on delete cascade on update cascade;

alter table departamentos
   add constraint fk_departamentos_paises foreign key (pais_id)
      references paises (id)
      on delete cascade on update cascade;

alter table detallescompras
   add constraint fk_detallescompras_compras foreign key (compra_id)
      references compras (id)
      on delete cascade on update cascade;

alter table detallescompras
   add constraint fk_detalles_detallesc_producto foreign key (producto_unidadmedida_id)
      references productos_unidadmedida (id)
      on delete cascade on update cascade;

alter table direcciones
   add constraint fk_direccio_direccion_barrios foreign key (barrio_id)
      references barrios (id)
      on delete cascade on update cascade;

alter table direcciones
   add constraint fk_direcciones_personas foreign key (persona_id)
      references personas (id)
      on delete cascade on update cascade;

alter table direcciones
   add constraint fk_direcciones_tipos foreign key (tipo_id)
      references tipos (id)
      on delete cascade on update cascade;

alter table documentos
   add constraint fk_documentos_ciudades foreign key (lugar_emision)
      references ciudades (id)
      on delete cascade on update cascade;

alter table documentos
   add constraint fk_documentos_personas foreign key (persona_id)
      references personas (id)
      on delete cascade on update cascade;

alter table documentos
   add constraint fk_documentos_tipos foreign key (tipo_id)
      references tipos (id)
      on delete cascade on update cascade;

alter table personas
   add constraint fk_personas__ciudades foreign key (lugar_nac)
      references ciudades (id)
      on delete cascade on update cascade;

alter table productos
   add constraint fk_productos_categorias foreign key (categoria_id)
      references categorias (id)
      on delete cascade on update cascade;

alter table productos
   add constraint fk_productos_colores foreign key (color_id)
      references colores (id)
      on delete cascade on update cascade;

alter table productos
   add constraint fk_productos_marcas foreign key (marca_id)
      references marcas (id)
      on delete cascade on update cascade;

alter table productos_unidadmedida
   add constraint fk_producto_productos_unidad_m foreign key (unidad_medida_id)
      references unidad_medida (id)
      on delete cascade on update cascade;

alter table productos_unidadmedida
   add constraint fk_unidadmedida_productos foreign key (producto_id)
      references productos (id)
      on delete cascade on update cascade;

alter table proveedores
   add constraint fk_proveedores_personas foreign key (persona_id)
      references personas (id)
      on delete cascade on update cascade;

alter table roles_permisos
   add constraint fk_rolespermisos_permisos foreign key (permiso_id)
      references permisos (id)
      on delete cascade on update cascade;

alter table roles_permisos
   add constraint fk_rolespermisos_roles foreign key (rol_id)
      references roles (id)
      on delete cascade on update cascade;

alter table unidad_medida
   add constraint fk_unidadmedida_tipos foreign key (tipo_id)
      references tipos (id)
      on delete cascade on update cascade;

alter table usuarios
   add constraint fk_usuarios_personas foreign key (persona_id)
      references personas (id)
      on delete cascade on update cascade;

alter table usuarios_roles
   add constraint fk_usuarios_usuariosr_roles foreign key (rol_id)
      references roles (id)
      on delete cascade on update cascade;

alter table usuarios_roles
   add constraint fk_usuariosroles_usuarios foreign key (usuario_id)
      references usuarios (id)
      on delete cascade on update cascade;

