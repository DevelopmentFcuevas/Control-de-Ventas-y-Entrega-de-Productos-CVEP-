package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.domain.Moneda;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.repos.MonedaRepository;
import py.com.housesolutions.ubicaciones.service.MonedaService;
import py.com.housesolutions.ubicaciones.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MonedaServiceImpl implements MonedaService {
    private final MonedaRepository repository;
    private final AuditoriaRepository auditoriaRepository;

    public MonedaServiceImpl(MonedaRepository repository, AuditoriaRepository auditoriaRepository) {
        this.repository = repository;
        this.auditoriaRepository = auditoriaRepository;
    }

    // Mapea una entidad Moneda a un DTO.
    @Override
    public MonedaDTO mapToDTO(Moneda entity) {
        MonedaDTO dto = new MonedaDTO();
        // Mapeo de cada campo de la entidad a su equivalente en el DTO.
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setSimbolo(entity.getSimbolo());
        dto.setIsoNum(entity.getIsoNum());
        dto.setMinorUnit(entity.getMinorUnit());
        dto.setNotas(entity.getNotas());
        dto.setEstado(entity.getEstado());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeleted(entity.isDeleted());
        dto.setDeletedBy(entity.getDeletedBy());
        dto.setDeletedAt(entity.getDeletedAt());
        return dto;
    }

    // Mapea una entidad Moneda a un MonedaResponseDTO para la respuesta.
    @Override
    public MonedaResponseDTO mapToResponseDTO(Moneda entity) {
        MonedaResponseDTO response = new MonedaResponseDTO();
        // Mapeo de cada campo de la entidad a su equivalente en el ResponseDTO.
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setCode(entity.getCode());
        response.setSimbolo(entity.getSimbolo());
        response.setIsoNum(entity.getIsoNum());
        response.setMinorUnit(entity.getMinorUnit());
        response.setNotas(entity.getNotas());
        response.setEstado(entity.getEstado());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }

    // Mapea un DTO a una entidad Moneda.
    @Override
    public Moneda mapToEntity(MonedaDTO dto) {
        Moneda entity = new Moneda();
        // Mapeo de cada campo del DTO a su equivalente en la entidad.
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setSimbolo(dto.getSimbolo());
        entity.setIsoNum(dto.getIsoNum());
        entity.setMinorUnit(dto.getMinorUnit());
        entity.setNotas(dto.getNotas());
        entity.setEstado(dto.getEstado());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setDeleted(dto.isDeleted());
        entity.setDeletedBy(dto.getDeletedBy());
        entity.setDeletedAt(dto.getDeletedAt());

        return entity;
    }

    // Busca todas las monedas activas.
    @Transactional(readOnly = true)
    @Override
    public List<MonedaResponseDTO> findAll() throws Exception {
        try {
            //intentar
            log.info("MonedaService-findAll::Iniciando Servicio para obtener listado de monedas");
            List<Moneda> list = repository.findAllActive();
            List<MonedaResponseDTO> dtoList = new ArrayList<>();
            for (Moneda entity : list) {
                MonedaResponseDTO dto = mapToResponseDTO(entity);
                dtoList.add(dto);
            }
            log.info("MonedaService-findAll::Acción completada sin errores.");
            return dtoList;
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataAccessException e) {
            //capturar, la raridad
            log.error("MonedaService-findAll-DataAccessException::Error en el Service no se puede acceder a la Base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("MonedaService-findAll-Exception::Error en el Service al obtener el listado de Monedas", e);
            throw new Exception("Error al obtener el Listado de Monedas. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Busca una moneda activa por ID.
    @Transactional(readOnly = true)
    @Override
    public MonedaResponseDTO get(Long id) throws Exception {
        try {
            //intentar
            log.info("MonedaService-get::Iniciando Servicio para obtener Moneda buscada por ID");

            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Moneda> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró la moneda con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("MonedaService-get::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("MonedaService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("MonedaService-get-NotFoundException::Error en el Service, Moneda con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("MonedaService-get-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("MonedaService-get-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    // Busca una moneda activa por Nombre.
    @Transactional(readOnly = true)
    @Override
    public MonedaResponseDTO getByName(String name) throws Exception {
        try {
            //intentar
            log.info("MonedaService-getByName::Iniciando Servicio para obtener Moneda buscada por Nombre");

            if (name == null || name.isEmpty()) {
                throw new MissingParameterException("El parámetro 'Nombre' es requerido.");
            }

            Optional<Moneda> optional = repository.findByNameAndNotDeleted(name);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró la moneda con el Nombre " + name + ". Por favor, verifica el Nombre y vuelve a intentarlo.");
            }

            log.info("MonedaService-getByName::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("MonedaService-getByName-MissingParameterException::Error en el Service, no se recibió el parámetro Nombre");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("MonedaService-getByName-NotFoundException::Error en el Service, Moneda con el Nombre: {}, No encontrado ", name);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("MonedaService-getByName-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("MonedaService-getByName-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    // Busca una moneda activa por ID y retorna todas sus propiedades.
    @Override
    public MonedaDTO getAll(Long id) throws Exception {
        try {
            //intentar
            log.info("MonedaService-getAll::Iniciando Servicio para obtener Moneda buscada por ID");
            Optional<Moneda> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                log.error("MonedaService-getAll::Error en el Service, Moneda con el ID: {}, No encontrado ", id);
                throw new NotFoundException("No se encontró la moneda con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("MonedaService-getAll::Acción completada sin errores");
            return mapToDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("MonedaService-getAll-NotFoundException::Error en el Service, Moneda con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        }  catch (Exception e) {
            //capturar, la raridad
            log.error("MonedaService-getAll-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    // Crea una nueva Moneda.
    @Override
    public MonedaResponseDTO create(MonedaCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("MonedaService-create::Persistir en la Base de datos la moneda");

            // Verificar si la moneda ya existe como eliminada
            Optional<Moneda> optionalDeleted = repository.findByNameAndDeleted(request.getName());
            if (optionalDeleted.isPresent()) {
                //Reutilización de Registros Eliminados
                Moneda deletedEntity = optionalDeleted.get();
                log.info("MonedaService-create::Reactivando la moneda eliminada con ID: {}", deletedEntity.getId());

                deletedEntity.setName(request.getName());
                deletedEntity.setCode(request.getCode());
                deletedEntity.setSimbolo(request.getSimbolo());
                deletedEntity.setIsoNum(request.getIsoNum());
                deletedEntity.setMinorUnit(request.getMinorUnit());
                deletedEntity.setNotas(request.getNotas());
                // Reactivar el registro
                deletedEntity.setEstado(Estado.ACTIVO);
                deletedEntity.setDeleted(false);
                deletedEntity.setDeletedAt(null);
                deletedEntity.setDeletedBy(null);
                deletedEntity.setUpdatedAt(LocalDateTime.now());
                deletedEntity.setUpdatedBy("system");
                Moneda reactivatedEntity = repository.save(deletedEntity);

                // Registrar auditoría, registrar el evento de Reactivación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.REACTIVATED);
                auditoria.setEntity("Moneda");
                auditoria.setEntityId(reactivatedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Reactivación de una moneda marcada como eliminada");
                auditoriaRepository.save(auditoria);

                log.info("MonedaService-create::Acción completada sin errores");
                return mapToResponseDTO(reactivatedEntity);
            } else {
                // Crear nuevo registro si no existe eliminado
                MonedaDTO dto = new MonedaDTO();
                dto.setName(request.getName());
                dto.setCode(request.getCode());
                dto.setSimbolo(request.getSimbolo());
                dto.setIsoNum(request.getIsoNum());
                dto.setMinorUnit(request.getMinorUnit());
                dto.setNotas(request.getNotas());
                dto.setEstado(Estado.ACTIVO);
                dto.setCreatedBy("system");
                dto.setCreatedAt(LocalDateTime.now());
                Moneda entity = mapToEntity(dto);
                Moneda savedEntity = repository.save(entity);

                // Registrar auditoría, registrar el evento de creación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.CREATE);
                auditoria.setEntity("Moneda");
                auditoria.setEntityId(savedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Creación de una nueva moneda");
                auditoriaRepository.save(auditoria);

                log.info("MonedaService-create::Acción completada sin errores");
                return mapToResponseDTO(savedEntity);
            }
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("MonedaService-create-DataIntegrityViolationException::Error en el Service al intentar persistir la Moneda, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El nombre de moneda '" + request.getName() + "' ya existe en nuestra base de datos. El nombre de un nuevo registro para Monedas no se puede repetir. Por favor, verifica el Nombre y vuelve a intentarlo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("MonedaService-create-Exception::Error en el Service al intentar persistir la Moneda", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de Moneda. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Actualiza una Moneda existente.
    @Override
    public MonedaResponseDTO update(Long id, MonedaUpdateDTO dto) throws Exception {
        try {
            //intentar
            log.info("MonedaService-update::Iniciando la operación para actualizar moneda con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Moneda> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró la moneda con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            optional.get().setName(dto.getName());
            optional.get().setCode(dto.getCode());
            optional.get().setSimbolo(dto.getSimbolo());
            optional.get().setIsoNum(dto.getIsoNum());
            optional.get().setMinorUnit(dto.getMinorUnit());
            optional.get().setNotas(dto.getNotas());
            optional.get().setUpdatedBy("system");
            optional.get().setUpdatedAt(LocalDateTime.now());

            // Registrar auditoría, registrar el evento de actualización.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.UPDATE);
            auditoria.setEntity("Moneda");
            auditoria.setEntityId(optional.get().getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Actualización de una moneda existente");
            auditoriaRepository.save(auditoria);

            log.info("MonedaService-update::Acción completada sin errores");
            return mapToResponseDTO(repository.save(optional.get()));
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("MonedaService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("MonedaService-update-NotFoundException::Error en el Service, Moneda con el ID: {}, No encontrada ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("MonedaService-update-DataIntegrityViolationException::Error en el Service al intentar persistir la Moneda, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El Nombre: '"+dto.getName()+"' ya está en uso. Por favor, inténtelo de nuevo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("MonedaService-update-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la actualización. Por favor, contacta al administrador del sistema.");
        }
    }

    // Marca un registro de Moneda como si fuera eliminado físicamente de la BD.
    @Override
    public void delete(Long id) throws Exception {
        try {
            //intentar
            log.info("MonedaService-delete::Iniciando la operación para eliminar moneda con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Moneda> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                //log.error("MonedaService-delete2::Error en el Service, Moneda con el ID: {}, No encontrado ", id);
                throw new NotFoundException("No se encontró la moneda con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            Moneda entity = optional.get();
            entity.setEstado(Estado.INACTIVO);
            entity.setDeleted(true);
            entity.setDeletedBy("system");
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
            log.info("MonedaService-delete::La moneda con ID: {} se ha marcado como eliminado.", id);

            // Registrar auditoría, registrar el evento de eliminación.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.DELETE);
            auditoria.setEntity("Moneda");
            auditoria.setEntityId(entity.getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Eliminación de una moneda");
            auditoriaRepository.save(auditoria);

            log.info("MonedaService-delete::Acción completada sin errores");
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("MonedaService-delete-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("MonedaService-delete-NotFoundException::Error en el Service, Moneda con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (Exception e) {
            //capturar, la raridad
            log.error("MonedaService-delete-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la eliminación. Por favor, contacta al administrador del sistema.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public long countByEstado(Estado estado) throws Exception {
        try {
            log.info("MonedaService-countByEstado::Contando monedas por estado: {}", estado);
            return repository.countByEstadoAndNotDeleted(estado);
        } catch (DataAccessException e) {
            log.error("MonedaService-countByEstado-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos.");
        } catch (Exception e) {
            log.error("MonedaService-countByEstado-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Error inesperado al contar monedas por estado.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public long countByFechaCreacion(LocalDate fecha) throws Exception {
        try {
            log.info("MonedaService-countByFechaCreacion::Contando monedas por fecha: {}", fecha);
            return repository.countCreatedToday(fecha);
        } catch (DataAccessException e) {
            log.error("MonedaService-countByFechaCreacion-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos.");
        } catch (Exception e) {
            log.error("MonedaService-countByFechaCreacion-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Error inesperado al contar monedas por fecha.");
        }
    }


}
