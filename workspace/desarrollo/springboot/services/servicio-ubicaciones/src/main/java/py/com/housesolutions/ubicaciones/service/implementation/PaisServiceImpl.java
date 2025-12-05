package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.PaisRepository;
import py.com.housesolutions.ubicaciones.service.AuditoriaService;
import py.com.housesolutions.ubicaciones.service.PaisMonedaService;
import py.com.housesolutions.ubicaciones.service.PaisService;
import py.com.housesolutions.ubicaciones.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio {@link PaisService}, encargada de gestionar
 * toda la lógica de negocio relacionada con la entidad {@link Pais}.
 *
 * <p>Incluye operaciones de creación, actualización, eliminación lógica,
 * consultas avanzadas, conteos y utilidades de mapeo entre entidades y DTOs.
 *
 * <p>Esta clase está diseñada para trabajar con transacciones declarativas,
 * manejo centralizado de excepciones y validaciones previas al acceso a datos.
 */
@Service
@Slf4j
public class PaisServiceImpl implements PaisService {
    private final PaisRepository repository;
    private final AuditoriaService auditoriaService;
    private final PaisMonedaService paisMonedaService;

    /**
     * Constructor principal de la clase.
     * <p>Recibe el repositorio de acceso a datos mediante inyección de dependencias.
     *
     * @param repository Implementación de {@link PaisRepository} utilizada para
     *                   realizar operaciones CRUD y consultas personalizadas.
     */
    public PaisServiceImpl(PaisRepository repository,
                           AuditoriaService auditoriaService,
                           PaisMonedaService paisMonedaService) {
        this.repository = repository;
        this.auditoriaService = auditoriaService;
        this.paisMonedaService = paisMonedaService;
    }

    // ============================================================ //
    //                   MÉTODOS DE MAPEOS (DTO <-> ENTITY)         //
    // ============================================================ //
    /**
     * Convierte una entidad {@link Pais} en un objeto {@link PaisDTO},
     * proporcionando un mapeo completo de todas sus propiedades.
     *
     * @param entity La entidad que será convertida. No debe ser nula.
     * @return Un objeto {@link PaisDTO} con todos los campos relevantes.
     */
    @Override
    public PaisDTO mapToDTO(Pais entity) {
        log.info("PaisService-mapToDTO::Iniciando Servicio para mapear una entidad País a un DTO");

        PaisDTO dto = new PaisDTO();
        // Mapeo de cada campo de la entidad a su equivalente en el DTO.
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCodigoIso2(entity.getCodigoIso2());
        dto.setCodigoIso3(entity.getCodigoIso3());
        dto.setCapital(entity.getCapital());
        dto.setPoblacion(entity.getPoblacion());
        dto.setArea(entity.getArea());
        dto.setIdioma(entity.getIdioma());
        dto.setDominioTld(entity.getDominioTld());
        dto.setHusoHorario(entity.getHusoHorario());
        dto.setContinente(entity.getContinente());
        dto.setEstado(entity.getEstado());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeleted(entity.isDeleted());
        dto.setDeletedBy(entity.getDeletedBy());
        dto.setDeletedAt(entity.getDeletedAt());

        log.info("PaisService-mapToDTO::Acción completada sin errores.");
        return dto;
    }

    /**
     * Convierte una entidad {@link Pais} en un {@link PaisResponseDTO},
     * pensado para respuestas ligeras hacia el cliente, principalmente
     * para listados o consultas básicas.
     *
     * @param entity Entidad {@link Pais} a convertir.
     * @return Un objeto {@link PaisResponseDTO} con la información esencial.
     */
    @Override
    public PaisResponseDTO mapToResponseDTO(Pais entity) {
        log.info("PaisService-mapToResponseDTO::Iniciando Servicio para mapear una entidad País a un PaisResponseDTO para la respuesta");

        PaisResponseDTO response = new PaisResponseDTO();
        // Mapeo de cada campo de la entidad a su equivalente en el ResponseDTO.
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setCodigoIso2(entity.getCodigoIso2());
        response.setCodigoIso3(entity.getCodigoIso3());
        response.setCapital(entity.getCapital());
        response.setPoblacion(entity.getPoblacion());
        response.setArea(entity.getArea());
        response.setIdioma(entity.getIdioma());
        response.setDominioTld(entity.getDominioTld());
        response.setHusoHorario(entity.getHusoHorario());
        response.setContinente(entity.getContinente());
        response.setEstado(entity.getEstado());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        log.info("PaisService-mapToResponseDTO::Acción completada sin errores.");
        return response;
    }

    /**
     * Convierte un DTO {@link PaisDTO} en una entidad {@link Pais}.
     * <p>Este método es utilizado tanto en creaciones como en actualizaciones.
     *
     * @param dto El DTO a transformar. No debe ser nulo.
     * @return La entidad {@link Pais} resultante del mapeo.
     */
    @Override
    public Pais mapToEntity(PaisDTO dto) {
        log.info("PaisService-mapToEntity::Iniciando Servicio para mapear un DTO a una entidad País");

        Pais entity = new Pais();
        // Mapeo de cada campo del DTO a su equivalente en la entidad.
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCodigoIso2(dto.getCodigoIso2());
        entity.setCodigoIso3(dto.getCodigoIso3());
        entity.setCapital(dto.getCapital());
        entity.setPoblacion(dto.getPoblacion());
        entity.setArea(dto.getArea());
        entity.setIdioma(dto.getIdioma());
        entity.setDominioTld(dto.getDominioTld());
        entity.setHusoHorario(dto.getHusoHorario());
        entity.setContinente(dto.getContinente());
        entity.setEstado(dto.getEstado());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setDeleted(dto.isDeleted());
        entity.setDeletedBy(dto.getDeletedBy());
        entity.setDeletedAt(dto.getDeletedAt());

        log.info("PaisService-mapToEntity::Acción completada sin errores.");
        return entity;
    }

    /**
     * Crea un nuevo registro de {@link Pais} en el sistema.
     * <p>Valida los datos obligatorios antes de persistir y utiliza
     * mapeos DTO-Entity para garantizar separación entre capas.
     *
     * @param dto Objeto {@link PaisCreateDTO} con los datos ingresados por el cliente.
     * @return {@link PaisResponseDTO} con la información del país creado.
     *
     * @throws MissingParameterException si el DTO es nulo o faltan datos requeridos.
     * @throws DataAccessException si ocurre un error al persistir en la base de datos.
     */
    @Override
    public PaisResponseDTO create(PaisCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("PaisService-create::Persistir en la Base de datos el nuevo registro de País");

            if (request == null) {
                //throw new MissingParameterException("El objeto País es requerido para la creación.");
                throw new MissingParameterException("");
            }

            // Se ejecutan dos niveles de validación complementarios, y ambos sirven en momentos distintos:
            // 🔹 1. Validación lógica (antes de guardar)
            // ✅ Esto evita el error antes de intentar persistir, haciendo una consulta al repositorio.
            // Es más limpio, y devuelve un mensaje más amigable al usuario.
            //👉 Ideal para control de negocio y validación lógica.

            // 1️⃣ Validar duplicado
            Optional<Pais> optional = repository.findByNameAndNotDeleted(request.getName());
            if (optional.isPresent()) { // Quiero ejecutar código si hay valor
                //throw new NameAlreadyExistsException("Ya existe un país activo con el nombre '" + request.getName() + "'.");
                throw new NameAlreadyExistsException("");
            }

            // 2️⃣ Mapear y guardar
            PaisDTO dto = new PaisDTO();
            dto.setName(request.getName());
            dto.setCodigoIso2(request.getCodigoIso2());
            dto.setCodigoIso3(request.getCodigoIso3());
            dto.setCapital(request.getCapital());
            dto.setPoblacion(request.getPoblacion());
            dto.setArea(request.getArea());
            dto.setIdioma(request.getIdioma());
            dto.setDominioTld(request.getDominioTld());
            dto.setHusoHorario(request.getHusoHorario());
            dto.setContinente(request.getContinente());
            dto.setEstado(Estado.ACTIVO);
            dto.setCreatedBy("system");
            dto.setCreatedAt(LocalDateTime.now());
            Pais entity = mapToEntity(dto);
            Pais savedEntity = repository.save(entity);

            // 3️⃣ Registrar auditoría, registrar el evento de creación.
            auditoriaService.registrarAuditoria(
                    Action.CREATE,
                    "País",
                    savedEntity.getId(),
                    "system",
                    "Creación de un nuevo país"
            );

            // 4️⃣ Crear relación inicial si hay moneda
            if (request.getMonedaId() != null) {
                paisMonedaService.registrarPrimerPaisMoneda(savedEntity.getId(), request.getMonedaId());
            }

            log.info("PaisService-create::Acción completada sin errores");
            return mapToResponseDTO(savedEntity);
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisService-create-MissingParameterException::Error en el Service, no se recibió el objeto País");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (DataIntegrityViolationException e) {
            // 🔹 2. Validación de integridad (en el catch)
            // ✅ Esto actúa como una “red de seguridad”:
            // si por algún motivo se intenta guardar un duplicado (por ejemplo, dos usuarios
            // crean al mismo tiempo el mismo país, o el findByNameAndNotDeleted no detectó algo),
            // entonces el motor de base de datos lanza una excepción de violación de restricción
            // única (unique constraint) y cae en este bloque.
            log.error("PaisService-create-DataIntegrityViolationException::Error en el Service al intentar persistir el País, Violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El país '" + request.getName() + "' ya existe en la base de datos.");
        } catch (NameAlreadyExistsException e) {
            log.error("PaisService-create-NameAlreadyExistsException::Error en el Service al intentar persistir el País, Nombre duplicado", e);
            throw new NameAlreadyExistsException("Ya existe un país activo con el nombre '" + request.getName() + "'.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisService-create-Exception::Error en el Service al intentar persistir el País", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de País. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    /**
     * Realiza una eliminación lógica del registro.
     * <p>No borra de forma física el país, solo marca el campo <b>deleted = true</b>.
     *
     * @param id Identificador del país a eliminar.
     *
     * @throws MissingParameterException si el ID es inválido.
     * @throws NotFoundException si no se encuentra el país solicitado.
     * @throws DataAccessException si ocurre un error en la operación.
     */
    @Override
    public void delete(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisService-delete::Iniciando la operación para eliminación lógica de País con ID: {}", id);
            if (id == null || id <= 0) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            // 1️⃣ Verificar existencia del país
            Optional<Pais> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el país con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            // 2️⃣ Marcar el país como eliminado (soft delete)
            Pais entity = optional.get();
            entity.setEstado(Estado.INACTIVO);
            entity.setDeleted(true);
            entity.setDeletedBy("system");
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
            log.info("PaisService-delete::El país con ID: {} se ha marcado como eliminado.", id);

            // 3️⃣ Registrar auditoría, registrar el evento de eliminación.
            auditoriaService.registrarAuditoria(
                    Action.DELETE,
                    "Pais",
                    entity.getId(),
                    "system",
                    "Eliminación lógica de un país"
            );

            // 4️⃣ Marcar relaciones Pais-Moneda como inactivas
            paisMonedaService.marcarRelacionesInactivasPorPais(entity.getId());

            log.info("PaisService-delete::Acción completada sin errores");
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisService-delete-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisService-delete-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisService-delete-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la eliminación. Por favor, contacta al administrador del sistema.");
        }
    }

    /**
     * Actualiza un registro existente de {@link Pais}.
     * <p>Se asegura de que el país exista, que no esté eliminado
     * e integra validaciones estándar de negocio.
     *
     * @param id Identificador del país a actualizar.
     * @param dto Objeto {@link PaisDTO} con los datos modificados.
     * @return {@link PaisResponseDTO} con los datos actualizados.
     *
     * @throws MissingParameterException si los parámetros básicos no son válidos.
     * @throws NotFoundException si no se encuentra el país solicitado.
     * @throws DataAccessException si ocurre un error al modificar en la base de datos.
     */
    @Override
    public PaisResponseDTO update(Long id, PaisUpdateDTO dto) throws Exception {
        try {
            //intentar
            log.info("PaisService-update::Iniciando la operación para actualizar pais con ID: {}", id);
            if (id == null || id <= 0) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }
            if (dto == null) {
                throw new MissingParameterException("El objeto País es requerido para la actualización.");
            }

            Optional<Pais> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el país con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            // --- Actualizar campos del país ---
            optional.get().setName(dto.getName());
            optional.get().setCodigoIso2(dto.getCodigoIso2());
            optional.get().setCodigoIso3(dto.getCodigoIso3());
            optional.get().setCapital(dto.getCapital());
            optional.get().setPoblacion(dto.getPoblacion());
            optional.get().setArea(dto.getArea());
            optional.get().setIdioma(dto.getIdioma());
            optional.get().setDominioTld(dto.getDominioTld());
            optional.get().setHusoHorario(dto.getHusoHorario());
            optional.get().setContinente(dto.getContinente());
            optional.get().setUpdatedBy("system");
            optional.get().setUpdatedAt(LocalDateTime.now());
            Pais updatedEntity = repository.save(optional.get());

            // Registrar auditoría, registrar el evento de actualización.
            auditoriaService.registrarAuditoria(
                    Action.UPDATE,
                    "Pais",
                    updatedEntity.getId(),
                    "system",
                    "Actualización de un país existente"
            );

            // --- Delegar manejo de monedas al service correspondiente ---
            if (dto.getMonedaId() != null) {
                paisMonedaService.actualizarMonedaPrincipal(
                        updatedEntity.getId(),
                        dto.getMonedaId(),
                        "system"
                );
            }

            log.info("PaisService-update::Acción completada sin errores");
            return mapToResponseDTO(updatedEntity);
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisService-update-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisService-update-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisService-update-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la actualización. Por favor, contacta al administrador del sistema.");
        }
    }

    /**
     * Obtiene un listado optimizado de países utilizando una proyección.
     * <p>Incluye datos básicos del país y su moneda principal cuando exista,
     * reduciendo carga de datos y mejorando el rendimiento en consultas masivas.
     *
     * @return Lista de {@link PaisListadoProjection} con datos resumidos.
     */
    @Transactional(readOnly = true)
    @Override
    public List<PaisResponseDTO> findAll() throws Exception {
        try {
            //intentar
            log.info("PaisService-findAll::Iniciando Servicio para obtener listado de países");

            List<PaisListadoProjection> rows = repository.findAllActive();

            log.info("PaisService-findAll::Acción completada sin errores.");
            List<PaisResponseDTO> dtoList = rows.stream().map(r -> {
                PaisResponseDTO dto = new PaisResponseDTO();
                dto.setId(r.getId());
                dto.setName(r.getName());
                dto.setCodigoIso2(r.getCodigoIso2());
                dto.setCodigoIso3(r.getCodigoIso3());
                dto.setCapital(r.getCapital());
                dto.setPoblacion(r.getPoblacion());
                dto.setArea(r.getArea());
                dto.setIdioma(r.getIdioma());
                dto.setMoneda(r.getMoneda());
                dto.setDominioTld(r.getDominioTld());
                dto.setHusoHorario(r.getHusoHorario());
                dto.setContinente(r.getContinente());
                dto.setEstado(r.getEstado());
                dto.setCreatedAt(r.getCreatedAt());
                dto.setUpdatedAt(r.getUpdatedAt());
                return dto;
            }).toList();

            return dtoList;
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataAccessException e) {
            //capturar, la raridad
            log.error("PaisService-findAll-DataAccessException::Error en el Service no se puede acceder a la Base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisService-findAll-Exception::Error en el Service al obtener el listado de Países", e);
            throw new Exception("Error al obtener el Listado de Países. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    /**
     * Obtiene el total de países activos filtrados por estado.
     *
     * @param estado Estado del país (ACTIVO, INACTIVO, etc.)
     * @return Número total de países activos con ese estado.
     */
    @Transactional(readOnly = true)
    @Override
    public long countByEstado(Estado estado) throws Exception {
        try {
            log.info("PaisService-countByEstado::Contando países por estado: {}", estado);
            return repository.countByEstadoAndNotDeleted(estado);
        } catch (DataAccessException e) {
            log.error("PaisService-countByEstado-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos.");
        } catch (Exception e) {
            log.error("PaisService-countByEstado-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Error inesperado al contar países por estado.");
        }
    }

    /**
     * Retorna la cantidad de países creados en la fecha actual.
     * <p>Se excluyen registros eliminados y se toma únicamente el valor
     * de la fecha sin considerar la hora.
     *
     * @return Número total de países nuevos creados hoy.
     */
    @Transactional(readOnly = true)
    @Override
    public long countByFechaCreacion(LocalDate fecha) throws Exception {
        try {
            log.info("PaisService-countByFechaCreacion::Contando países por fecha: {}", fecha);
            return repository.countCreatedToday(fecha);
        } catch (DataAccessException e) {
            log.error("PaisService-countByFechaCreacion-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos.");
        } catch (Exception e) {
            log.error("PaisService-countByFechaCreacion-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Error inesperado al contar países por fecha.");
        }
    }

    /**
     * Obtiene un país por su identificador único.
     *
     * @param id Identificador del país.
     * @return {@link PaisResponseDTO} con los datos solicitados.
     *
     * @throws MissingParameterException si el ID es nulo o inválido.
     * @throws NotFoundException si el país no existe o está marcado como eliminado.
     */
    @Transactional(readOnly = true)
    @Override
    public PaisResponseDTO get(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisService-get::Iniciando Servicio para obtener País buscado por ID");

            if (id == null || id <= 0) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Pais> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el país con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("PaisService-get::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisService-get-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("PaisService-get-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("PaisService-get-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    // Busca un país activo por ID y retorna todas sus propiedades.
    /**
     * Obtiene el listado completo de países activos (no eliminados).
     * <p>Este método utiliza un mapeo Entity → DTO estándar, por lo que
     * no incluye información adicional como la moneda principal.
     *
     * @return Lista de {@link PaisResponseDTO} con la información general.
     */
    @Override
    public PaisDTO getAll(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisService-getAll::Iniciando Servicio para obtener País buscado por ID");
            Optional<Pais> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                log.error("PaisService-getAll::Error en el Service, País con el ID: {}, No encontrado ", id);
                throw new NotFoundException("No se encontró el país con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("PaisService-getAll::Acción completada sin errores");
            return mapToDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisService-getAll-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        }  catch (Exception e) {
            //capturar, la raridad
            log.error("PaisService-getAll-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    // Busca un país activo por Nombre.
    @Transactional(readOnly = true)
    @Override
    public PaisResponseDTO getByName(String name) throws Exception {
        try {
            //intentar
            log.info("PaisService-getByName::Iniciando Servicio para obtener País buscado por Nombre");

            if (name == null || name.isEmpty()) {
                throw new MissingParameterException("El parámetro 'Nombre' es requerido.");
            }

            Optional<Pais> optional = repository.findByNameAndNotDeleted(name);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el país con el Nombre " + name + ". Por favor, verifica el Nombre y vuelve a intentarlo.");
            }

            log.info("PaisService-getByName::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisService-getByName-MissingParameterException::Error en el Service, no se recibió el parámetro Nombre");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisService-getByName-NotFoundException::Error en el Service, País con el Nombre: {}, No encontrado ", name);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("PaisService-getByName-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("PaisService-getByName-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

}
