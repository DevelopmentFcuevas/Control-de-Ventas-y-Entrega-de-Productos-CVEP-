/*
package py.com.housesolutions.ubicaciones.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaisMonedaId implements Serializable {
    @Column(name = "pais_id")
    private Long paisId;

    @Column(name = "moneda_id")
    private Long monedaId;

    // equals & hashCode son obligatorios para claves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaisMonedaId that = (PaisMonedaId) o;
        return Objects.equals(paisId, that.paisId) && Objects.equals(monedaId, that.monedaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paisId, monedaId);
    }
}
*/