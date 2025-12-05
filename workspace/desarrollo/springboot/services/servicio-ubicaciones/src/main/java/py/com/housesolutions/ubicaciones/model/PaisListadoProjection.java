package py.com.housesolutions.ubicaciones.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaisListadoProjection {
    Long getId();
    String getName();
    String getCodigoIso2();
    String getCodigoIso3();
    String getCapital();
    Integer getPoblacion();
    BigDecimal getArea();
    String getIdioma();
    String getDominioTld();
    String getHusoHorario();
    Continente getContinente();
    Estado getEstado();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    // moneda principal
    String getMoneda();
}
