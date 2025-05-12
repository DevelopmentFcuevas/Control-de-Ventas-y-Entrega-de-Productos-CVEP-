package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.housesolutions.ubicaciones.domain.Ciudad;

import java.util.List;
import java.util.Optional;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
    @Query("SELECT c FROM Ciudad c WHERE c.deleted = false")
    List<Ciudad> findAllActive();

    @Query("SELECT c FROM Ciudad c WHERE c.id = :id AND c.deleted = false")
    Optional<Ciudad> findByIdAndNotDeleted(@Param("id") Long id);

    //Antes de insertar un nuevo registro, verificar que no exista otro
    // con el mismo nombre y que no esté marcado como eliminado.
    @Query("SELECT c FROM Ciudad c WHERE c.name = :name AND c.departamento.id = :departamentoId AND c.deleted = true")
    Optional<Ciudad> findByNameAndDeleted(@Param("name") String name, @Param("departamentoId") Long departamentoId);
}
