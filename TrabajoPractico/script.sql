/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 10                       */
/* Created on:     26/07/2010 21:48:52                          */
/*==============================================================*/


if exists(select 1 from sys.sysforeignkey where role='Empleado=Empleado') then
    alter table Ajustes_stock
       delete foreign key "Empleado=Empleado"
end if;

if exists(select 1 from sys.sysforeignkey where role='Empleado=Autorizante') then
    alter table Ajustes_stock
       delete foreign key "Empleado=Autorizante"
end if;

if exists(select 1 from sys.sysforeignkey where role='Ajuste=Ajuste') then
    alter table Ajustes_stock_det
       delete foreign key "Ajuste=Ajuste"
end if;

if exists(select 1 from sys.sysforeignkey where role='Producto=Producto,Udm=Udm') then
    alter table Ajustes_stock_det
       delete foreign key "Producto=Producto,Udm=Udm"
end if;

if exists(select 1 from sys.sysforeignkey where role='Motivo_ajuste=Motivo_ajuste') then
    alter table Ajustes_stock_det
       delete foreign key "Motivo_ajuste=Motivo_ajuste"
end if;

if exists(select 1 from sys.sysforeignkey where role='Ciudad=Ciudad') then
    alter table Clientes
       delete foreign key "Ciudad=Ciudad"
end if;

if exists(select 1 from sys.sysforeignkey where role='Proveedor=Proveedor') then
    alter table Compras
       delete foreign key "Proveedor=Proveedor"
end if;

if exists(select 1 from sys.sysforeignkey where role='Compra=Compra') then
    alter table Compras_det
       delete foreign key "Compra=Compra"
end if;

if exists(select 1 from sys.sysforeignkey where role='Producto=Producto,Udm=Udm') then
    alter table Compras_det
       delete foreign key "Producto=Producto,Udm=Udm"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pedido=Pedido') then
    alter table Empaques_det
       delete foreign key "Pedido=Pedido"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pedido=Pedido') then
    alter table Entregas
       delete foreign key "Pedido=Pedido"
end if;

if exists(select 1 from sys.sysforeignkey where role='Repartidor=Repartidor') then
    alter table Entregas
       delete foreign key "Repartidor=Repartidor"
end if;

if exists(select 1 from sys.sysforeignkey where role='Vehiculo=Vehiculo') then
    alter table Entregas
       delete foreign key "Vehiculo=Vehiculo"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pedido=Pedido') then
    alter table Facturas_venta
       delete foreign key "Pedido=Pedido"
end if;

if exists(select 1 from sys.sysforeignkey where role='Nro_factura=Nro_factura') then
    alter table Facturas_venta_det
       delete foreign key "Nro_factura=Nro_factura"
end if;

if exists(select 1 from sys.sysforeignkey where role='Producto=Producto,Udm=Udm') then
    alter table Facturas_venta_det
       delete foreign key "Producto=Producto,Udm=Udm"
end if;

if exists(select 1 from sys.sysforeignkey where role='Cliente=Cliente') then
    alter table Pedidos
       delete foreign key "Cliente=Cliente"
end if;

if exists(select 1 from sys.sysforeignkey where role='Empleado=Empleado') then
    alter table Pedidos
       delete foreign key "Empleado=Empleado"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pedido=Pedido') then
    alter table Pedidos_det
       delete foreign key "Pedido=Pedido"
end if;

if exists(select 1 from sys.sysforeignkey where role='Producto=Producto,Udm=Udm') then
    alter table Pedidos_det
       delete foreign key "Producto=Producto,Udm=Udm"
end if;

if exists(select 1 from sys.sysforeignkey where role='Cliente=Cliente') then
    alter table Pedidos_no_realizados
       delete foreign key "Cliente=Cliente"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pedido_no_realizado=Pedigo_no_realizado') then
    alter table Pedidos_no_realizados_det
       delete foreign key "Pedido_no_realizado=Pedigo_no_realizado"
end if;

if exists(select 1 from sys.sysforeignkey where role='Producto=Producto,Udm=Udm') then
    alter table Pedidos_no_realizados_det
       delete foreign key "Producto=Producto,Udm=Udm"
end if;

if exists(select 1 from sys.sysforeignkey where role='Marca=Marca') then
    alter table Productos
       delete foreign key "Marca=Marca"
end if;

if exists(select 1 from sys.sysforeignkey where role='Color=Color') then
    alter table Productos
       delete foreign key "Color=Color"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pais=Pais') then
    alter table Productos
       delete foreign key "Pais=Pais"
end if;

if exists(select 1 from sys.sysforeignkey where role='Tipo_Producto=Tipo_Producto') then
    alter table Productos
       delete foreign key "Tipo_Producto=Tipo_Producto"
end if;

if exists(select 1 from sys.sysforeignkey where role='Producto=Producto') then
    alter table Productos_x_Udm
       delete foreign key "Producto=Producto"
end if;

if exists(select 1 from sys.sysforeignkey where role='Udm=Udm') then
    alter table Productos_x_Udm
       delete foreign key "Udm=Udm"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pedido=Pedido') then
    alter table Rastreo_pedido
       delete foreign key "Pedido=Pedido"
end if;

if exists(select 1 from sys.sysforeignkey where role='Pedido=Pedido') then
    alter table Reporte_pedido
       delete foreign key "Pedido=Pedido"
end if;

if exists(select 1 from sys.sysforeignkey where role='Repartidor=Repartidor') then
    alter table Reporte_pedido
       delete foreign key "Repartidor=Repartidor"
end if;

if exists(select 1 from sys.sysforeignkey where role='Motivo_no_entrega=Motivo_no_entrega') then
    alter table Reporte_pedido
       delete foreign key "Motivo_no_entrega=Motivo_no_entrega"
end if;

if exists(select 1 from sys.sysforeignkey where role='Cliente=Cliente') then
    alter table Telefonos
       delete foreign key "Cliente=Cliente"
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Ajustes_stock'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Ajustes_stock
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Ajustes_stock_det'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Ajustes_stock_det
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Ciudades'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Ciudades
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Clientes'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Clientes
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Colores'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Colores
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Compras'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Compras
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Compras_det'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Compras_det
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Empaques_det'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Empaques_det
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Empleados'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Empleados
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Entregas'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Entregas
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Facturas_venta'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Facturas_venta
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Facturas_venta_det'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Facturas_venta_det
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Marcas'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Marcas
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Motivos_Ajustes'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Motivos_Ajustes
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Motivos_de_no_entregas'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Motivos_de_no_entregas
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Paises'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Paises
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Pedidos'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Pedidos
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Pedidos_det'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Pedidos_det
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Pedidos_no_realizados'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Pedidos_no_realizados
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Pedidos_no_realizados_det'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Pedidos_no_realizados_det
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Productos'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Productos
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Productos_x_Udm'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Productos_x_Udm
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Proveedores'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Proveedores
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Rastreo_pedido'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Rastreo_pedido
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Repartidores'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Repartidores
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Reporte_pedido'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Reporte_pedido
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Telefonos'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Telefonos
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Tipos_Productos'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Tipos_Productos
end if;

if exists(
   select 1 from sys.systable 
   where table_name='UDMS'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table UDMS
end if;

if exists(
   select 1 from sys.systable 
   where table_name='Vehiculos'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table Vehiculos
end if;

if exists(select 1 from sys.sysusertype where type_name='AJUSTE') then
   drop datatype AJUSTE
end if;

if exists(select 1 from sys.sysusertype where type_name='CANTIDAD') then
   drop datatype CANTIDAD
end if;

if exists(select 1 from sys.sysusertype where type_name='CEDULA') then
   drop datatype CEDULA
end if;

if exists(select 1 from sys.sysusertype where type_name='CIUDAD') then
   drop datatype CIUDAD
end if;

if exists(select 1 from sys.sysusertype where type_name='CLIENTE') then
   drop datatype CLIENTE
end if;

if exists(select 1 from sys.sysusertype where type_name='COLOR') then
   drop datatype COLOR
end if;

if exists(select 1 from sys.sysusertype where type_name='COMPRA') then
   drop datatype COMPRA
end if;

if exists(select 1 from sys.sysusertype where type_name='CONFIRMADO') then
   drop datatype CONFIRMADO
end if;

if exists(select 1 from sys.sysusertype where type_name='CONTACTO') then
   drop datatype CONTACTO
end if;

if exists(select 1 from sys.sysusertype where type_name='CONTRASENA') then
   drop datatype CONTRASENA
end if;

if exists(select 1 from sys.sysusertype where type_name='DESCRIPCION') then
   drop datatype DESCRIPCION
end if;

if exists(select 1 from sys.sysusertype where type_name='DIRECCION') then
   drop datatype DIRECCION
end if;

if exists(select 1 from sys.sysusertype where type_name='EMPAQUE') then
   drop datatype EMPAQUE
end if;

if exists(select 1 from sys.sysusertype where type_name='EMPLEADO') then
   drop datatype EMPLEADO
end if;

if exists(select 1 from sys.sysusertype where type_name='ENTREGA') then
   drop datatype ENTREGA
end if;

if exists(select 1 from sys.sysusertype where type_name='ESTADO') then
   drop datatype ESTADO
end if;

if exists(select 1 from sys.sysusertype where type_name='EXENTA') then
   drop datatype EXENTA
end if;

if exists(select 1 from sys.sysusertype where type_name='FECHA') then
   drop datatype FECHA
end if;

if exists(select 1 from sys.sysusertype where type_name='FECHAHORA') then
   drop datatype FECHAHORA
end if;

if exists(select 1 from sys.sysusertype where type_name='HORA') then
   drop datatype HORA
end if;

if exists(select 1 from sys.sysusertype where type_name='IVA_10') then
   drop datatype IVA_10
end if;

if exists(select 1 from sys.sysusertype where type_name='IVA_5') then
   drop datatype IVA_5
end if;

if exists(select 1 from sys.sysusertype where type_name='MARCA') then
   drop datatype MARCA
end if;

if exists(select 1 from sys.sysusertype where type_name='MOTIVO_AJUSTE') then
   drop datatype MOTIVO_AJUSTE
end if;

if exists(select 1 from sys.sysusertype where type_name='MOTIVO_NO_ENTREGA') then
   drop datatype MOTIVO_NO_ENTREGA
end if;

if exists(select 1 from sys.sysusertype where type_name='NOMBRE') then
   drop datatype NOMBRE
end if;

if exists(select 1 from sys.sysusertype where type_name='NUMERO') then
   drop datatype NUMERO
end if;

if exists(select 1 from sys.sysusertype where type_name='NUMERO_CHAPA') then
   drop datatype NUMERO_CHAPA
end if;

if exists(select 1 from sys.sysusertype where type_name='PAIS') then
   drop datatype PAIS
end if;

if exists(select 1 from sys.sysusertype where type_name='PEDIDO') then
   drop datatype PEDIDO
end if;

if exists(select 1 from sys.sysusertype where type_name='PEDIDO_NO_REALIZADO') then
   drop datatype PEDIDO_NO_REALIZADO
end if;

if exists(select 1 from sys.sysusertype where type_name='PORCENTAJE_IVA') then
   drop datatype PORCENTAJE_IVA
end if;

if exists(select 1 from sys.sysusertype where type_name='PRECIO') then
   drop datatype PRECIO
end if;

if exists(select 1 from sys.sysusertype where type_name='PRODUCTO') then
   drop datatype PRODUCTO
end if;

if exists(select 1 from sys.sysusertype where type_name='PROVEEDOR') then
   drop datatype PROVEEDOR
end if;

if exists(select 1 from sys.sysusertype where type_name='REFERENCIA') then
   drop datatype REFERENCIA
end if;

if exists(select 1 from sys.sysusertype where type_name='REPARTIDOR') then
   drop datatype REPARTIDOR
end if;

if exists(select 1 from sys.sysusertype where type_name='RUC') then
   drop datatype RUC
end if;

if exists(select 1 from sys.sysusertype where type_name='SIGNO') then
   drop datatype SIGNO
end if;

if exists(select 1 from sys.sysusertype where type_name='TELEFONO') then
   drop datatype TELEFONO
end if;

if exists(select 1 from sys.sysusertype where type_name='TIPO') then
   drop datatype TIPO
end if;

if exists(select 1 from sys.sysusertype where type_name='TIPO_PRODUCTO') then
   drop datatype TIPO_PRODUCTO
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL') then
   drop datatype TOTAL
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_COMPRAS_GENERAL') then
   drop datatype TOTAL_COMPRAS_GENERAL
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_EXENTA') then
   drop datatype TOTAL_EXENTA
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_EXENTA_GENERAL') then
   drop datatype TOTAL_EXENTA_GENERAL
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_GENERAL_PEDIDO') then
   drop datatype TOTAL_GENERAL_PEDIDO
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_IVA') then
   drop datatype TOTAL_IVA
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_IVA10') then
   drop datatype TOTAL_IVA10
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_IVA10_GENERAL') then
   drop datatype TOTAL_IVA10_GENERAL
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_IVA5') then
   drop datatype TOTAL_IVA5
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_IVA5_GENERAL') then
   drop datatype TOTAL_IVA5_GENERAL
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_IVA_GENERAL') then
   drop datatype TOTAL_IVA_GENERAL
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_PRODUCTO') then
   drop datatype TOTAL_PRODUCTO
end if;

if exists(select 1 from sys.sysusertype where type_name='TOTAL_VENTA_GENERAL') then
   drop datatype TOTAL_VENTA_GENERAL
end if;

if exists(select 1 from sys.sysusertype where type_name='UBICACION') then
   drop datatype UBICACION
end if;

if exists(select 1 from sys.sysusertype where type_name='UDM') then
   drop datatype UDM
end if;

if exists(select 1 from sys.sysusertype where type_name='VEHICULO') then
   drop datatype VEHICULO
end if;

/*==============================================================*/
/* Domain: AJUSTE                                               */
/*==============================================================*/
create domain AJUSTE as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: CANTIDAD                                             */
/*==============================================================*/
create domain CANTIDAD as integer;

/*==============================================================*/
/* Domain: CEDULA                                               */
/*==============================================================*/
create domain CEDULA as varchar(15);

/*==============================================================*/
/* Domain: CIUDAD                                               */
/*==============================================================*/
create domain CIUDAD as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: CLIENTE                                              */
/*==============================================================*/
create domain CLIENTE as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: COLOR                                                */
/*==============================================================*/
create domain COLOR as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: COMPRA                                               */
/*==============================================================*/
create domain COMPRA as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: CONFIRMADO                                           */
/*==============================================================*/
create domain CONFIRMADO as char(1) 
     check (@column is null or ( @column in ('S','N') ));

/*==============================================================*/
/* Domain: CONTACTO                                             */
/*==============================================================*/
create domain CONTACTO as varchar(40);

/*==============================================================*/
/* Domain: CONTRASENA                                           */
/*==============================================================*/
create domain CONTRASENA as varchar(30);

/*==============================================================*/
/* Domain: DESCRIPCION                                          */
/*==============================================================*/
create domain DESCRIPCION as varchar(90);

/*==============================================================*/
/* Domain: DIRECCION                                            */
/*==============================================================*/
create domain DIRECCION as varchar(50);

/*==============================================================*/
/* Domain: EMPAQUE                                              */
/*==============================================================*/
create domain EMPAQUE as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: EMPLEADO                                             */
/*==============================================================*/
create domain EMPLEADO as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: ENTREGA                                              */
/*==============================================================*/
create domain ENTREGA as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: ESTADO                                               */
/*==============================================================*/
create domain ESTADO as char(2) 
     check (@column is null or ( @column in ('EN','NO') ));

/*==============================================================*/
/* Domain: EXENTA                                               */
/*==============================================================*/
create domain EXENTA as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: FECHA                                                */
/*==============================================================*/
create domain FECHA as date;

/*==============================================================*/
/* Domain: FECHAHORA                                            */
/*==============================================================*/
create domain FECHAHORA as datetime;

/*==============================================================*/
/* Domain: HORA                                                 */
/*==============================================================*/
create domain HORA as time;

/*==============================================================*/
/* Domain: IVA_10                                               */
/*==============================================================*/
create domain IVA_10 as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: IVA_5                                                */
/*==============================================================*/
create domain IVA_5 as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: MARCA                                                */
/*==============================================================*/
create domain MARCA as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: MOTIVO_AJUSTE                                        */
/*==============================================================*/
create domain MOTIVO_AJUSTE as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: MOTIVO_NO_ENTREGA                                    */
/*==============================================================*/
create domain MOTIVO_NO_ENTREGA as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: NOMBRE                                               */
/*==============================================================*/
create domain NOMBRE as varchar(40);

/*==============================================================*/
/* Domain: NUMERO                                               */
/*==============================================================*/
create domain NUMERO as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: NUMERO_CHAPA                                         */
/*==============================================================*/
create domain NUMERO_CHAPA as varchar(20);

/*==============================================================*/
/* Domain: PAIS                                                 */
/*==============================================================*/
create domain PAIS as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: PEDIDO                                               */
/*==============================================================*/
create domain PEDIDO as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: PEDIDO_NO_REALIZADO                                  */
/*==============================================================*/
create domain PEDIDO_NO_REALIZADO as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: PORCENTAJE_IVA                                       */
/*==============================================================*/
create domain PORCENTAJE_IVA as numeric(3) 
     check (@column is null or (@column between 1 and 999 ));

/*==============================================================*/
/* Domain: PRECIO                                               */
/*==============================================================*/
create domain PRECIO as integer 
     check (@column is null or (@column >= 1 ));

/*==============================================================*/
/* Domain: PRODUCTO                                             */
/*==============================================================*/
create domain PRODUCTO as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: PROVEEDOR                                            */
/*==============================================================*/
create domain PROVEEDOR as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: REFERENCIA                                           */
/*==============================================================*/
create domain REFERENCIA as varchar(90);

/*==============================================================*/
/* Domain: REPARTIDOR                                           */
/*==============================================================*/
create domain REPARTIDOR as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: RUC                                                  */
/*==============================================================*/
create domain RUC as varchar(20);

/*==============================================================*/
/* Domain: SIGNO                                                */
/*==============================================================*/
create domain SIGNO as char(1) 
     check (@column is null or ( @column in ('+','-') ));

/*==============================================================*/
/* Domain: TELEFONO                                             */
/*==============================================================*/
create domain TELEFONO as varchar(30);

/*==============================================================*/
/* Domain: TIPO                                                 */
/*==============================================================*/
create domain TIPO as varchar(90);

/*==============================================================*/
/* Domain: TIPO_PRODUCTO                                        */
/*==============================================================*/
create domain TIPO_PRODUCTO as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: TOTAL                                                */
/*==============================================================*/
create domain TOTAL as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_COMPRAS_GENERAL                                */
/*==============================================================*/
create domain TOTAL_COMPRAS_GENERAL as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_EXENTA                                         */
/*==============================================================*/
create domain TOTAL_EXENTA as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_EXENTA_GENERAL                                 */
/*==============================================================*/
create domain TOTAL_EXENTA_GENERAL as numeric(10) 
     check (@column is null or (@column between 1 and 9999999999 ));

/*==============================================================*/
/* Domain: TOTAL_GENERAL_PEDIDO                                 */
/*==============================================================*/
create domain TOTAL_GENERAL_PEDIDO as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_IVA                                            */
/*==============================================================*/
create domain TOTAL_IVA as numeric(10) 
     check (@column is null or (@column between 1 and 9999999999 ));

/*==============================================================*/
/* Domain: TOTAL_IVA10                                          */
/*==============================================================*/
create domain TOTAL_IVA10 as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_IVA10_GENERAL                                  */
/*==============================================================*/
create domain TOTAL_IVA10_GENERAL as numeric(10) 
     check (@column is null or (@column between 1 and 9999999999 ));

/*==============================================================*/
/* Domain: TOTAL_IVA5                                           */
/*==============================================================*/
create domain TOTAL_IVA5 as numeric(15) 
     check (@column is null or (@column between 1 and 9999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_IVA5_GENERAL                                   */
/*==============================================================*/
create domain TOTAL_IVA5_GENERAL as numeric(10) 
     check (@column is null or (@column between 1 and 9999999999 ));

/*==============================================================*/
/* Domain: TOTAL_IVA_GENERAL                                    */
/*==============================================================*/
create domain TOTAL_IVA_GENERAL as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_PRODUCTO                                       */
/*==============================================================*/
create domain TOTAL_PRODUCTO as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: TOTAL_VENTA_GENERAL                                  */
/*==============================================================*/
create domain TOTAL_VENTA_GENERAL as numeric(15) 
     check (@column is null or (@column between 1 and 999999999999999 ));

/*==============================================================*/
/* Domain: UBICACION                                            */
/*==============================================================*/
create domain UBICACION as varchar(90);

/*==============================================================*/
/* Domain: UDM                                                  */
/*==============================================================*/
create domain UDM as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Domain: VEHICULO                                             */
/*==============================================================*/
create domain VEHICULO as numeric(5) 
     check (@column is null or (@column between 1 and 99999 ));

/*==============================================================*/
/* Table: Ajustes_stock                                         */
/*==============================================================*/
create table Ajustes_stock 
(
    Ajuste               AJUSTE                         not null,
    FechaHora            FECHAHORA                      not null,
    Empleado             EMPLEADO                       not null,
    Autorizante          EMPLEADO                       not null,
    constraint PK_AJUSTES_STOCK primary key (Ajuste)
);

/*==============================================================*/
/* Table: Ajustes_stock_det                                     */
/*==============================================================*/
create table Ajustes_stock_det 
(
    Ajuste               AJUSTE                         not null,
    Producto             PRODUCTO                       not null,
    Udm                  UDM                            not null,
    Motivo_ajuste        MOTIVO_AJUSTE                  not null,
    Signo                SIGNO                          not null,
    Cantidad             CANTIDAD                       not null,
    constraint PK_AJUSTES_STOCK_DET primary key (Ajuste, Producto, Udm)
);

/*==============================================================*/
/* Table: Ciudades                                              */
/*==============================================================*/
create table Ciudades 
(
    Ciudad               CIUDAD                         not null,
    Nombre               NOMBRE                         not null,
    constraint PK_CIUDADES primary key (Ciudad)
);

/*==============================================================*/
/* Table: Clientes                                              */
/*==============================================================*/
create table Clientes 
(
    Cliente              CLIENTE                        not null,
    Nombre               NOMBRE                         not null,
    Direccion            DIRECCION                      not null,
    Contacto             CONTACTO                       not null,
    RUC                  RUC                            not null,
    Referencia_Direccion REFERENCIA                     not null,
    Ciudad               CIUDAD                         not null,
    constraint PK_CLIENTES primary key (Cliente)
);

/*==============================================================*/
/* Table: Colores                                               */
/*==============================================================*/
create table Colores 
(
    Color                COLOR                          not null,
    Nombre               NOMBRE                         not null,
    constraint PK_COLORES primary key (Color)
);

/*==============================================================*/
/* Table: Compras                                               */
/*==============================================================*/
create table Compras 
(
    Compra               COMPRA                         not null,
    Nro_Factura          NUMERO                         not null,
    Fecha                FECHA                          not null,
    Proveedor            PROVEEDOR                      not null,
    Fecha_Vencimiento_pago FECHA                          not null,
    Total_compras_general TOTAL_COMPRAS_GENERAL          not null,
    Total_exenta_general TOTAL_EXENTA_GENERAL           not null,
    Total_IVA5_general   TOTAL_IVA5_GENERAL             not null,
    Total_IVA10_general  TOTAL_IVA10_GENERAL            not null,
    Total_IVA            TOTAL_IVA                      not null,
    constraint PK_COMPRAS primary key (Compra)
);

/*==============================================================*/
/* Table: Compras_det                                           */
/*==============================================================*/
create table Compras_det 
(
    Producto             PRODUCTO                       not null,
    Udm                  UDM                            not null,
    Compra               COMPRA                         not null,
    Cantidad             CANTIDAD                       not null,
    Precio_costo_unitario PRECIO                         not null,
    Porcentaje_IVA       PORCENTAJE_IVA                 not null,
    Total                TOTAL                          not null,
    Exenta               EXENTA                         not null,
    IVA_5                IVA_5                          not null,
    IVA_10               IVA_10                         not null,
    constraint PK_COMPRAS_DET primary key (Producto, Udm, Compra)
);

/*==============================================================*/
/* Table: Empaques_det                                          */
/*==============================================================*/
create table Empaques_det 
(
    Pedido               PEDIDO                         not null,
    Empaque              EMPAQUE                        not null,
    Fecha_entrega_solicitada FECHA                          not null,
    constraint PK_EMPAQUES_DET primary key (Pedido, Empaque)
);

/*==============================================================*/
/* Table: Empleados                                             */
/*==============================================================*/
create table Empleados 
(
    Empleado             EMPLEADO                       not null,
    Nombre               NOMBRE                         not null,
    Direccion            DIRECCION                      not null,
    Telefono             TELEFONO,
    Contrasena           CONTRASENA                     not null,
    constraint PK_EMPLEADOS primary key (Empleado)
);

/*==============================================================*/
/* Table: Entregas                                              */
/*==============================================================*/
create table Entregas 
(
    Entrega              ENTREGA                        not null,
    Pedido               PEDIDO                         not null,
    Fecha                FECHA                          not null,
    Repartidor           REPARTIDOR                     not null,
    Vehiculo             VEHICULO                       not null,
    FechaHora_recepcion  FECHAHORA                      not null,
    constraint PK_ENTREGAS primary key (Entrega, Pedido)
);

/*==============================================================*/
/* Table: Facturas_venta                                        */
/*==============================================================*/
create table Facturas_venta 
(
    Nro_Factura          NUMERO                         not null,
    Fecha_emision        FECHA                          not null,
    Pedido               PEDIDO                         not null,
    Fecha_pedido         FECHA                          not null,
    Cliente              CLIENTE                        not null,
    Nombre_Cliente       NOMBRE                         not null,
    Fecha_vencimiento    FECHA                          not null,
    Total_venta_general  TOTAL_VENTA_GENERAL            not null,
    Total_exenta_general TOTAL_EXENTA_GENERAL,
    Total_IVA10_general  TOTAL_IVA10_GENERAL,
    Total_IVA5_general   TOTAL_IVA5_GENERAL,
    Total_IVA_general    TOTAL_IVA_GENERAL,
    constraint PK_FACTURAS_VENTA primary key (Nro_Factura)
);

/*==============================================================*/
/* Table: Facturas_venta_det                                    */
/*==============================================================*/
create table Facturas_venta_det 
(
    Nro_Factura          NUMERO                         not null,
    Producto             PRODUCTO                       not null,
    Udm                  UDM                            not null,
    Descripcion          DESCRIPCION                    not null,
    Cantidad             CANTIDAD                       not null,
    Precio_unitario_venta PRECIO                         not null,
    Porcentaje_IVA       PORCENTAJE_IVA                 not null,
    Total_producto       TOTAL_PRODUCTO                 not null,
    Total_exenta         TOTAL_EXENTA                   not null,
    Total_IVA10          TOTAL_IVA10                    not null,
    Total_IVA5           TOTAL_IVA5                     not null,
    constraint PK_FACTURAS_VENTA_DET primary key (Nro_Factura, Producto, Udm)
);

/*==============================================================*/
/* Table: Marcas                                                */
/*==============================================================*/
create table Marcas 
(
    Marca                MARCA                          not null,
    Nombre               NOMBRE                         not null,
    constraint PK_MARCAS primary key (Marca)
);

/*==============================================================*/
/* Table: Motivos_Ajustes                                       */
/*==============================================================*/
create table Motivos_Ajustes 
(
    Motivo_ajuste        MOTIVO_AJUSTE                  not null,
    Descripcion          DESCRIPCION                    not null,
    constraint PK_MOTIVOS_AJUSTES primary key (Motivo_ajuste)
);

/*==============================================================*/
/* Table: Motivos_de_no_entregas                                */
/*==============================================================*/
create table Motivos_de_no_entregas 
(
    Motivo_no_entrega    MOTIVO_NO_ENTREGA              not null,
    Descripcion          DESCRIPCION                    not null,
    constraint PK_MOTIVOS_DE_NO_ENTREGAS primary key (Motivo_no_entrega)
);

/*==============================================================*/
/* Table: Paises                                                */
/*==============================================================*/
create table Paises 
(
    Pais                 PAIS                           not null,
    Nombre               NOMBRE                         not null,
    constraint PK_PAISES primary key (Pais)
);

/*==============================================================*/
/* Table: Pedidos                                               */
/*==============================================================*/
create table Pedidos 
(
    Pedido               PEDIDO                         not null,
    Cliente              CLIENTE                        not null,
    Nombre_Solicitante   NOMBRE                         not null,
    FechaHora_entrega    FECHAHORA                      not null,
    Total_general_pedigo TOTAL_GENERAL_PEDIDO           not null,
    Confirmado           CONFIRMADO                     not null,
    Empleado             EMPLEADO                       not null,
    Estado               ESTADO                         not null,
    constraint PK_PEDIDOS primary key (Pedido)
);

/*==============================================================*/
/* Table: Pedidos_det                                           */
/*==============================================================*/
create table Pedidos_det 
(
    Pedido               PEDIDO                         not null,
    Producto             PRODUCTO                       not null,
    Udm                  UDM                            not null,
    Cantidad             CANTIDAD                       not null,
    Total_Producto       TOTAL_PRODUCTO                 not null,
    constraint PK_PEDIDOS_DET primary key (Pedido, Producto, Udm)
);

/*==============================================================*/
/* Table: Pedidos_no_realizados                                 */
/*==============================================================*/
create table Pedidos_no_realizados 
(
    Pedido_no_realizado  PEDIDO_NO_REALIZADO            not null,
    Cliente              CLIENTE                        not null,
    FechaHora            FECHAHORA                      not null,
    constraint PK_PEDIDOS_NO_REALIZADOS primary key (Pedido_no_realizado)
);

/*==============================================================*/
/* Table: Pedidos_no_realizados_det                             */
/*==============================================================*/
create table Pedidos_no_realizados_det 
(
    Pedido_no_realizado  PEDIDO_NO_REALIZADO            not null,
    Producto             PRODUCTO                       not null,
    Udm                  UDM                            not null,
    Cantidad             CANTIDAD                       not null,
    Total_Producto       TOTAL_PRODUCTO                 not null,
    constraint PK_PEDIDOS_NO_REALIZADOS_DET primary key (Pedido_no_realizado, Producto, Udm)
);

/*==============================================================*/
/* Table: Productos                                             */
/*==============================================================*/
create table Productos 
(
    Producto             PRODUCTO                       not null,
    Nombre               NOMBRE                         not null,
    Marca                MARCA                          not null,
    Color                COLOR                          not null,
    Porcentaje_IVA       PORCENTAJE_IVA                 not null,
    Descripcion_Producto DESCRIPCION                    not null,
    Pais                 PAIS                           not null,
    Tipo_Producto        TIPO_PRODUCTO                  not null,
    constraint PK_PRODUCTOS primary key (Producto)
);

/*==============================================================*/
/* Table: Productos_x_Udm                                       */
/*==============================================================*/
create table Productos_x_Udm 
(
    Producto             PRODUCTO                       not null,
    Udm                  UDM                            not null,
    Precio_Venta         PRECIO,
    Existencia           CANTIDAD,
    constraint PK_PRODUCTOS_X_UDM primary key (Producto, Udm)
);

/*==============================================================*/
/* Table: Proveedores                                           */
/*==============================================================*/
create table Proveedores 
(
    Proveedor            PROVEEDOR                      not null,
    Nombre               NOMBRE                         not null,
    Direccion            DIRECCION                      not null,
    RUC                  RUC,
    constraint PK_PROVEEDORES primary key (Proveedor)
);

/*==============================================================*/
/* Table: Rastreo_pedido                                        */
/*==============================================================*/
create table Rastreo_pedido 
(
    Pedido               PEDIDO                         not null,
    Motivo_no_entrega    MOTIVO_NO_ENTREGA              not null,
    Ubicacion            UBICACION                      not null,
    constraint PK_RASTREO_PEDIDO primary key (Pedido, Ubicacion)
);

/*==============================================================*/
/* Table: Repartidores                                          */
/*==============================================================*/
create table Repartidores 
(
    Repartidor           REPARTIDOR                     not null,
    Nombre               NOMBRE                         not null,
    Direccion            DIRECCION                      not null,
    Telefono             TELEFONO,
    constraint PK_REPARTIDORES primary key (Repartidor)
);

/*==============================================================*/
/* Table: Reporte_pedido                                        */
/*==============================================================*/
create table Reporte_pedido 
(
    Pedido               PEDIDO                         not null,
    Repartidor           REPARTIDOR                     not null,
    FechaHora_entrega    FECHAHORA                      not null,
    Cedula_recepcion     CEDULA,
    Nombre_recepcion     NOMBRE                         not null,
    Entregado            CONFIRMADO                     not null,
    Motivo_no_entrega    MOTIVO_NO_ENTREGA              not null,
    Hora_retorno         HORA                           not null,
    constraint PK_REPORTE_PEDIDO primary key (Pedido, Repartidor)
);

/*==============================================================*/
/* Table: Telefonos                                             */
/*==============================================================*/
create table Telefonos 
(
    Telefono             TELEFONO                       not null,
    Cliente              CLIENTE                        not null,
    constraint PK_TELEFONOS primary key (Telefono, Cliente)
);

/*==============================================================*/
/* Table: Tipos_Productos                                       */
/*==============================================================*/
create table Tipos_Productos 
(
    Tipo_Producto        TIPO_PRODUCTO                  not null,
    Nombre               NOMBRE                         not null,
    constraint PK_TIPOS_PRODUCTOS primary key (Tipo_Producto)
);

/*==============================================================*/
/* Table: UDMS                                                  */
/*==============================================================*/
create table UDMS 
(
    Udm                  UDM                            not null,
    Nombre               NOMBRE                         not null,
    constraint PK_UDMS primary key (Udm)
);

/*==============================================================*/
/* Table: Vehiculos                                             */
/*==============================================================*/
create table Vehiculos 
(
    Vehiculo             VEHICULO                       not null,
    Tipo                 TIPO                           not null,
    Nro_chapa            NUMERO_CHAPA,
    constraint PK_VEHICULOS primary key (Vehiculo)
);

alter table Ajustes_stock
   add constraint "Empleado=Empleado" foreign key (Empleado)
      references Empleados (Empleado)
      on update restrict
      on delete restrict;

alter table Ajustes_stock
   add constraint "Empleado=Autorizante" foreign key (Autorizante)
      references Empleados (Empleado)
      on update restrict
      on delete restrict;

alter table Ajustes_stock_det
   add constraint "Ajuste=Ajuste" foreign key (Ajuste)
      references Ajustes_stock (Ajuste)
      on update restrict
      on delete restrict;

alter table Ajustes_stock_det
   add constraint "Producto=Producto,Udm=Udm" foreign key (Producto, Udm)
      references Productos_x_Udm (Producto, Udm)
      on update restrict
      on delete restrict;

alter table Ajustes_stock_det
   add constraint "Motivo_ajuste=Motivo_ajuste" foreign key (Motivo_ajuste)
      references Motivos_Ajustes (Motivo_ajuste)
      on update restrict
      on delete restrict;

alter table Clientes
   add constraint "Ciudad=Ciudad" foreign key (Ciudad)
      references Ciudades (Ciudad)
      on update restrict
      on delete restrict;

alter table Compras
   add constraint "Proveedor=Proveedor" foreign key (Proveedor)
      references Proveedores (Proveedor)
      on update restrict
      on delete restrict;

alter table Compras_det
   add constraint "Compra=Compra" foreign key (Compra)
      references Compras (Compra)
      on update restrict
      on delete restrict;

alter table Compras_det
   add constraint "Producto=Producto,Udm=Udm" foreign key (Producto, Udm)
      references Productos_x_Udm (Producto, Udm)
      on update restrict
      on delete restrict;

alter table Empaques_det
   add constraint "Pedido=Pedido" foreign key (Pedido)
      references Pedidos (Pedido)
      on update restrict
      on delete restrict;

alter table Entregas
   add constraint "Pedido=Pedido" foreign key (Pedido)
      references Pedidos (Pedido)
      on update restrict
      on delete restrict;

alter table Entregas
   add constraint "Repartidor=Repartidor" foreign key (Repartidor)
      references Repartidores (Repartidor)
      on update restrict
      on delete restrict;

alter table Entregas
   add constraint "Vehiculo=Vehiculo" foreign key (Vehiculo)
      references Vehiculos (Vehiculo)
      on update restrict
      on delete restrict;

alter table Facturas_venta
   add constraint "Pedido=Pedido" foreign key (Pedido)
      references Pedidos (Pedido)
      on update restrict
      on delete restrict;

alter table Facturas_venta_det
   add constraint "Nro_factura=Nro_factura" foreign key (Nro_Factura)
      references Facturas_venta (Nro_Factura)
      on update restrict
      on delete restrict;

alter table Facturas_venta_det
   add constraint "Producto=Producto,Udm=Udm" foreign key (Producto, Udm)
      references Productos_x_Udm (Producto, Udm)
      on update restrict
      on delete restrict;

alter table Pedidos
   add constraint "Cliente=Cliente" foreign key (Cliente)
      references Clientes (Cliente)
      on update restrict
      on delete restrict;

alter table Pedidos
   add constraint "Empleado=Empleado" foreign key (Empleado)
      references Empleados (Empleado)
      on update restrict
      on delete restrict;

alter table Pedidos_det
   add constraint "Pedido=Pedido" foreign key (Pedido)
      references Pedidos (Pedido)
      on update restrict
      on delete restrict;

alter table Pedidos_det
   add constraint "Producto=Producto,Udm=Udm" foreign key (Producto, Udm)
      references Productos_x_Udm (Producto, Udm)
      on update restrict
      on delete restrict;

alter table Pedidos_no_realizados
   add constraint "Cliente=Cliente" foreign key (Cliente)
      references Clientes (Cliente)
      on update restrict
      on delete restrict;

alter table Pedidos_no_realizados_det
   add constraint "Pedido_no_realizado=Pedigo_no_realizado" foreign key (Pedido_no_realizado)
      references Pedidos_no_realizados (Pedido_no_realizado)
      on update restrict
      on delete restrict;

alter table Pedidos_no_realizados_det
   add constraint "Producto=Producto,Udm=Udm" foreign key (Producto, Udm)
      references Productos_x_Udm (Producto, Udm)
      on update restrict
      on delete restrict;

alter table Productos
   add constraint "Marca=Marca" foreign key (Marca)
      references Marcas (Marca)
      on update restrict
      on delete restrict;

alter table Productos
   add constraint "Color=Color" foreign key (Color)
      references Colores (Color)
      on update restrict
      on delete restrict;

alter table Productos
   add constraint "Pais=Pais" foreign key (Pais)
      references Paises (Pais)
      on update restrict
      on delete restrict;

alter table Productos
   add constraint "Tipo_Producto=Tipo_Producto" foreign key (Tipo_Producto)
      references Tipos_Productos (Tipo_Producto)
      on update restrict
      on delete restrict;

alter table Productos_x_Udm
   add constraint "Producto=Producto" foreign key (Producto)
      references Productos (Producto)
      on update restrict
      on delete restrict;

alter table Productos_x_Udm
   add constraint "Udm=Udm" foreign key (Udm)
      references UDMS (Udm)
      on update restrict
      on delete restrict;

alter table Rastreo_pedido
   add constraint "Pedido=Pedido" foreign key (Pedido)
      references Pedidos (Pedido)
      on update restrict
      on delete restrict;

alter table Reporte_pedido
   add constraint "Pedido=Pedido" foreign key (Pedido)
      references Pedidos (Pedido)
      on update restrict
      on delete restrict;

alter table Reporte_pedido
   add constraint "Repartidor=Repartidor" foreign key (Repartidor)
      references Repartidores (Repartidor)
      on update restrict
      on delete restrict;

alter table Reporte_pedido
   add constraint "Motivo_no_entrega=Motivo_no_entrega" foreign key (Motivo_no_entrega)
      references Motivos_de_no_entregas (Motivo_no_entrega)
      on update restrict
      on delete restrict;

alter table Telefonos
   add constraint "Cliente=Cliente" foreign key (Cliente)
      references Clientes (Cliente)
      on update restrict
      on delete restrict;

