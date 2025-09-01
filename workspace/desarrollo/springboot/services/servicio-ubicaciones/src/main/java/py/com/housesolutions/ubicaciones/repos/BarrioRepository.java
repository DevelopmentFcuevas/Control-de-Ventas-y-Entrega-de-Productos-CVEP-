package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.housesolutions.ubicaciones.domain.Barrio;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BarrioRepository extends JpaRepository<Barrio, Long> {
    @Query("SELECT b FROM Barrio b WHERE b.deleted = false")
    List<Barrio> findAllActive();

    @Query("SELECT b FROM Barrio b WHERE b.id = :id AND b.deleted = false")
    Optional<Barrio> findByIdAndNotDeleted(@Param("id") Long id);

    //Antes de insertar un nuevo registro, verificar que no exista otro
    // con el mismo nombre y que no esté marcado como eliminado.
    @Query("SELECT b FROM Barrio b WHERE b.name = :name AND b.ciudad.id = :ciudadId AND b.deleted = true")
    Optional<Barrio> findByNameAndDeleted(@Param("name") String name, @Param("ciudadId") Long ciudadId);

    // Total de Barrios Activos
    @Query("SELECT COUNT(b) FROM Barrio b WHERE b.deleted = false AND b.estado = :estado")
    long countByEstadoAndNotDeleted(@Param("estado") Estado estado);

    // Total de nuevos Barrios Activos agregados hoy.
    @Query("SELECT COUNT(b) FROM Barrio b WHERE b.deleted = false AND DATE(b.createdAt) = :fecha")
    long countCreatedToday(@Param("fecha") LocalDate fecha);
}
