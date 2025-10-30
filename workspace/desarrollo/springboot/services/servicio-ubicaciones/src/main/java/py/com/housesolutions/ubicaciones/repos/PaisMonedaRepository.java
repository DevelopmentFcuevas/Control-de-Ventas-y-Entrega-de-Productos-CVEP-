package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.housesolutions.ubicaciones.domain.Moneda;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.domain.PaisMoneda;

import java.util.List;
import java.util.Optional;

public interface PaisMonedaRepository extends JpaRepository<PaisMoneda, Long> {

    /**/
    @Query("SELECT pm FROM PaisMoneda pm WHERE pm.deleted = false")
    List<PaisMoneda> findAllActive();

    @Query("SELECT pm FROM PaisMoneda pm WHERE pm.id = :id AND pm.deleted = false")
    Optional<PaisMoneda> findByIdAndNotDeleted(@Param("id") Long id);

    //Antes de insertar un nuevo registro, verificar que no exista otro
    // con el mismo IdPais/IdMoneda y que no esté marcado como eliminado.
    @Query("SELECT pm FROM PaisMoneda pm WHERE pm.pais = :paisId AND pm.moneda = :monedaId AND pm.deleted = true")
    Optional<PaisMoneda> findByPaisIdAndMonedaIdAndDeleted(@Param("paisId") Long paisId, @Param("monedaId") Long monedaId);

    Optional<PaisMoneda> findByPais_IdAndEsPrimaria(Long id, Boolean esPrimaria);

    Optional<PaisMoneda> findByPais_IdAndMoneda_Id(Long paisId, Long monedaId);

    List<PaisMoneda> findByPais_Id(Long paisId);

    @Query("SELECT pm FROM PaisMoneda pm WHERE pm.pais.id = :paisId AND pm.deleted = false")
    List<PaisMoneda> findAllByPais_IdAndNotDeleted(Long paisId);
    /**/

    /*
    boolean existsByPaisAndMoneda(Pais pais, Moneda moneda);
    Optional<PaisMoneda> findByPaisAndMoneda(Pais pais, Moneda moneda);
    List<PaisMoneda> findByPaisId(Long paisId);
    List<PaisMoneda> findByMonedaId(Long monedaId);
    void deleteByPaisAndMoneda(Pais pais, Moneda moneda);
    //Optional<PaisMoneda> findByPaisIdAndEsPrimaria(Long paisId, Boolean esPrimaria);
    */
}
