package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.Estado;
import py.com.housesolutions.ubicaciones.model.PaisListadoProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    /**
     * Busca un objeto {@code Pais} por su identificador único (ID), excluyendo
     * aquellos que hayan sido marcados como eliminados (soft-delete).
     *
     * @param id El identificador único (ID) del país a buscar. Debe ser no nulo.
     * @return Un {@code Optional} que contiene el objeto {@code Pais} si se
     * encuentra y {@code deleted = false}; de lo contrario, un
     * {@code Optional#empty()}.
     */
    @Query("SELECT p FROM Pais p WHERE p.id = :id AND p.deleted = false")
    Optional<Pais> findByIdAndNotDeleted(@Param("id") Long id);

    /**
     * Busca un objeto {@code Pais} por su nombre, excluyendo aquellos que hayan
     * sido marcados como eliminados (soft-delete). La búsqueda por nombre
     * generalmente es sensible a mayúsculas/minúsculas a menos que la base de
     * datos esté configurada de otra manera o se use una función SQL como UPPER/LOWER.
     *
     * @param name El nombre exacto del país a buscar. Debe ser no nulo.
     * @return Un {@code Optional} que contiene el objeto {@code Pais} si se
     * encuentra y {@code deleted = false}; de lo contrario, un
     * {@code Optional#empty()}.
     */
    @Query("SELECT p FROM Pais p WHERE p.name = :name AND p.deleted = false")
    Optional<Pais> findByNameAndNotDeleted(@Param("name") String name);

    /**
     * Obtiene una lista proyectada de países activos (no eliminados), incluyendo
     * información relevante como nombre, códigos ISO, capital, población, área,
     * idioma, TLD, huso horario, continente, estado y fechas de creación/
     * actualización. Además, incorpora el nombre de la moneda oficial mediante
     * una relación a {@code PaisMoneda}.
     *
     * Este método utiliza proyecciones para mejorar el rendimiento, evitando
     * cargar entidades completas cuando no es necesario.
     *
     * @return Una lista de {@code PaisListadoProjection} representando todos los
     * países activos.
     */
    @Query("SELECT p.id AS id, " +
            "p.name AS name, " +
            "p.codigoIso2 AS codigoIso2, " +
            "p.codigoIso3 AS codigoIso3, " +
            "p.capital AS capital, " +
            "p.poblacion AS poblacion, " +
            "p.area AS area, " +
            "p.idioma AS idioma, " +
            "p.dominioTld AS dominioTld, " +
            "p.husoHorario AS husoHorario, " +
            "p.continente AS continente, " +
            "p.estado AS estado, " +
            "p.createdAt AS createdAt, " +
            "p.updatedAt AS updatedAt, " +
            "m.name AS moneda " +
            "FROM Pais p " +
            "LEFT JOIN PaisMoneda pm ON pm.pais = p AND pm.esOficial = true AND pm.deleted = false " +
            "LEFT JOIN Moneda m ON pm.moneda = m WHERE p.deleted = false")
    List<PaisListadoProjection> findAllActive();

    /**
     * Cuenta el número total de países activos (no eliminados) que se encuentran
     * en el estado especificado.
     *
     * @param estado El estado del país (por ejemplo, ACTIVO, INACTIVO).
     * @return La cantidad total de países activos con el estado indicado.
     */
    @Query("SELECT COUNT(p) FROM Pais p WHERE p.deleted = false AND p.estado = :estado")
    long countByEstadoAndNotDeleted(@Param("estado") Estado estado);

    /**
     * Cuenta la cantidad de países activos (no eliminados) que fueron creados
     * exactamente en la fecha indicada. Utilizado principalmente para métricas
     * o dashboards que muestran nuevos registros diarios.
     *
     * @param fecha La fecha en la cual fueron creados los países.
     * @return El número total de países creados en la fecha especificada.
     */
    @Query("SELECT COUNT(p) FROM Pais p WHERE p.deleted = false AND DATE(p.createdAt) = :fecha")
    long countCreatedToday(@Param("fecha") LocalDate fecha);
}
