package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.domain.Barrio;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.repos.BarrioRepository;
import py.com.housesolutions.ubicaciones.service.BarrioService;
import py.com.housesolutions.ubicaciones.service.CiudadService;
import py.com.housesolutions.ubicaciones.util.*;
import py.com.housesolutions.ubicaciones.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BarrioServiceImpl implements BarrioService {
    private final BarrioRepository repository;
    private final AuditoriaRepository auditoriaRepository;
    private final CiudadService ciudadService;

    // Constructor que inyecta los repositorios y el service que se va a utilizar en esta Clase.
    public BarrioServiceImpl(BarrioRepository repository,
                             AuditoriaRepository auditoriaRepository,
                             CiudadService ciudadService) {
        this.repository = repository;
        this.auditoriaRepository = auditoriaRepository;
        this.ciudadService = ciudadService;
    }

    public CiudadDTO getCiudadById(Long id) {
        CiudadDTO ciudadDTO;
        try {
            log.info("BarrioService-getCiudadById::Iniciando Servicio para obtener ciudad por ID");
            ciudadDTO = ciudadService.getAll(id);
            log.info("BarrioService-getCiudadById::Acción completada sin errores.");
            return ciudadDTO;
        } catch (Exception e) {
            log.error("BarrioService-getCiudadById::Error en el Service al buscar Ciudad", e);
            return ciudadDTO = null;
        }
    }

    public CiudadResponseDTO getCiudadResponseById(Long id) {
        log.info("BarrioService-getCiudadResponseById::Iniciando Servicio para obtener ciudad por ID");
        CiudadResponseDTO ciudadResponseDTO;
        try {
            ciudadResponseDTO = ciudadService.get(id);
            log.info("BarrioService-getCiudadResponseById::Acción completada sin errores.");
            return ciudadResponseDTO;
        } catch (Exception e) {
            log.error("BarrioService-getCiudadResponseById::Error en el Service al buscar Ciudad", e);
            return ciudadResponseDTO = null;
        }
    }

    // Mapea una entidad Barrio a un DTO.
    @Override
    public BarrioDTO mapToDTO(Barrio entity) {
        BarrioDTO dto = new BarrioDTO();

        // Mapeo de cada campo de la entidad a su equivalente en el DTO.
        dto.setId(entity.getId());

        CiudadDTO ciudad = getCiudadById(entity.getCiudad().getId());
        if (ciudad != null) {
            dto.setCiudad(ciudad);
        }
        dto.setName(entity.getName());
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

    // Mapea una entidad Barrio a un BarrioResponseDTO para la respuesta.
    @Override
    public BarrioResponseDTO mapToResponseDTO(Barrio entity) {

        BarrioResponseDTO response = new BarrioResponseDTO();

        // Mapeo de cada campo de la entidad a su equivalente en el ResponseDTO.
        response.setId(entity.getId());

        CiudadResponseDTO ciudad = getCiudadResponseById(entity.getCiudad().getId());
        if (ciudad != null) {
            response.setCiudad(ciudad);
        }
        response.setName(entity.getName());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    // Mapea un DTO a una entidad Barrio.
    @Override
    public Barrio mapToEntity(BarrioDTO dto) {
        Barrio entity = new Barrio();

        // Mapeo de cada campo del DTO a su equivalente en la entidad.
        entity.setId(dto.getId());

        CiudadDTO ciudad = getCiudadById(dto.getCiudad().getId());
        if (ciudad != null) {
            entity.setCiudad(ciudadService.mapToEntity(ciudad) );
        }
        entity.setName(dto.getName());
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

    // Busca todos los barrios activos.
    @Transactional(readOnly = true)
    @Override
    public List<BarrioResponseDTO> findAll() throws Exception {
        try {
            log.info("BarrioService-findAll::Iniciando Servicio para obtener listado de barrios");
            List<Barrio> list = repository.findAllActive();
            List<BarrioResponseDTO> dtoList = new ArrayList<>();
            for (Barrio entity : list) {
                BarrioResponseDTO dto = mapToResponseDTO(entity);
                dtoList.add(dto);
            }
            log.info("BarrioService-findAll::Acción completada sin errores.");
            return dtoList;
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataAccessException e) {
            //capturar, la raridad
            log.error("BarrioService-findAll-DataAccessException::Error en el Service no se puede acceder a la Base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            log.error("BarrioService-findAll::Error en el Service al obtener el listado de Barrios", e);
            throw new Exception("Error al obtener el Listado de Barrios. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Busca un barrio activo por ID.
    @Transactional(readOnly = true)
    @Override
    public BarrioResponseDTO get(Long id) throws Exception {
        try {
            //intentar
            log.info("BarrioService-get::Iniciando Servicio para obtener Barrio buscado por ID");
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Barrio> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el barrio con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("BarrioService-get::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("BarrioService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("BarrioService-get-NotFoundException::Error en el Service, Barrio con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("BarrioService-get-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("BarrioService-get-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }


    // Crea un nuevo Barrio.
    @Override
    public BarrioResponseDTO create(BarrioCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("BarrioService-create::Persistir en la Base de datos el barrio");
            // Verificar si el barrio ya existe como eliminado
            Optional<Barrio> optionalDeleted = repository.findByNameAndDeleted(request.getName(), request.getCiudad().getId());
            if (optionalDeleted.isPresent()) {
                //Reutilización de Registros Eliminados
                Barrio deletedEntity = optionalDeleted.get();
                log.info("BarrioService-create::Reactivando el barrio eliminado con ID: {}", deletedEntity.getId());
                deletedEntity.setName(request.getName());

                // Reactivar el registro
                deletedEntity.setEstado(Estado.ACTIVO);
                deletedEntity.setDeleted(false);
                deletedEntity.setDeletedAt(null);
                deletedEntity.setDeletedBy(null);
                deletedEntity.setUpdatedAt(LocalDateTime.now());
                deletedEntity.setUpdatedBy("system");
                Barrio reactivatedEntity = repository.save(deletedEntity);

                // Registrar auditoría, registrar el evento de Reactivación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.REACTIVATED);
                auditoria.setEntity("Barrio");
                auditoria.setEntityId(reactivatedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Reactivación de un barrio marcado como eliminado");
                auditoriaRepository.save(auditoria);

                log.info("BarrioService-create::Acción completada sin errores");
                return mapToResponseDTO(reactivatedEntity);
            } else {
                // Crear nuevo registro si no existe eliminado
                BarrioDTO dto = new BarrioDTO();

                CiudadDTO ciudad = getCiudadById(request.getCiudad().getId());
                dto.setCiudad(ciudad);

                dto.setName(request.getName());
                dto.setEstado(Estado.ACTIVO);
                dto.setCreatedBy("system");
                dto.setCreatedAt(LocalDateTime.now());
                Barrio entity = mapToEntity(dto);

                Barrio savedEntity = repository.save(entity);

                // Registrar auditoría, registrar el evento de creación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.CREATE);
                auditoria.setEntity("Barrio");
                auditoria.setEntityId(savedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Creación de un nuevo barrio");
                auditoriaRepository.save(auditoria);

                log.info("BarrioService-create::Acción completada sin errores");
                return mapToResponseDTO(savedEntity);
            }
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("BarrioService-create-DataIntegrityViolationException::Error en el Service al intentar persistir el Barrio, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El nombre de barrio '" + request.getName() + "' ya existe en nuestra base de datos. El nombre de un nuevo registro para Barrios no se puede repetir. Por favor, verifica el Nombre y vuelve a intentarlo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("BarrioService-create-Exception::Error en el Service al intentar persistir el Barrio", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de Barrio. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }


    // Actualiza un Barrio existente.
    @Override
    public BarrioResponseDTO update(Long id, BarrioUpdateDTO request) throws Exception {
        try {
            log.info("BarrioService-update::Iniciando la operación para actualizar barrio con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Barrio> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el barrio con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            CiudadDTO ciudad = getCiudadById(request.getCiudad().getId());
            optional.get().setCiudad( ciudadService.mapToEntity(ciudad) );

            optional.get().setName(request.getName());
            optional.get().setUpdatedBy("system");
            optional.get().setUpdatedAt(LocalDateTime.now());

            // Registrar auditoría, registrar el evento de actualización.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.UPDATE);
            auditoria.setEntity("Barrio");
            auditoria.setEntityId(optional.get().getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Actualización de un barrio existente");
            auditoriaRepository.save(auditoria);

            log.info("BarrioService-update::Acción completada sin errores");
            return mapToResponseDTO(repository.save(optional.get()));
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("BarrioService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("BarrioService-update-NotFoundException::Error en el Service, Barrio con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("BarrioService-update-DataIntegrityViolationException::Error en el Service al intentar persistir la Barrio, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El Nombre: '"+request.getName()+"' ya está en uso. Por favor, inténtelo de nuevo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("BarrioService-update-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la actualización. Por favor, contacta al administrador del sistema.");
        }
    }

    // Marca un registro de Barrio como si fuera eliminado físicamente de la BD.
    @Override
    public void delete(Long id) throws Exception {
        try {
            log.info("BarrioService-delete::Iniciando la operación para eliminar barrio con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Barrio> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el barrio con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            Barrio entity = optional.get();
            entity.setEstado(Estado.INACTIVO);
            entity.setDeleted(true);
            entity.setDeletedBy("system");
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
            log.info("BarrioService-delete::El barrio con ID: {} se ha marcado como eliminado.", id);

            // Registrar auditoría, registrar el evento de eliminación.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.DELETE);
            auditoria.setEntity("Barrio");
            auditoria.setEntityId(entity.getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Eliminación de un barrio");
            auditoriaRepository.save(auditoria);

            log.info("BarrioService-delete::Acción completada sin errores");
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("BarrioService-delete-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("BarrioService-delete-NotFoundException::Error en el Service, Barrio con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (Exception e) {
            //capturar, la raridad
            log.error("BarrioService-delete-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la eliminación. Por favor, contacta al administrador del sistema.");
        }
    }
}
