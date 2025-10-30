package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.domain.PaisMoneda;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.repos.PaisMonedaRepository;
import py.com.housesolutions.ubicaciones.service.MonedaService;
import py.com.housesolutions.ubicaciones.service.PaisMonedaService;
import py.com.housesolutions.ubicaciones.service.PaisService;
import py.com.housesolutions.ubicaciones.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaisMonedaServiceImpl implements PaisMonedaService {
    private final PaisMonedaRepository repository;
    private final AuditoriaRepository auditoriaRepository;
    private final PaisService paisService;
    private final MonedaService monedaService;

    public PaisMonedaServiceImpl(PaisMonedaRepository repository,
                                 AuditoriaRepository auditoriaRepository,
                                 PaisService paisService,
                                 MonedaService monedaService) {
        this.repository = repository;
        this.auditoriaRepository = auditoriaRepository;
        this.paisService = paisService;
        this.monedaService = monedaService;
    }

    public PaisDTO getPaisById(Long id) {
        PaisDTO paisDTO;
        try {
            log.info("PaisMonedaService-getPaisById::Iniciando Servicio para obtener país por ID");
            paisDTO = paisService.getAll(id);
            log.info("PaisMonedaService-getPaisById::Acción completada sin errores.");
            return paisDTO;
        } catch (Exception e) {
            log.error("PaisMonedaService-getPaisById::Error en el Service al buscar Pais", e);
            return paisDTO = null;
        }
    }

    public MonedaDTO getMonedaById(Long id) {
        MonedaDTO monedaDTO;
        try {
            log.info("PaisMonedaService-getMonedaById::Iniciando Servicio para obtener moneda por ID");
            monedaDTO = monedaService.getAll(id);
            log.info("PaisMonedaService-getMonedaById::Acción completada sin errores.");
            return monedaDTO;
        } catch (Exception e) {
            log.error("PaisMonedaService-getMonedaById::Error en el Service al buscar Moneda", e);
            return monedaDTO = null;
        }
    }

    public PaisResponseDTO getPaisResponseById(Long id) {
        log.info("PaisMonedaService-getPaisResponseById::Iniciando Servicio para obtener país por ID");
        PaisResponseDTO paisResponseDTO;
        try {
            paisResponseDTO = paisService.get(id);
            log.info("PaisMonedaService-getPaisResponseById::Acción completada sin errores.");
            return paisResponseDTO;
        } catch (Exception e) {
            log.error("PaisMonedaService-getPaisResponseById::Error en el Service al buscar Pais", e);
            return paisResponseDTO = null;
        }
    }

    public MonedaResponseDTO getMonedaResponseById(Long id) {
        log.info("PaisMonedaService-getMonedaResponseById::Iniciando Servicio para obtener moneda por ID");
        MonedaResponseDTO monedaResponseDTO;
        try {
            monedaResponseDTO = monedaService.get(id);
            log.info("PaisMonedaService-getMonedaResponseById::Acción completada sin errores.");
            return monedaResponseDTO;
        } catch (Exception e) {
            log.error("PaisMonedaService-getMonedaResponseById::Error en el Service al buscar Moneda", e);
            return monedaResponseDTO = null;
        }
    }

    // Mapea una entidad PaisMoneda a un DTO.
    @Override
    public PaisMonedaDTO mapToDTO(PaisMoneda entity) {
        log.info("PaisMonedaService-mapToDTO::Iniciando Servicio para mapear una entidad PaisMoneda a un DTO");

        PaisMonedaDTO dto = new PaisMonedaDTO();

        // Mapeo de cada campo de la entidad a su equivalente en el DTO.
        dto.setId(entity.getId());

        //PaisDTO paisDTO = paisService.getAll(entity.getPais().getId());
        PaisDTO paisDTO = getPaisById(entity.getPais().getId());
        if (paisDTO != null) {
            dto.setPais(paisDTO);
        }

        MonedaDTO monedaDTO = getMonedaById(entity.getMoneda().getId());
        if (monedaDTO != null) {
            dto.setMoneda(monedaDTO);
        }

        dto.setEsOficial(entity.getEsOficial());
        dto.setEsPrimaria(entity.getEsPrimaria());
        dto.setValidoDesde(entity.getValidoDesde());
        dto.setValidoHasta(entity.getValidoHasta());
        dto.setEstado(entity.getEstado());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeleted(entity.isDeleted());
        dto.setDeletedBy(entity.getDeletedBy());
        dto.setDeletedAt(entity.getDeletedAt());

        log.info("PaisMonedaService-mapToDTO::Acción completada sin errores.");
        return dto;
    }

    // Mapea una entidad PaisMoneda a un PaisMonedaResponseDTO para la respuesta.
    @Override
    public PaisMonedaResponseDTO mapToResponseDTO(PaisMoneda entity) {
        log.info("PaisMonedaService-mapToResponseDTO::Iniciando Servicio para mapear una entidad PaisMoneda a un PaisMonedaResponseDTO para la respuesta");

        PaisMonedaResponseDTO response = new PaisMonedaResponseDTO();

        // Mapeo de cada campo de la entidad a su equivalente en el ResponseDTO.
        response.setId(entity.getId());

        PaisResponseDTO paisResponseDTO = getPaisResponseById(entity.getPais().getId());
        if (paisResponseDTO != null) {
            response.setPais(paisResponseDTO);
        }

        MonedaResponseDTO monedaResponseDTO = getMonedaResponseById(entity.getMoneda().getId());
        if (monedaResponseDTO != null) {
            response.setMoneda(monedaResponseDTO);
        }

        response.setEsOficial(entity.getEsOficial());
        response.setEsPrimaria(entity.getEsPrimaria());
        response.setValidoDesde(entity.getValidoDesde());
        response.setValidoHasta(entity.getValidoHasta());
        response.setEstado(entity.getEstado());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        log.info("PaisMonedaService-mapToResponseDTO::Acción completada sin errores.");
        return response;
    }

    // Mapea un DTO a una entidad PaisMoneda.
    @Override
    public PaisMoneda mapToEntity(PaisMonedaDTO dto) {
        log.info("PaisMonedaService-mapToEntity::Iniciando Servicio para mapear un DTO a una entidad PaisMoneda");

        PaisMoneda entity = new PaisMoneda();

        // Mapeo de cada campo del DTO a su equivalente en la entidad.
        entity.setId(dto.getId());

        PaisDTO paisDTO = getPaisById(dto.getPais().getId());
        if (paisDTO != null) {
            entity.setPais(paisService.mapToEntity(paisDTO) );
        }

        MonedaDTO monedaDTO = getMonedaById(dto.getMoneda().getId());
        if (monedaDTO != null) {
            entity.setMoneda(monedaService.mapToEntity(monedaDTO));
        }

        entity.setEsOficial(dto.getEsOficial());
        entity.setEsPrimaria(dto.getEsPrimaria());
        entity.setValidoDesde(dto.getValidoDesde());
        entity.setValidoHasta(dto.getValidoHasta());
        entity.setEstado(dto.getEstado());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setDeleted(dto.isDeleted());
        entity.setDeletedBy(dto.getDeletedBy());
        entity.setDeletedAt(dto.getDeletedAt());

        log.info("PaisMonedaService-mapToEntity::Acción completada sin errores.");
        return entity;
    }

    // Busca todos los PaísesMonedas activos.
    @Transactional(readOnly = true)
    @Override
    public List<PaisMonedaResponseDTO> findAll() throws Exception {
        try {
            //intentar
            log.info("PaisMonedaService-findAll::Iniciando Servicio para obtener listado de PaísesMonedas");
            List<PaisMoneda> list = repository.findAllActive();
            List<PaisMonedaResponseDTO> dtoList = new ArrayList<>();
            for (PaisMoneda entity : list) {
                PaisMonedaResponseDTO dto = mapToResponseDTO(entity);
                dtoList.add(dto);
            }
            log.info("PaisMonedaService-findAll::Acción completada sin errores.");
            return dtoList;
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataAccessException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-findAll-DataAccessException::Error en el Service no se puede acceder a la Base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisMonedaService-findAll-Exception::Error en el Service al obtener el listado de PaísesMonedas", e);
            throw new Exception("Error al obtener el Listado de PaísesMonedas. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Busca un PaísMoneda activo por ID.
    @Transactional(readOnly = true)
    @Override
    public PaisMonedaResponseDTO get(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisMonedaService-get::Iniciando Servicio para obtener PaísMoneda buscado por ID");

            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<PaisMoneda> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el PaísMoneda con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("PaisMonedaService-get::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-get-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("PaisMonedaService-get-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("PaisMonedaService-get-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    // Busca un PaísMoneda activo por ID y retorna todas sus propiedades.
    @Override
    public PaisMonedaDTO getAll(Long id) throws Exception {
        try {
            //intentar
            log.info("PaisMonedaService-getAll::Iniciando Servicio para obtener PaisMoneda buscado por ID");
            Optional<PaisMoneda> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                log.error("PaisMonedaService-getAll::Error en el Service, PaisMoneda con el ID: {}, No encontrado ", id);
                throw new NotFoundException("No se encontró el PaísMoneda con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("PaisMonedaService-getAll::Acción completada sin errores");
            return mapToDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-getAll-NotFoundException::Error en el Service, PaísMoneda con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        }  catch (Exception e) {
            //capturar, la raridad
            log.error("PaisMonedaService-getAll-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }



    // Busca un PaísMoneda activo por IdPais y EsPrimaria.
    @Transactional(readOnly = true)
    @Override
    public PaisMonedaResponseDTO getByPaisIdAndEsPrimaria(Long paisId, Boolean esPrimaria) throws Exception {
        try {
            //intentar
            log.info("PaisMonedaService-getByPaisIdAndEsPrimaria::Iniciando Servicio para obtener PaísMoneda buscado por IdPaisAndEsPrimaria {}, y EsPrimaria {}", paisId, esPrimaria);

            if (paisId == null) {
                throw new MissingParameterException("El parámetro 'paisId' es requerido.");
            }

            Optional<PaisMoneda> optional = repository.findByPais_IdAndEsPrimaria(paisId, esPrimaria);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el PaísMoneda con el IdPais " + paisId + " y el estado EsPrimaria" + ". Por favor, verifica el IdPais y vuelve a intentarlo.");
            }

            log.info("PaisMonedaService-getByPaisIdAndEsPrimaria::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-getByPaisIdAndEsPrimaria-MissingParameterException::Error en el Service, no se recibió el parámetro paisId");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-getByPaisIdAndEsPrimaria-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", paisId);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("PaisMonedaService-getByPaisIdAndEsPrimaria-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("PaisMonedaService-getByPaisIdAndEsPrimaria-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }

    @Override
    public PaisMonedaResponseDTO create(PaisMonedaCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("PaisMonedaService-create::Persistir en la Base de datos el País-Moneda");

            // Verificar si el país ya existe como eliminado
            //Optional<Pais> optionalDeleted = repository.findByNameAndDeleted(request.getName());
            //if (optionalDeleted.isPresent()) {

            //} else {
                // Crear nuevo registro si no existe eliminado
                PaisMonedaDTO dto = new PaisMonedaDTO();

                PaisDTO paisDTO = getPaisById(request.getPais().getId());
                dto.setPais(paisDTO);

                MonedaDTO monedaDTO = getMonedaById(request.getMoneda().getId());
                dto.setMoneda(monedaDTO);

                dto.setEsOficial(request.getEsOficial());
                dto.setEsPrimaria(request.getEsPrimaria());
                dto.setValidoDesde(request.getValidoDesde());
                dto.setValidoHasta(request.getValidoHasta());
                dto.setEstado(Estado.ACTIVO);
                dto.setCreatedBy("system");
                dto.setCreatedAt(LocalDateTime.now());

                PaisMoneda entity = mapToEntity(dto);
                PaisMoneda savedEntity = repository.save(entity);

                // Registrar auditoría, registrar el evento de creación.
                Auditoria auditoria = new Auditoria();
                auditoria.setAction(Action.CREATE);
                auditoria.setEntity("PaisMoneda");
                auditoria.setEntityId(savedEntity.getId());
                auditoria.setPerformedBy("system");
                auditoria.setTimestamp(LocalDateTime.now());
                auditoria.setDetails("Creación de un nuevo país-moneda");
                auditoriaRepository.save(auditoria);

                log.info("PaisMonedaService-create::Acción completada sin errores");
                return mapToResponseDTO(savedEntity);
            //}
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-create-DataIntegrityViolationException::Error en el Service al intentar persistir el País-Moneda, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El........**** nombre de país '" + "request.getName()" + "' ya existe en nuestra base de datos. El nombre de un nuevo registro para Países no se puede repetir. Por favor, verifica el Nombre y vuelve a intentarlo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisMonedaService-create-Exception::Error en el Service al intentar persistir el País-Moneda", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de País-Moneda. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    // Actualiza un PaísMoneda existente.
    @Override
    public PaisMonedaResponseDTO update(Long id, PaisMonedaUpdateDTO dto) throws Exception {
        try {
            //intentar
            log.info("PaisMonedaService-update::Iniciando la operación para actualizar Pais-Moneda con ID: {}", id);
            if (id == null) {
                throw new MissingParameterException("El parámetro 'id' es requerido.");
            }

            Optional<PaisMoneda> optional = repository.findByIdAndNotDeleted(id);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el País-Moneda con el ID " + id + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            //pais
            PaisDTO paisDTO = getPaisById(dto.getPais().getId());
            optional.get().setPais( paisService.mapToEntity(paisDTO) );

            //moneda
            MonedaDTO monedaDTO = getMonedaById(dto.getMoneda().getId());
            optional.get().setMoneda( monedaService.mapToEntity(monedaDTO));

            optional.get().setEsOficial(dto.getEsOficial());
            optional.get().setEsPrimaria(dto.getEsPrimaria());
            optional.get().setValidoDesde(dto.getValidoDesde());
            optional.get().setValidoHasta(dto.getValidoHasta());
            optional.get().setUpdatedBy("system");
            optional.get().setUpdatedAt(LocalDateTime.now());

            PaisMoneda savedEntity = repository.save(optional.get());

            // Registrar auditoría, registrar el evento de actualización.
            Auditoria auditoria = new Auditoria();
            auditoria.setAction(Action.UPDATE);
            auditoria.setEntity("PaisMoneda");
            auditoria.setEntityId(savedEntity.getId());
            auditoria.setPerformedBy("system");
            auditoria.setTimestamp(LocalDateTime.now());
            auditoria.setDetails("Actualización de un País-Moneda existente");
            auditoriaRepository.save(auditoria);

            log.info("PaisMonedaService-update::Acción completada sin errores");
            return mapToResponseDTO(savedEntity);
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-get-MissingParameterException::Error en el Service, no se recibió el parámetro ID");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-update-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", id);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataIntegrityViolationException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-update-DataIntegrityViolationException::Error en el Service al intentar persistir el País, violación de integridad de datos", e);
            throw new NameAlreadyExistsException("El Nombre: '"+ "dto.getName()"+"' ya está en uso. Por favor, inténtelo de nuevo.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("PaisMonedaService-update-Exception::Error inesperado en el Service", e);
            throw new Exception("Ha ocurrido un error inesperado en la actualización. Por favor, contacta al administrador del sistema.");
        }
    }

    //@Override
    //public PaisMonedaResponseDTO registrarPrimerPaisMoneda(Long paisId, Long monedaId) throws Exception {
        //try {
            //intentar
            //log.info("PaisMonedaService-registrarPrimerPaisMoneda::Iniciando Servicio para guardar primera relación de Pais Moneda");
            //PaisDTO paisDTO = paisService.getAll(paisId);
            //MonedaDTO monedaDTO = monedaService.getAll(monedaId);
            //PaisMonedaCreateDTO paisMonedaCreateDTO = new PaisMonedaCreateDTO();
            //paisMonedaCreateDTO.setPais(paisDTO);
            //paisMonedaCreateDTO.setMoneda(monedaDTO);
            //paisMonedaCreateDTO.setValidoDesde(LocalDate.now());

            // Reutilizar el método create para persistir la primera relacion Pais-Moneda
            //log.info("PaisMonedaService-registrarPrimerPaisMoneda::Relación Pais-Moneda registrada correctamente");
            //return create(paisMonedaCreateDTO);
        //} catch (Exception e) {
            //capturar, la raridad
            //log.error("PaisMonedaService-registrarPrimerPaisMoneda-Exception::Error en el Service al intentar persistir el País-Moneda", e);
            //throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de País-Moneda. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        //}
    //}


    // Busca un PaísMoneda activo por IdPais y IdMoneda.
    @Transactional(readOnly = true)
    @Override
    public PaisMonedaResponseDTO findByPaisIdAndMonedaId(Long paisId, Long monedaId) throws Exception {
        try {
            //intentar
            log.info("PaisMonedaService-findByPaisIdAndMonedaId::Iniciando Servicio para obtener PaísMoneda buscado por IdPais y IdMoneda");

            if (paisId == null) {
                throw new MissingParameterException("El parámetro 'paisId' es requerido.");
            }

            if (monedaId == null) {
                throw new MissingParameterException("El parámetro 'monedaId' es requerido.");
            }

            //Optional<PaisMoneda> optional = repository.findByIdAndNotDeleted(id);
            Optional<PaisMoneda> optional = repository.findByPais_IdAndMoneda_Id(paisId, monedaId);
            if (optional.isEmpty()) {
                throw new NotFoundException("No se encontró el PaísMoneda con el IDPais " + paisId + ". Por favor, verifica el ID y vuelve a intentarlo.");
            }

            log.info("PaisMonedaService-findByPaisIdAndMonedaId::Acción completada sin errores");
            return mapToResponseDTO(optional.get());
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (MissingParameterException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-findByPaisIdAndMonedaId-MissingParameterException::Error en el Service, no se recibió el parámetro IDPais o IDMoneda");
            throw e; // Dejamos que la excepción MissingParameterException se propague
        } catch (NotFoundException e) {
            //capturar, la raridad
            log.error("PaisMonedaService-findByPaisIdAndMonedaId-NotFoundException::Error en el Service, País con el ID: {}, No encontrado ", paisId);
            throw e; // Dejamos que la excepción NotFoundException se propague
        } catch (DataAccessException e) {
            // Capturamos DataAccessException para manejar específicamente errores de base de datos.
            log.error("PaisMonedaService-findByPaisIdAndMonedaId-DataAccessException::Error al acceder a la base de datos", e);
            throw new DatabaseException("Error al acceder a la base de datos. Por favor, intenta nuevamente más tarde.");
        } catch (Exception e) {
            //Capturamos Exception para manejar otros tipos de excepciones inesperadas.
            log.error("PaisMonedaService-findByPaisIdAndMonedaId-Exception::Error inesperado", e);
            throw new InternalServerErrorException("Ha ocurrido un error inesperado. Por favor, contacta al administrador del sistema.");
        }
    }







    /*
    @Override
    public boolean existsByPaisAndMoneda(Pais pais, Moneda moneda) {
        return false;
    }

    @Override
    public boolean existsByPaisIdAndMonedaId(Long paisId, Long monedaId) {
        return false;
    }

    @Override
    public Optional<PaisMoneda> findByPaisAndMoneda(Pais pais, Moneda moneda) {
        return Optional.empty();
    }

    @Override
    public Optional<PaisMoneda> findByPaisIdAndMonedaId(Long paisId, Long monedaId) {
        return Optional.empty();
    }

    @Override
    public List<PaisMoneda> findByPaisId(Long paisId) {
        return null;
    }

    @Override
    public PaisMoneda create(Pais pais, Moneda moneda, Boolean esOficial, Boolean esPrimaria, LocalDate validoDesde, LocalDate validoHasta) {
        return null;
    }

    @Override
    public void deleteByPaisAndMoneda(Pais pais, Moneda moneda) {

    }

    @Override
    public void deleteByPaisIdAndMonedaId(Long paisId, Long monedaId) {

    }
    */
}
