package py.com.housesolutions.ubicaciones.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MonedaResponseDTO {
    private Long id;
    private String name;
    private String code;
    private String simbolo;
    private String isoNum;
    private Float minorUnit;
    private String notas;
    private Estado estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
