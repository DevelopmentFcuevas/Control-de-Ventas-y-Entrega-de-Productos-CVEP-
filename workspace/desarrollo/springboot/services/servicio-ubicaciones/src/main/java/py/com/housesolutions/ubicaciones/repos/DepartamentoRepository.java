package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.housesolutions.ubicaciones.domain.Departamento;

import java.util.List;
import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    @Query("SELECT d FROM Departamento d WHERE d.deleted = false")
    List<Departamento> findAllActive();

    @Query("SELECT d FROM Departamento d WHERE d.id = :id AND d.deleted = false")
    Optional<Departamento> findByIdAndNotDeleted(@Param("id") Long id);


    //Antes de insertar un nuevo registro, verificar que no exista otro
    // con el mismo nombre y que no esté marcado como eliminado.
    @Query("SELECT d FROM Departamento d WHERE d.name = :name AND d.pais.id = :paisId AND d.deleted = true")
    Optional<Departamento> findByNameAndDeleted(@Param("name") String name, @Param("paisId") Long paisId);

}
