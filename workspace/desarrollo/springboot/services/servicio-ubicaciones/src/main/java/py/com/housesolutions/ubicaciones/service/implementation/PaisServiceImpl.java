package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.repos.PaisRepository;
import py.com.housesolutions.ubicaciones.service.PaisService;
import py.com.housesolutions.ubicaciones.util.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PaisServiceImpl implements PaisService {
    private final PaisRepository repository;
    private final AuditoriaRepository auditoriaRepository;
    // Constructor que inyecta los repositorios.
    public PaisServiceImpl(final PaisRepository repository, AuditoriaRepository auditoriaRepository) {
        this.repository = repository;
        this.auditoriaRepository = auditoriaRepository;
    }


    // Mapea una entidad Pais a un DTO.
    @Override
    public PaisDTO mapToDTO(Pais entity) {
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
        dto.setMoneda(entity.getMoneda());
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
        return dto;
    }

    // Mapea una entidad Pais a un PaisResponseDTO para la respuesta.
    @Override
    public PaisResponseDTO mapToResponseDTO(Pais entity) {
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
        response.setMoneda(entity.getMoneda());
        response.setDominioTld(entity.getDominioTld());
        response.setHusoHorario(entity.getHusoHorario());
        response.setContinente(entity.getContinente());
        //response.setImagePath(entity.getImagePath());
        response.setEstado(entity.getEstado());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    // Mapea un DTO a una entidad Pais.
    @Override
    public Pais mapToEntity(PaisDTO dto) {
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
        entity.setMoneda(dto.getMoneda());
        entity.setDominioTld(dto.getDominioTld());
        entity.setHusoHorario(dto.getHusoHorario());
        entity.setContinente(dto.getContinente());
        //entity.setImagePath(dto.getImagePath());
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


    // Busca todos los países activos.
    @Transactional(readOnly = true)
    @Override
    public List<PaisResponseDTO> findAll() throws Exception {
        try {
            //intentar
            log.info("PaisService-findAll::Iniciando Servicio para obtener listado de países");
            List<Pais> list = repository.findAllActive();
            List<PaisResponseDTO> dtoList = new ArrayList<>();
            for (Pais entity : list) {
                PaisResponseDTO dto = mapToResponseDTO(entity);
                dtoList.add(dto);
            }
            log.info("PaisService-findAll::Acción completada sin errores.");
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

    // Busca un país activo por ID.
    @Transactional(readOnly = true)
    @Override
    public PaisResponseDTO get(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisService-get::Iniciando Servicio para obtener País buscado por ID");

            if (id == null) {
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

    // Busca un país activo por ID y retorna todas sus propiedades.
    @Override
    public PaisDTO getAll(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisService-getAll::Iniciando Servicio para obtener Pais buscado por ID");
            Optional<Pais> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                log.error("PaisService-getAll::Error en el Service, Pais con el ID: {}, No encontrado ", id);
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

    // Crea un nuevo País.
    @Override
    //public PaisResponseDTO create(PaisCreateDTO request) throws Exception {
    public PaisResponseDTO create(PaisCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("PaisService-create::Persistir en la Base de datos el país");

            // Verificar si el país ya existe como eliminado
            Optional<Pais> optionalDeleted = repository.findByNameAndDeleted(request.getName());
            if (optionalDeleted.isPresent()) {
                //Reutilización de Registros Eliminados
                Pais deletedEntity = optionalDeleted.get();
                log.info("PaisService-create::Reactivando el país eliminado con ID: {}", deletedEntity.getId());

                deletedEntity.setName(request.getName());
                deletedEntity.setCodigoIso2(request.getCodigoIso2());
                deletedEntity.setCodigoIso3(request.getCodigoIso3());
                deletedEntity.setCapital(request.getCapital());
                deletedEntity.setPoblacion(request.getPoblacion());
                deletedEntity.setArea(request.getArea());
                deletedEntity.setIdioma(request.getIdioma());
                deletedEntity.setMoneda(request.getMoneda());
                deletedEntity.setDominioTld(request.getDominioTld());
                deletedEntity.setHusoHorario(request.getHusoHorario());
                deletedEntity.setContinente(request.getContinente());
                // Reactivar el registro
                deletedEntity.setEstado(Estado.ACTIVO);
                deletedEntity.setDeleted(false);
                deletedEntity.setDeletedAt(null);
                deletedEntity.setDeletedBy(null);
                deletedEntity.setUpdatedAt(LocalDateTime.now());
                deletedEntity.setUpdatedBy("system");
                Pais reactivatedEntity = repository.save(deletedEntity);

                // Registrar auditoría, registrar el evento de Reactivación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.REACTIVATED);
                auditoria.setEntity("Pais");
                auditoria.setEntityId(reactivatedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Reactivación de un país marcado como eliminado");
                auditoriaRepository.save(auditoria);

                log.info("PaisService-create::Acción completada sin errores");
                return mapToResponseDTO(reactivatedEntity);
            } else {
                // Crear nuevo registro si no existe eliminado
                PaisDTO dto = new PaisDTO();
                dto.setName(request.getName());
                dto.setCodigoIso2(request.getCodigoIso2());
                dto.setCodigoIso3(request.getCodigoIso3());
                dto.setCapital(request.getCapital());
                dto.setPoblacion(request.getPoblacion());
                dto.setArea(request.getArea());
                dto.setIdioma(request.getIdioma());
                dto.setMoneda(request.getMoneda());
                dto.setDominioTld(request.getDominioTld());
                dto.setHusoHorario(request.getHusoHorario());
                dto.setContinente(request.getContinente());
                //dto.setImagePath(
                //        request.getImagePath() != null ? request.getImagePath() : "/images/default-flag.png"
                //);
                //String imagePath;
                //if (imageFile != null && !imageFile.isEmpty()) {
                //    String uploadDir = "uploads/paises"; // Carpeta relativa a la raíz del proyecto
                //    String extension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
                //    String filename = UUID.randomUUID().toString() + "." + extension;

                //    File uploadFolder = new File(uploadDir);
                //    if (!uploadFolder.exists()) {
                //        uploadFolder.mkdirs();
                //    }

                //    Path path = Paths.get(uploadDir, filename);
                //    Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                //    imagePath = "/" + uploadDir + "/" + filename;
                //} else {
                //    imagePath = "/uploads/default-flag.png";
                //}
                //dto.setImagePath(imagePath);

                dto.setEstado(Estado.ACTIVO);
                dto.setCreatedBy("system");
                dto.setCreatedAt(LocalDateTime.now());
                Pais entity = mapToEntity(dto);
                Pais savedEntity = repository.save(entity);

                // Registrar auditoría, registrar el evento de creación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.CREATE);
                auditoria.setEntity("Pais");
                auditoria.setEntityId(savedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Creación de un nuevo país");
                auditoriaRepository.save(auditoria);

                log.info("PaisService-create::Acción completada sin errores");
                return mapToResponseDTO(savedEntity);
            }
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("PaisService-create-DataIntegrityViolationException::Error en el Service al intentar persistir el País, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El nombre de país '" + request.getName() + "' ya existe en nuestra base de datos. El nombre de un nuevo registro para Países no se puede repetir. Por favor, verifica el Nombre y vuelve a intentarlo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisService-create-Exception::Error en el Service al intentar persistir el País", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de País. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Actualiza un País existente.
    @Override
    public PaisResponseDTO update(Long id, PaisUpdateDTO dto) throws Exception {
        try {
            //intentar
            log.info("PaisService-update::Iniciando la operación para actualizar pais con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Pais> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el país con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            optional.get().setName(dto.getName());
            optional.get().setCodigoIso2(dto.getCodigoIso2());
            optional.get().setCodigoIso3(dto.getCodigoIso3());
            optional.get().setCapital(dto.getCapital());
            optional.get().setPoblacion(dto.getPoblacion());
            optional.get().setArea(dto.getArea());
            optional.get().setIdioma(dto.getIdioma());
            optional.get().setMoneda(dto.getMoneda());
            optional.get().setDominioTld(dto.getDominioTld());
            optional.get().setHusoHorario(dto.getHusoHorario());
            optional.get().setContinente(dto.getContinente());
            optional.get().setUpdatedBy("system");
            optional.get().setUpdatedAt(LocalDateTime.now());

            // Registrar auditoría, registrar el evento de actualización.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.UPDATE);
            auditoria.setEntity("Pais");
            auditoria.setEntityId(optional.get().getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Actualización de un país existente");
            auditoriaRepository.save(auditoria);

            log.info("PaisService-update::Acción completada sin errores");
            return mapToResponseDTO(repository.save(optional.get()));
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisService-update-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("PaisService-update-DataIntegrityViolationException::Error en el Service al intentar persistir el País, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El Nombre: '"+dto.getName()+"' ya está en uso. Por favor, inténtelo de nuevo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisService-update-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la actualización. Por favor, contacta al administrador del sistema.");
        }
    }

    // Marca un registro de País como si fuera eliminado físicamente de la BD.
    @Override
    public void delete(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisService-delete::Iniciando la operación para eliminar pais con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<Pais> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                //log.error("PaisService-delete2::Error en el Service, Pais con el ID: {}, No encontrado ", id);
                throw new NotFoundException("No se encontró el país con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            Pais entity = optional.get();
            entity.setEstado(Estado.INACTIVO);
            entity.setDeleted(true);
            entity.setDeletedBy("system");
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
            log.info("PaisService-delete::El país con ID: {} se ha marcado como eliminado.", id);

            // Registrar auditoría, registrar el evento de eliminación.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.DELETE);
            auditoria.setEntity("Pais");
            auditoria.setEntityId(entity.getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Eliminación de un país");
            auditoriaRepository.save(auditoria);

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

}
