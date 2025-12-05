/*
package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.housesolutions.ubicaciones.domain.Moneda;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.domain.PaisMoneda;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.repos.MonedaRepository;
import py.com.housesolutions.ubicaciones.repos.PaisMonedaRepository;
import py.com.housesolutions.ubicaciones.repos.PaisRepository;
import py.com.housesolutions.ubicaciones.service.AuditoriaService;
import py.com.housesolutions.ubicaciones.service.MonedaService;
import py.com.housesolutions.ubicaciones.service.PaisService;
import py.com.housesolutions.ubicaciones.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaisServiceImplCOPIA implements PaisService {

    private final PaisRepository repository;
    private final MonedaRepository monedaRepository;
    private final PaisMonedaRepository paisMonedaRepository;
    private final MonedaService monedaService;
    private final AuditoriaRepository auditoriaRepository;
    //private final PaisMonedaService paisMonedaService;
    private final AuditoriaService auditoriaService;

    // Constructor que inyecta los repositorios.
    public PaisServiceImplCOPIA(final PaisRepository repository,
                                MonedaRepository monedaRepository,
                                PaisMonedaRepository paisMonedaRepository,
                                MonedaService monedaService, AuditoriaRepository auditoriaRepository,//,
                                //PaisMonedaService paisMonedaService
                                AuditoriaService auditoriaService) {
        this.repository = repository;
        this.monedaRepository = monedaRepository;
        this.paisMonedaRepository = paisMonedaRepository;
        this.monedaService = monedaService;
        this.auditoriaRepository = auditoriaRepository;
        //this.paisMonedaService = paisMonedaService;
        this.auditoriaService = auditoriaService;
    }

    public MonedaResponseDTO getMonedaResponseById(Long id) {
        log.info("PaisService-getMonedaResponseById::Iniciando Servicio para obtener moneda por ID");
        MonedaResponseDTO monedaResponseDTO;
        try {
            monedaResponseDTO = monedaService.get(id);
            log.info("PaisService-getMonedaResponseById::Acción completada sin errores.");
            return monedaResponseDTO;
        } catch (Exception e) {
            log.error("PaisService-getMonedaResponseById::Error en el Service al buscar Moneda", e);
            return monedaResponseDTO = null;
        }
    }


    //public PaisMonedaResponseDTO getPaisMonedaResponseById(Long id) {
    //    log.info("PaisService-getPaisMonedaResponseById::Iniciando Servicio para obtener PaísMoneda por ID");
    //    PaisMonedaResponseDTO paisMonedaResponseDTO;
    //    try {
    //        paisMonedaResponseDTO = paisMonedaService.get(id);
    //    } catch (Exception e) {
    //        log.error("PaisService-getPaisMonedaResponseById::Error en el Service al buscar PaisMoneda", e);
    //        return paisMonedaResponseDTO = null;
    //    }
    //}


    // Mapea una entidad Pais a un DTO.
    @Override
    public PaisDTO mapToDTO(Pais entity) {
        log.info("PaisService-mapToDTO::Iniciando Servicio para mapear una entidad Pais a un DTO");
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
        //dto.setMoneda(entity.getMoneda());
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

    // Mapea una entidad Pais a un PaisResponseDTO para la respuesta.
    @Override
    public PaisResponseDTO mapToResponseDTO(Pais entity) {
        log.info("PaisService-mapToResponseDTO::Iniciando Servicio para mapear una entidad Pais a un PaisResponseDTO para la respuesta");

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

        //response.setMoneda(entity.getMoneda());
        //MonedaResponseDTO monedaResponseDTO = getMonedaResponseById()
        //PaisMonedaResponseDTO paisMonedaResponseDTO = getPaisMonedaResponseById(entity.getPaisMonedas().get())

        response.setDominioTld(entity.getDominioTld());
        response.setHusoHorario(entity.getHusoHorario());
        response.setContinente(entity.getContinente());
        //response.setImagePath(entity.getImagePath());
        response.setEstado(entity.getEstado());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        log.info("PaisService-mapToResponseDTO::Acción completada sin errores.");
        return response;
    }

    // Mapea un DTO a una entidad Pais.
    @Override
    public Pais mapToEntity(PaisDTO dto) {
        log.info("PaisService-mapToEntity::Iniciando Servicio para mapear un DTO a una entidad Pais");

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
        //entity.setMoneda(dto.getMoneda());
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

        log.info("PaisService-mapToEntity::Acción completada sin errores.");
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
    public PaisResponseDTO create(PaisCreateDTO request) throws Exception {
        try {
            //intentar
            log.info("PaisService-create::Persistir en la Base de datos el país");

            // Verificar si el país ya existe como eliminado
            Optional<Pais> optionalDeleted = repository.findByNameAndDeleted(request.getName());
            if (optionalDeleted.isPresent()) {
                // Reutilización de Registros Eliminados
                Pais deletedEntity = optionalDeleted.get();
                log.info("PaisService-create::Reactivando el país eliminado con ID: {}", deletedEntity.getId());

                deletedEntity.setName(request.getName());
                deletedEntity.setCodigoIso2(request.getCodigoIso2());
                deletedEntity.setCodigoIso3(request.getCodigoIso3());
                deletedEntity.setCapital(request.getCapital());
                deletedEntity.setPoblacion(request.getPoblacion());
                deletedEntity.setArea(request.getArea());
                deletedEntity.setIdioma(request.getIdioma());

                //deletedEntity.setMoneda(request.getMoneda());

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
                auditoriaService.registrarAuditoria(
                        Action.REACTIVATED,
                        "Pais",
                        reactivatedEntity.getId(),
                        "system",
                        "Reactivación de un país marcado como eliminado"
                );


                if (request.getMonedaId() != null) {
                    //
                    Optional<PaisMoneda> relaciones = paisMonedaRepository.findByPais_IdAndMoneda_Id(
                            deletedEntity.getId(), request.getMonedaId()
                    );
                    if (relaciones.isPresent()) {
                        PaisMoneda pm = new PaisMoneda();
                        pm.setId(relaciones.get().getId());
                        pm.setPais(relaciones.get().getPais());
                        pm.setMoneda(relaciones.get().getMoneda());
                        // Reactivar el registro
                        pm.setEstado(Estado.ACTIVO);
                        pm.setDeleted(false);
                        pm.setDeletedAt(null);
                        pm.setDeletedBy(null);
                        pm.setUpdatedAt(LocalDateTime.now());
                        pm.setUpdatedBy("system");

                        paisMonedaRepository.save(pm);
                    } else {
                        // Crea un nuevo Pais-Moneda
                        Optional<Moneda> moneda = monedaRepository.findById(request.getMonedaId());

                        PaisMoneda pm = new PaisMoneda();
                        pm.setPais(deletedEntity);
                        pm.setMoneda(moneda.get());
                        pm.setValidoDesde(LocalDate.now());
                        pm.setEstado(Estado.ACTIVO);
                        pm.setCreatedBy("system");
                        pm.setCreatedAt(LocalDateTime.now());
                        PaisMoneda paisMoneda = new PaisMoneda();
                        paisMoneda = paisMonedaRepository.save(pm);
                    }

                    // Registrar auditoría, registrar el evento de Reactivación.
                    auditoriaService.registrarAuditoria(
                            Action.REACTIVATED,
                            "PaisMoneda",
                            reactivatedEntity.getId(),
                            "system",
                            "Reactivación de un PaísMoneda marcado como eliminado"
                    );
                }


                log.info("PaisService-create::Acción completada sin errores");
                return mapToResponseDTO(reactivatedEntity);
            } else {
                // Crear nuevo registro si no existe como eliminado
                PaisDTO dto = new PaisDTO();
                dto.setName(request.getName());
                dto.setCodigoIso2(request.getCodigoIso2());
                dto.setCodigoIso3(request.getCodigoIso3());
                dto.setCapital(request.getCapital());
                dto.setPoblacion(request.getPoblacion());
                dto.setArea(request.getArea());
                dto.setIdioma(request.getIdioma());
                //dto.setMoneda(request.getMoneda());
                dto.setDominioTld(request.getDominioTld());
                dto.setHusoHorario(request.getHusoHorario());
                dto.setContinente(request.getContinente());
                dto.setEstado(Estado.ACTIVO);
                dto.setCreatedBy("system");
                dto.setCreatedAt(LocalDateTime.now());
                Pais entity = mapToEntity(dto);
                Pais savedEntity = repository.save(entity);

                // Registrar auditoría, registrar el evento de creación.
                auditoriaService.registrarAuditoria(
                        Action.CREATE,
                        "Pais",
                        savedEntity.getId(),
                        "system",
                        "Creación de un nuevo país"
                );

                // Para este caso en particular, es la primera vez que se carga un registro de pais, entonces
                // al crearse el pais, si el usuario selecciono alguna Moneda, crear la primera relación
                // de Pais-Moneda con valores por defecto.
                if (request.getMonedaId() != null) {
                    Optional<Moneda> moneda = monedaRepository.findById(request.getMonedaId());
                    if (moneda.isPresent()) {
                        PaisMoneda pm = new PaisMoneda();
                        pm.setPais(savedEntity);
                        pm.setMoneda(moneda.get());
                        pm.setValidoDesde(LocalDate.now());
                        pm.setEstado(Estado.ACTIVO);
                        pm.setCreatedBy("system");
                        pm.setCreatedAt(LocalDateTime.now());
                        //paisMonedaRepository.save(pm);
                        PaisMoneda paisMoneda = new PaisMoneda();
                        paisMoneda = paisMonedaRepository.save(pm);

                        // Registrar auditoría, registrar el evento de creación.
                        auditoriaService.registrarAuditoria(
                                Action.CREATE,
                                "PaisMoneda",
                                paisMoneda.getId(),
                                "system",
                                "Creación de un nuevo País-Moneda"
                        );
                    }
                }

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
            //optional.get().setMoneda(dto.getMoneda());

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
                    optional.get().getId(),
                    "system",
                    "Actualización de un país existente"
            );


            // ---------------------------------------------------------
            // 🪙 Actualizar relación PAIS-MONEDA (solo si viene monedaId)
            // ---------------------------------------------------------
            // Sí vino monedaId, actualizar la relación en pais_monedas con valores actualizados
            if (dto.getMonedaId() != null) {
                //    PaisMonedaResponseDTO paisMonedaResponseDTO = paisMonedaService.findByPaisIdAndMonedaId(id, dto.getMonedaId());
                //    if (paisMonedaResponseDTO != null) {
                //        //PARA PODER ACTUALIZAR MI PAISMONEDA, DEBO TENER PAISMONEDAID
                //        // Y PAISMONEDAUPDATEDTO(PaisDTO, MonedaDTO)
                //        //paisMonedaResponseDTO.getId()  = idPaisMoneda
                //        //paisMonedaResponseDTO.getPais().getId()
                //        PaisDTO paisDTO = getAll(paisMonedaResponseDTO.getPais().getId());
                //        MonedaDTO monedaDTO = monedaService.getAll( paisMonedaResponseDTO.getMoneda().getId() );
                //        PaisMonedaUpdateDTO paisMonedaUpdateDTO = new PaisMonedaUpdateDTO();
                //        paisMonedaUpdateDTO.setId(paisMonedaResponseDTO.getId());
                //        paisMonedaUpdateDTO.setPais(paisDTO);
                //        paisMonedaUpdateDTO.setMoneda(monedaDTO);
                //        //PaisMonedaResponseDTO paisMonedaUpdated = new PaisMonedaResponseDTO();
                //        //paisMonedaUpdated =
                //        paisMonedaService.update(paisMonedaResponseDTO.getId(), paisMonedaUpdateDTO);
                //    }

                // PRIMERO DEBO BUSCAR Y RECORRER POR PAIS-MONEDA Y DESACTIVAR LOS DEMAS REGISTROS
                // EN CASO DE QUE YA EXISTAN... Y ACTIVAR SOLO EL NUEVO CASO..
                //Optional<Moneda> moneda = monedaRepository.findById(dto.getMonedaId());
                //if (moneda.isPresent()) {
                //    PaisMoneda pm = new PaisMoneda();
                //    pm.setId(updatedEntity.getId());
                //    pm.setPais(updatedEntity);
                //    pm.setMoneda(moneda.get());
                //    pm.setEstado(Estado.ACTIVO);
                //    pm.setCreatedBy("system");
                //    pm.setCreatedAt(LocalDateTime.now());
                //    paisMonedaRepository.save(pm);
                //}

                Long nuevaMonedaId = dto.getMonedaId();

                List<PaisMoneda> relaciones = paisMonedaRepository.findAllByPais_IdAndNotDeleted(id);
                for (PaisMoneda pm: relaciones) {
                    pm.setEstado(Estado.INACTIVO);
                    pm.setDeleted(true);
                    pm.setDeletedBy("system");
                    pm.setDeletedAt(LocalDateTime.now());
                    paisMonedaRepository.save(pm);

                    auditoriaService.registrarAuditoria(
                            Action.UPDATE,
                            "PaisMoneda",
                            pm.getId(),
                            "system",
                            "Desactivación de relación anterior de País-Moneda"
                    );
                }

                // 2️⃣ Verificar si la nueva relación ya existía antes (para evitar duplicados)
                Optional<PaisMoneda> existente = paisMonedaRepository
                        .findByPais_IdAndMoneda_Id(id, nuevaMonedaId);

                PaisMoneda nuevaRelacion;
                //PaisMoneda nuevaRelacion = new PaisMoneda();

                if (existente.isPresent()) {
                    // Reactivar la relación existente
                    nuevaRelacion = existente.get();
                    nuevaRelacion.setEstado(Estado.ACTIVO);
                    nuevaRelacion.setDeleted(false);
                    nuevaRelacion.setDeletedBy(null);
                    nuevaRelacion.setDeletedAt(null);
                    nuevaRelacion.setUpdatedBy("system");
                    nuevaRelacion.setUpdatedAt(LocalDateTime.now());
                    nuevaRelacion.setEsPrimaria(true);
                    nuevaRelacion.setEsOficial(true);
                    paisMonedaRepository.save(nuevaRelacion);

                } else {
                    // Crear una nueva relación
                    Optional<Moneda> moneda = monedaRepository.findById(nuevaMonedaId);
                    if (moneda.isEmpty()) {
                        throw new NotFoundException("No se encontró la moneda con ID "+ nuevaMonedaId);
                    }

                    nuevaRelacion = new PaisMoneda();
                    nuevaRelacion.setPais(updatedEntity);
                    nuevaRelacion.setMoneda(moneda.get());
                    nuevaRelacion.setEstado(Estado.ACTIVO);
                    nuevaRelacion.setEsPrimaria(true);
                    nuevaRelacion.setEsOficial(true);
                    nuevaRelacion.setValidoDesde(LocalDate.now());
                    nuevaRelacion.setCreatedBy("system");
                    nuevaRelacion.setCreatedAt(LocalDateTime.now());
                    paisMonedaRepository.save(nuevaRelacion);
                }

                // 3️⃣ Registrar auditoría de nueva relación activa
                auditoriaService.registrarAuditoria(
                        Action.UPDATE,
                        "PaisMoneda",
                        nuevaRelacion.getId(),
                        "system",
                        "Cambio de moneda principal del país"
                );

            }

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

            // 1️⃣ Marcar el país como eliminado
            Pais entity = optional.get();
            entity.setEstado(Estado.INACTIVO);
            entity.setDeleted(true);
            entity.setDeletedBy("system");
            entity.setDeletedAt(LocalDateTime.now());
            repository.save(entity);
            log.info("PaisService-delete::El país con ID: {} se ha marcado como eliminado.", id);

            // Registrar auditoría, registrar el evento de eliminación.
            auditoriaService.registrarAuditoria(
                    Action.DELETE,
                    "Pais",
                    entity.getId(),
                    "system",
                    "Eliminación de un país"
            );


            // 2️⃣ Buscar relaciones PaisMoneda activas asociadas a este país
            List<PaisMoneda> relaciones = paisMonedaRepository.findByPais_Id(optional.get().getId());
            if (!relaciones.isEmpty()) {
                for (PaisMoneda pm : relaciones) {
                    pm.setEstado(Estado.INACTIVO);
                    pm.setDeleted(true);
                    pm.setDeletedBy("system");
                    pm.setDeletedAt(LocalDateTime.now());
                    paisMonedaRepository.save(pm);

                    // 3️⃣ Registrar auditoría por cada relación eliminada
                    auditoriaService.registrarAuditoria(
                            Action.DELETE,
                            "PaisMoneda",
                            pm.getId(),
                            "system",
                            "Eliminación lógica de la relación País-Moneda asociada al país con ID " + id
                    );
                    //log.info("PaisService-delete::PaisMoneda con ID: {} marcado como eliminado.", pm.getId());
                }
            } else {
                log.info("PaisService-delete::No se encontraron relaciones PaisMoneda para el país ID: {}", id);
            }

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
 */