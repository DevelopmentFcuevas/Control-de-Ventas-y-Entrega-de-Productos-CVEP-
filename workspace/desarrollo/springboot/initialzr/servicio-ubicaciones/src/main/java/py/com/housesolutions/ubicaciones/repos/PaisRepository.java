package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.Estado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    //Excluir Países Eliminados en Consultas

    @Query("SELECT p FROM Pais p WHERE p.deleted = false")
    List<Pais> findAllActive();

    @Query("SELECT p FROM Pais p WHERE p.id = :id AND p.deleted = false")
    Optional<Pais> findByIdAndNotDeleted(@Param("id") Long id);

    @Query("SELECT p FROM Pais p WHERE p.name = :name AND p.deleted = false")
    Optional<Pais> findByNameAndNotDeleted(@Param("name") String id);

    //Antes de insertar un nuevo registro, verificar que no exista otro
    // con el mismo nombre y que no esté marcado como eliminado.
    //@Query("select (count(p) > 0) from Pais p where p.name = ?1 and p.deleted <> ?2")
    //boolean existsByNameAndDeletedNot(String name, boolean deleted);
    @Query("SELECT p FROM Pais p WHERE p.name = :name AND p.deleted = true")
    Optional<Pais> findByNameAndDeleted(@Param("name") String name);

    // Total de Países
    @Query("SELECT COUNT(p) FROM Pais p WHERE p.deleted = false")
    long countAllNotDeleted();

    // Total de Países Activos
    @Query("SELECT COUNT(p) FROM Pais p WHERE p.deleted = false AND p.estado = :estado")
    long countByEstadoAndNotDeleted(@Param("estado") Estado estado);

    // Total de nuevos Países Activos agregados hoy.
    @Query("SELECT COUNT(p) FROM Pais p WHERE p.deleted = false AND DATE(p.createdAt) = :fecha")
    long countCreatedToday(@Param("fecha") LocalDate fecha);

}
