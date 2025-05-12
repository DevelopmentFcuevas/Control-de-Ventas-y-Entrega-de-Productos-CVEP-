package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.domain.Ciudad;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.repos.CiudadRepository;
import py.com.housesolutions.ubicaciones.service.CiudadService;
import py.com.housesolutions.ubicaciones.service.DepartamentoService;
import py.com.housesolutions.ubicaciones.util.*;
import py.com.housesolutions.ubicaciones.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CiudadServiceImpl implements CiudadService {
    private final CiudadRepository repository;
    private final AuditoriaRepository auditoriaRepository;
    private final DepartamentoService departamentoService;

    // Constructor que inyecta los repositorios y el service que se va a utilizar en esta Clase.
    public CiudadServiceImpl(CiudadRepository repository,
                                   AuditoriaRepository auditoriaRepository,
                                   DepartamentoService departamentoService) {
        this.repository = repository;
        this.auditoriaRepository = auditoriaRepository;
        this.departamentoService = departamentoService;
    }

    public DepartamentoDTO getDepartamentoById(Long id) {
        DepartamentoDTO departamentoDTO;
        try {
            log.info("CiudadService-getDepartamentoById::Iniciando Servicio para obtener departamento por ID");
            departamentoDTO = departamentoService.getAll(id);
            log.info("CiudadService-getDepartamentoById::Acción completada sin errores.");
            return departamentoDTO;
        } catch (Exception e) {
            log.error("CiudadService-getDepartamentoById::Error en el Service al buscar Departamento", e);
            return departamentoDTO = null;
        }
    }

    public DepartamentoResponseDTO getDepartamentoResponseById(Long id) {
        log.info("CiudadService-getDepartamentoResponseById::Iniciando Servicio para obtener departamento por ID");
        DepartamentoResponseDTO departamentoResponseDTO;
        try {
            departamentoResponseDTO = departamentoService.get(id);
            log.info("CiudadService-getDepartamentoResponseById::Acción completada sin errores.");
            return departamentoResponseDTO;
        } catch (Exception e) {
            log.error("CiudadService-getDepartamentoResponseById::Error en el Service al buscar Departamento", e);
            return departamentoResponseDTO = null;
        }
    }

    // Mapea una entidad Ciudad a un DTO.
    @Override
    public CiudadDTO mapToDTO(Ciudad entity) {
        CiudadDTO dto = new CiudadDTO();

        // Mapeo de cada campo de la entidad a su equivalente en el DTO.
        dto.setId(entity.getId());

        DepartamentoDTO departamento = getDepartamentoById(entity.getDepartamento().getId());
        if (departamento != null) {
            dto.setDepartamento(departamento);
        }
        dto.setName(entity.getName());
        dto.setCodigoPostal(entity.getCodigoPostal());
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

    // Mapea una entidad Ciudad a un CiudadResponseDTO para la respuesta.
    @Override
    public CiudadResponseDTO mapToResponseDTO(Ciudad entity) {

        CiudadResponseDTO response = new CiudadResponseDTO();

        // Mapeo de cada campo de la entidad a su equivalente en el ResponseDTO.
        response.setId(entity.getId());

        DepartamentoResponseDTO departamento = getDepartamentoResponseById(entity.getDepartamento().getId());
        if (departamento != null) {
            response.setDepartamento(departamento);
        }
        response.setName(entity.getName());
        response.setCodigoPostal(entity.getCodigoPostal());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    // Mapea un DTO a una entidad Ciudad.
    @Override
    public Ciudad mapToEntity(CiudadDTO dto) {
        Ciudad entity = new Ciudad();

        // Mapeo de cada campo del DTO a su equivalente en la entidad.
        entity.setId(dto.getId());

        DepartamentoDTO departamento = getDepartamentoById(dto.getDepartamento().getId());
        if (departamento != null) {
            entity.setDepartamento(departamentoService.mapToEntity(departamento) );
        }
        entity.setName(dto.getName());
        entity.setCodigoPostal(dto.getCodigoPostal());
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

    // Busca todas las ciudades activas.
    @Transactional(readOnly = true)
    @Override
    public List<CiudadResponseDTO> findAll() throws Exception {
        try {
            log.info("CiudadService-findAll::Iniciando Servicio para obtener listado de ciudades");
            List<Ciudad> list = repository.findAllActive();
            List<CiudadResponseDTO> dtoList = new ArrayList<>();
            for (Ciudad entity : list) {
                CiudadResponseDTO dto = mapToResponseDTO(entity);
                dtoList.add(dto);
            }
            log.info("CiudadService-findAll::Acción completada sin errores.");
            return dtoList;
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataAccessException e) {
            //capturar, la raridad
            log.error("CiudadService-findAll-DataAccessException::Error en el Service no se puede acceder a la Base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            log.error("CiudadService-findAll::Error en el Service al obtener el listado de Ciudades", e);
            throw new Exception("Error al obtener el Listado de Ciudades. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Busca una ciudad activa por ID.
    @Transactional(readOnly = true)
    @Override
    public CiudadResponseDTO get(Long id) throws Exception {
        try {
            //intentar
            log.info("CiudadService-get::Iniciando Servicio para obtener Ciudad buscada por ID");
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Ciudad> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró la ciudad con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("CiudadService-get::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("CiudadService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("CiudadService-get-NotFoundException::Error en el Service, Ciudad con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("CiudadService-get-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("CiudadService-get-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }


    @Override
    public CiudadDTO getAll(Long id) throws Exception {
        try {
            //intentar
            log.info("CiudadService-getAll::Iniciando Servicio para obtener Ciudad buscada por ID");
            Optional<Ciudad> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                log.error("CiudadService-getAll::Error en el Service, Ciudad con el ID: {}, No encontrado ", id);
                throw new NotFoundException("No se encontró la ciudad con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("CiudadService-getAll::Acción completada sin errores");
            return mapToDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("CiudadService-getAll-NotFoundException::Error en el Service, Ciudad con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        }  catch (Exception e) {
            //capturar, la raridad
            log.error("CiudadService-getAll-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }



    // Crea una nueva Ciudad.
    @Override
    public CiudadResponseDTO create(CiudadCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("CiudadService-create::Persistir en la Base de datos el departamento");
            // Verificar si la ciudad ya existe como eliminado
            Optional<Ciudad> optionalDeleted = repository.findByNameAndDeleted(request.getName(), request.getDepartamento().getId());
            if (optionalDeleted.isPresent()) {
                //Reutilización de Registros Eliminados
                Ciudad deletedEntity = optionalDeleted.get();
                log.info("CiudadService-create::Reactivando la ciudad eliminada con ID: {}", deletedEntity.getId());
                deletedEntity.setName(request.getName());
                deletedEntity.setCodigoPostal(request.getCodigoPostal());

                // Reactivar el registro
                deletedEntity.setEstado(Estado.ACTIVO);
                deletedEntity.setDeleted(false);
                deletedEntity.setDeletedAt(null);
                deletedEntity.setDeletedBy(null);
                deletedEntity.setUpdatedAt(LocalDateTime.now());
                deletedEntity.setUpdatedBy("system");
                Ciudad reactivatedEntity = repository.save(deletedEntity);

                // Registrar auditoría, registrar el evento de Reactivación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.REACTIVATED);
                auditoria.setEntity("Ciudad");
                auditoria.setEntityId(reactivatedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Reactivación de una ciudad marcada como eliminada");
                auditoriaRepository.save(auditoria);

                log.info("CiudadService-create::Acción completada sin errores");
                return mapToResponseDTO(reactivatedEntity);
            } else {
                // Crear nuevo registro si no existe eliminado
                CiudadDTO dto = new CiudadDTO();

                DepartamentoDTO departamento = getDepartamentoById(request.getDepartamento().getId());
                dto.setDepartamento(departamento);

                dto.setName(request.getName());
                dto.setCodigoPostal(request.getCodigoPostal());
                dto.setEstado(Estado.ACTIVO);
                dto.setCreatedBy("system");
                dto.setCreatedAt(LocalDateTime.now());
                Ciudad entity = mapToEntity(dto);

                Ciudad savedEntity = repository.save(entity);

                // Registrar auditoría, registrar el evento de creación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.CREATE);
                auditoria.setEntity("Ciudad");
                auditoria.setEntityId(savedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Creación de una nueva ciudad");
                auditoriaRepository.save(auditoria);

                log.info("CiudadService-create::Acción completada sin errores");
                return mapToResponseDTO(savedEntity);
            }
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("CiudadService-create-DataIntegrityViolationException::Error en el Service al intentar persistir la Ciudad, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El nombre de departamento '" + request.getName() + "' ya existe en nuestra base de datos. El nombre de un nuevo registro para Ciudades no se puede repetir. Por favor, verifica el Nombre y vuelve a intentarlo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("CiudadService-create-Exception::Error en el Service al intentar persistir la Ciudad", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de Ciudad. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }


    // Actualiza una Ciudad existente.
    @Override
    public CiudadResponseDTO update(Long id, CiudadUpdateDTO request) throws Exception {
        try {
            log.info("CiudadService-update::Iniciando la operación para actualizar ciudad con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Ciudad> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró la ciudad con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            DepartamentoDTO departamento = getDepartamentoById(request.getDepartamento().getId());
            optional.get().setDepartamento( departamentoService.mapToEntity(departamento) );

            optional.get().setName(request.getName());
            optional.get().setCodigoPostal(request.getCodigoPostal());
            optional.get().setUpdatedBy("system");
            optional.get().setUpdatedAt(LocalDateTime.now());

            // Registrar auditoría, registrar el evento de actualización.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.UPDATE);
            auditoria.setEntity("Ciudad");
            auditoria.setEntityId(optional.get().getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Actualización de una ciudad existente");
            auditoriaRepository.save(auditoria);

            log.info("CiudadService-update::Acción completada sin errores");
            return mapToResponseDTO(repository.save(optional.get()));
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("CiudadService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("CiudadService-update-NotFoundException::Error en el Service, Ciudad con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("CiudadService-update-DataIntegrityViolationException::Error en el Service al intentar persistir la Ciudad, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El Nombre: '"+request.getName()+"' ya está en uso. Por favor, inténtelo de nuevo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("CiudadService-update-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la actualización. Por favor, contacta al administrador del sistema.");
        }
    }

    // Marca un registro de Ciudad como si fuera eliminado físicamente de la BD.
    @Override
    public void delete(Long id) throws Exception {
        try {
            log.info("CiudadService-delete::Iniciando la operación para eliminar ciudad con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Ciudad> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró la ciudad con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            Ciudad entity = optional.get();
            entity.setEstado(Estado.INACTIVO);
            entity.setDeleted(true);
            entity.setDeletedBy("system");
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
            log.info("CiudadService-delete::El departamento con ID: {} se ha marcado como eliminado.", id);

            // Registrar auditoría, registrar el evento de eliminación.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.DELETE);
            auditoria.setEntity("Ciudad");
            auditoria.setEntityId(entity.getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Eliminación de una ciudad");
            auditoriaRepository.save(auditoria);

            log.info("CiudadService-delete::Acción completada sin errores");
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("CiudadService-delete-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("CiudadService-delete-NotFoundException::Error en el Service, Ciudad con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (Exception e) {
            //capturar, la raridad
            log.error("CiudadService-delete-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la eliminación. Por favor, contacta al administrador del sistema.");
        }
    }
}
