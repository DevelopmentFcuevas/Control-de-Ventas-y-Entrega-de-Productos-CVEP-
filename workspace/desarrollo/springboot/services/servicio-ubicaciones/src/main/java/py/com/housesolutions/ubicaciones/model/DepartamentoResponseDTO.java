package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DepartamentoResponseDTO {
    private Long id;
    PaisResponseDTO pais;
    private String name;
    private String codigoIso;
    private String capital;
    private Integer poblacion;
    private BigDecimal superficie;
    private Region region;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
