package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio encargado de gestionar todas las operaciones relacionadas con
 * la entidad {@link Pais}, incluyendo creación, actualización, eliminación
 * lógica, consultas y operaciones de conteo.
 *
 * También provee métodos de mapeo entre entidades y DTOs para asegurar un
 * transporte de datos desacoplado y seguro entre capas.
 */
public interface PaisService {
    /**
     * Convierte una entidad {@link Pais} en un objeto {@link PaisDTO}.
     *
     * @param entity La entidad País a convertir.
     * @return Un DTO con todas las propiedades del país.
     */
    PaisDTO mapToDTO(final Pais entity);

    /**
     * Convierte una entidad {@link Pais} en un {@link PaisResponseDTO},
     * pensado para respuestas más ligeras al cliente.
     *
     * @param entity La entidad País a convertir.
     * @return Un ResponseDTO con los datos principales del país.
     */
    PaisResponseDTO mapToResponseDTO(Pais entity);

    /**
     * Convierte un objeto {@link PaisDTO} en una entidad {@link Pais}.
     *
     * @param dto DTO con la información del país.
     * @return La entidad construida a partir del DTO.
     */
    Pais mapToEntity(final PaisDTO dto);

    /**
     * Crea un nuevo registro de País y lo persiste en la base de datos.
     *
     * @param request Objeto con los datos requeridos para registrar un país.
     * @return Un DTO de respuesta con los datos del país creado.
     * @throws Exception si ocurre un error en la validación o persistencia.
     */
    PaisResponseDTO create(PaisCreateDTO request) throws Exception;

    /**
     * Realiza una eliminación lógica sobre un país (soft delete).
     *
     * @param id Identificador único del país.
     * @throws Exception si el país no existe o si ocurre un error interno.
     */
    void delete(final Long id) throws Exception;

    /**
     * Actualiza un país existente según los datos provistos.
     *
     * @param id  Identificador del país a actualizar.
     * @param dto DTO con los nuevos valores a aplicar.
     * @return Un DTO con los valores actualizados.
     * @throws Exception si el país no existe o si ocurre un error interno.
     */
    PaisResponseDTO update(Long id, PaisUpdateDTO dto) throws Exception;

    /**
     * Obtiene un listado de todos los países activos.
     *
     * @return Lista de DTOs con información resumida de cada país.
     * @throws Exception si ocurre un error al acceder a la base de datos.
     */
    List<PaisResponseDTO> findAll() throws Exception;

    /**
     * Cuenta cuántos países activos existen según el estado indicado.
     *
     * @param estado Estado a filtrar (ACTIVO, INACTIVO).
     * @return Número total de países encontrados.
     * @throws Exception si ocurre un error en el cálculo.
     */
    long countByEstado(Estado estado) throws Exception;

    /**
     * Cuenta cuántos países fueron creados en una fecha específica.
     *
     * @param fecha Fecha a consultar.
     * @return Cantidad de países creados ese día.
     * @throws Exception si ocurre un error de acceso a la BD.
     */
    long countByFechaCreacion(LocalDate fecha) throws Exception;

    /**
     * Obtiene un país activo por su ID.
     *
     * @param id Identificador único del país.
     * @return Un DTO de respuesta con los datos del país.
     * @throws Exception si el país no existe o ocurre un error.
     */
    PaisResponseDTO get(Long id) throws Exception;

    /**
     * Obtiene todas las propiedades de un país activo por su ID.
     *
     * @param id Identificador único del país.
     * @return Un DTO completo con todas las propiedades del país.
     * @throws Exception si el país no existe.
     */
    PaisDTO getAll(Long id) throws Exception;

    /**
     * Obtiene un país activo según su nombre exacto.
     *
     * @param name Nombre del país.
     * @return Un DTO de respuesta con los datos del país encontrado.
     * @throws Exception si el país no existe o si el parámetro es inválido.
     */
    PaisResponseDTO getByName(String name) throws Exception;
}
