package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.housesolutions.ubicaciones.domain.Moneda;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MonedaRepository extends JpaRepository<Moneda, Long> {
    @Query("SELECT m FROM Moneda m WHERE m.deleted = false")
    List<Moneda> findAllActive();

    @Query("SELECT m FROM Moneda m WHERE m.id = :id AND m.deleted = false")
    Optional<Moneda> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT m FROM Moneda m WHERE m.name = :name AND m.deleted = false")
    Optional<Moneda> findByNameAndNotDeleted(@Param("name") String id);

    //Antes de insertar un nuevo registro, verificar que no exista otro
    // con el mismo nombre y que no esté marcado como eliminado.
    @Query("SELECT m FROM Moneda m WHERE m.name = :name AND m.deleted = true")
    Optional<Moneda> findByNameAndDeleted(@Param("name") String name);

    // Total de Monedas
    @Query("SELECT COUNT(m) FROM Moneda m WHERE m.deleted = false")
    long countAllNotDeleted();

    // Total de Monedas Activos
    @Query("SELECT COUNT(m) FROM Moneda m WHERE m.deleted = false AND m.estado = :estado")
    long countByEstadoAndNotDeleted(@Param("estado") Estado estado);

    // Total de nuevas Monedas Activos agregados hoy.
    @Query("SELECT COUNT(m) FROM Moneda m WHERE m.deleted = false AND DATE(m.createdAt) = :fecha")
    long countCreatedToday(@Param("fecha") LocalDate fecha);
}

