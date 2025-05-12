package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.domain.Departamento;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.repos.DepartamentoRepository;
import py.com.housesolutions.ubicaciones.service.DepartamentoService;
import py.com.housesolutions.ubicaciones.service.PaisService;
import py.com.housesolutions.ubicaciones.util.*;
import py.com.housesolutions.ubicaciones.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepartamentoServiceImpl implements DepartamentoService {
    private final DepartamentoRepository repository;
    private final AuditoriaRepository auditoriaRepository;
    private final PaisService paisService;
    
    // Constructor que inyecta los repositorios y el service que se va a utilizar en esta Clase.
    public DepartamentoServiceImpl(DepartamentoRepository repository,
                                   AuditoriaRepository auditoriaRepository,
                                   PaisService paisService) {
        this.repository = repository;
        this.auditoriaRepository = auditoriaRepository;
        this.paisService = paisService;
    }

    public PaisDTO getPaisById(Long id) {
        PaisDTO paisDTO;
        try {
            log.info("DepartamentoService-getPaisById::Iniciando Servicio para obtener país por ID");
            paisDTO = paisService.getAll(id);
            log.info("DepartamentoService-getPaisById::Acción completada sin errores.");
            return paisDTO;
        } catch (Exception e) {
            log.error("DepartamentoService-getPaisById::Error en el Service al buscar Pais", e);
            return paisDTO = null;
        }
    }

    public PaisResponseDTO getPaisResponseById(Long id) {
        log.info("DepartamentoService-getPaisResponseById::Iniciando Servicio para obtener país por ID");
        PaisResponseDTO paisResponseDTO;
        try {
            paisResponseDTO = paisService.get(id);
            log.info("DepartamentoService-getPaisResponseById::Acción completada sin errores.");
            return paisResponseDTO;
        } catch (Exception e) {
            log.error("DepartamentoService-getPaisResponseById::Error en el Service al buscar Pais", e);
            return paisResponseDTO = null;
        }
    }

    public PaisResponseDTO getPaisResponseByName(String name) {
        log.info("DepartamentoService-getPaisResponseByName::Iniciando Servicio para obtener país por Nombre");
        PaisResponseDTO paisResponseDTO;
        try {
            paisResponseDTO = paisService.getByName(name);
            log.info("DepartamentoService-getPaisResponseByName::Acción completada sin errores.");
            return paisResponseDTO;
        } catch (Exception e) {
            log.error("DepartamentoService-getPaisResponseByName::Error en el Service al buscar Pais", e);
            return paisResponseDTO = null;
        }
    }

    // Mapea una entidad Departamento a un DTO.
    @Override
    public DepartamentoDTO mapToDTO(Departamento entity) {
        DepartamentoDTO dto = new DepartamentoDTO();
        // Mapeo de cada campo de la entidad a su equivalente en el DTO.
        dto.setId(entity.getId());

        PaisDTO paisDTO = getPaisById(entity.getPais().getId());
        if (paisDTO != null) {
            dto.setPais(paisDTO);
        }

        dto.setName(entity.getName());
        dto.setCodigoIso(entity.getCodigoIso());
        dto.setCapital(entity.getCapital());
        dto.setPoblacion(entity.getPoblacion());
        dto.setSuperficie(entity.getSuperficie());
        dto.setRegion(entity.getRegion());
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

    // Mapea una entidad Departamento a un DepartamentoResponseDTO para la respuesta.
    @Override
    public DepartamentoResponseDTO mapToResponseDTO(Departamento entity) {
        DepartamentoResponseDTO response = new DepartamentoResponseDTO();
        // Mapeo de cada campo de la entidad a su equivalente en el ResponseDTO.
        response.setId(entity.getId());

        PaisResponseDTO paisResponseDTO = getPaisResponseById(entity.getPais().getId());
        if (paisResponseDTO != null) {
            response.setPais(paisResponseDTO);
        }

        response.setName(entity.getName());
        response.setCodigoIso(entity.getCodigoIso());
        response.setCapital(entity.getCapital());
        response.setPoblacion(entity.getPoblacion());
        response.setSuperficie(entity.getSuperficie());
        response.setRegion(entity.getRegion());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    // Mapea un DTO a una entidad Departamento.
    @Override
    public Departamento mapToEntity(DepartamentoDTO dto) {
        Departamento entity = new Departamento();
        // Mapeo de cada campo del DTO a su equivalente en la entidad.
        entity.setId(dto.getId());

        PaisDTO paisDTO = getPaisById(dto.getPais().getId());
        if (paisDTO != null) {
            entity.setPais(paisService.mapToEntity(paisDTO) );
        }

        entity.setName(dto.getName());
        entity.setCodigoIso(dto.getCodigoIso());
        entity.setCapital(dto.getCapital());
        entity.setPoblacion(dto.getPoblacion());
        entity.setSuperficie(dto.getSuperficie());
        entity.setRegion(dto.getRegion());
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

    // Busca todos los departamentos activos.
    @Transactional(readOnly = true)
    @Override
    public List<DepartamentoResponseDTO> findAll() throws Exception {
        try {
            log.info("DepartamentoService-findAll::Iniciando Servicio para obtener listado de departamentos");
            List<Departamento> list = repository.findAllActive();
            List<DepartamentoResponseDTO> dtoList = new ArrayList<>();
            for (Departamento entity : list) {
                DepartamentoResponseDTO dto = mapToResponseDTO(entity);
                dtoList.add(dto);
            }
            log.info("DepartamentoService-findAll::Acción completada sin errores.");
            return dtoList;
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataAccessException e) {
            //capturar, la raridad
            log.error("DepartamentoService-findAll-DataAccessException::Error en el Service no se puede acceder a la Base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            log.error("DepartamentoService-findAll::Error en el Service al obtener el listado de Países", e);
            throw new Exception("Error al obtener el Listado de Departamentos. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Busca un departamento activo por ID.
    @Transactional(readOnly = true)
    @Override
    public DepartamentoResponseDTO get(Long id) throws Exception {
        try {
            //intentar
            log.info("DepartamentoService-get::Iniciando Servicio para obtener Departamento buscado por ID");
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Departamento> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el departamento con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("DepartamentoService-get::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("DepartamentoService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("DepartamentoService-get-NotFoundException::Error en el Service, Departamento con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("DepartamentoService-get-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("DepartamentoService-get-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    @Override
    public DepartamentoDTO getAll(Long id) throws Exception {
        try {
            //intentar
            log.info("DepartamentoService-getAll::Iniciando Servicio para obtener Departamento buscado por ID");
            Optional<Departamento> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                log.error("DepartamentoService-getAll::Error en el Service, Departamento con el ID: {}, No encontrado ", id);
                throw new NotFoundException("No se encontró el departamento con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("DepartamentoService-getAll::Acción completada sin errores");
            return mapToDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("DepartamentoService-getAll-NotFoundException::Error en el Service, Departamento con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        }  catch (Exception e) {
            //capturar, la raridad
            log.error("DepartamentoService-getAll-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }



    // Crea un nuevo Departamento.
    @Override
    public DepartamentoResponseDTO create(DepartamentoCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("DepartamentoService-create::Persistir en la Base de datos el departamento");
            // Verificar si el departamento ya existe como eliminado
            Optional<Departamento> optionalDeleted = repository.findByNameAndDeleted(request.getName(), request.getPais().getId());
            if (optionalDeleted.isPresent()) {
                //Reutilización de Registros Eliminados
                Departamento deletedEntity = optionalDeleted.get();
                log.info("DepartamentoService-create::Reactivando el departamento eliminado con ID: {}", deletedEntity.getId());
                deletedEntity.setName(request.getName());
                deletedEntity.setCodigoIso(request.getCodigoIso());
                deletedEntity.setCapital(request.getCapital());
                deletedEntity.setPoblacion(request.getPoblacion());
                deletedEntity.setSuperficie(request.getSuperficie());
                deletedEntity.setRegion(request.getRegion());

                // Reactivar el registro
                deletedEntity.setEstado(Estado.ACTIVO);
                deletedEntity.setDeleted(false);
                deletedEntity.setDeletedAt(null);
                deletedEntity.setDeletedBy(null);
                deletedEntity.setUpdatedAt(LocalDateTime.now());
                deletedEntity.setUpdatedBy("system");
                Departamento reactivatedEntity = repository.save(deletedEntity);

                // Registrar auditoría, registrar el evento de Reactivación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.REACTIVATED);
                auditoria.setEntity("Pais");
                auditoria.setEntityId(reactivatedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Reactivación de un país marcado como eliminado");
                auditoriaRepository.save(auditoria);

                log.info("DepartamentoService-create::Acción completada sin errores");
                return mapToResponseDTO(reactivatedEntity);
            } else {
                // Crear nuevo registro si no existe eliminado
                DepartamentoDTO dto = new DepartamentoDTO();

                PaisDTO paisDTO = getPaisById(request.getPais().getId());
                dto.setPais(paisDTO);
                log.info("DepartamentoService-create::Creando un nuevo departamento con ID país: {}", paisDTO.getId());

                dto.setName(request.getName());
                dto.setCodigoIso(request.getCodigoIso());
                dto.setCapital(request.getCapital());
                dto.setPoblacion(request.getPoblacion());
                dto.setSuperficie(request.getSuperficie());
                dto.setRegion(request.getRegion());
                dto.setEstado(Estado.ACTIVO);
                dto.setCreatedBy("system");
                dto.setCreatedAt(LocalDateTime.now());
                Departamento entity = mapToEntity(dto);

                Departamento savedEntity = repository.save(entity);

                // Registrar auditoría, registrar el evento de creación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.CREATE);
                auditoria.setEntity("Departamento");
                auditoria.setEntityId(savedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Creación de un nuevo departamento");
                auditoriaRepository.save(auditoria);

                log.info("DepartamentoService-create::Acción completada sin errores");
                return mapToResponseDTO(savedEntity);
            }
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("DepartamentoService-create-DataIntegrityViolationException::Error en el Service al intentar persistir el Departamento, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El nombre de departamento '" + request.getName() + "' ya existe en nuestra base de datos. El nombre de un nuevo registro para Departamentos no se puede repetir. Por favor, verifica el Nombre y vuelve a intentarlo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("DepartamentoService-create-Exception::Error en el Service al intentar persistir el Departamento", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de Departamento. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }


    // Actualiza un Departamento existente.
    @Override
    public DepartamentoResponseDTO update(Long id, DepartamentoUpdateDTO dto) throws Exception {
        try {
            log.info("DepartamentoService-update::Iniciando la operación para actualizar departamento con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Departamento> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el departamento con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            PaisDTO paisDTO = getPaisById(dto.getPais().getId());
            optional.get().setPais( paisService.mapToEntity(paisDTO) );
            optional.get().setName(dto.getName());
            optional.get().setCodigoIso(dto.getCodigoIso());
            optional.get().setCapital(dto.getCapital());
            optional.get().setPoblacion(dto.getPoblacion());
            optional.get().setSuperficie(dto.getSuperficie());
            optional.get().setRegion(dto.getRegion());
            optional.get().setUpdatedBy("system");
            optional.get().setUpdatedAt(LocalDateTime.now());

            // Registrar auditoría, registrar el evento de actualización.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.UPDATE);
            auditoria.setEntity("Departamento");
            auditoria.setEntityId(optional.get().getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Actualización de un departamento existente");
            auditoriaRepository.save(auditoria);

            log.info("DepartamentoService-update::Acción completada sin errores");
            return mapToResponseDTO(repository.save(optional.get()));
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("DepartamentoService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("DepartamentoService-update-NotFoundException::Error en el Service, Departamento con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("DepartamentoService-update-DataIntegrityViolationException::Error en el Service al intentar persistir el Departamento, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El Nombre: '"+dto.getName()+"' ya está en uso. Por favor, inténtelo de nuevo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("DepartamentoService-update-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la actualización. Por favor, contacta al administrador del sistema.");
        }
    }

    // Marca un registro de Departamento como si fuera eliminado físicamente de la BD.
    @Override
    public void delete(Long id) throws Exception {
        try {
            log.info("DepartamentoService-delete::Iniciando la operación para eliminar departamento con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Departamento> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el departamento con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            Departamento entity = optional.get();
            entity.setEstado(Estado.INACTIVO);
            entity.setDeleted(true);
            entity.setDeletedBy("system");
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
            log.info("DepartamentoService-delete::El departamento con ID: {} se ha marcado como eliminado.", id);

            // Registrar auditoría, registrar el evento de eliminación.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.DELETE);
            auditoria.setEntity("Departamento");
            auditoria.setEntityId(entity.getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Eliminación de un departamento");
            auditoriaRepository.save(auditoria);

            log.info("DepartamentoService-delete::Acción completada sin errores");
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("DepartamentoService-delete-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("DepartamentoService-delete-NotFoundException::Error en el Service, Departamento con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (Exception e) {
            //capturar, la raridad
            log.error("DepartamentoService-delete-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la eliminación. Por favor, contacta al administrador del sistema.");
        }
    }

}
