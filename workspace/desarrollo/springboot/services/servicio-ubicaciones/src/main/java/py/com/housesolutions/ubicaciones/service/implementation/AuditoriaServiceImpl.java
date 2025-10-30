package py.com.housesolutions.ubicaciones.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.model.Action;
import py.com.housesolutions.ubicaciones.model.AuditoriaDTO;
import py.com.housesolutions.ubicaciones.repos.AuditoriaRepository;
import py.com.housesolutions.ubicaciones.service.AuditoriaService;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuditoriaServiceImpl implements AuditoriaService {
    private final AuditoriaRepository repository;

    public AuditoriaServiceImpl(AuditoriaRepository repository) {
        this.repository = repository;
    }

    // Mapea una entidad Auditoria a un DTO.
    @Override
    public AuditoriaDTO mapToDTO(Auditoria entity) {
        log.info("AuditoriaService-mapToDTO::Iniciando Servicio para mapear una entidad Auditoria a un DTO");
        AuditoriaDTO dto = new AuditoriaDTO();

        // Mapeo de cada campo de la entidad a su equivalente en el DTO.
        dto.setId(entity.getId());
        dto.setAction(entity.getAction());
        dto.setEntity(entity.getEntity());
        dto.setEntityId(entity.getEntityId());
        dto.setPerformedBy(entity.getPerformedBy());
        dto.setTimestamp(entity.getTimestamp());
        dto.setDetails(entity.getDetails());
        log.info("AuditoriaService-mapToDTO::Acción completada sin errores.");
        return dto;
    }

    // Mapea un DTO a una entidad Auditoria.
    @Override
    public Auditoria mapToEntity(AuditoriaDTO dto) {
        log.info("AuditoriaService-mapToEntity::Iniciando Servicio para mapear un DTO a una entidad Auditoria");
        Auditoria entity = new Auditoria();

        // Mapeo de cada campo del DTO a su equivalente en la entidad.
        entity.setId(dto.getId());
        entity.setAction(dto.getAction());
        entity.setEntity(dto.getEntity());
        entity.setEntityId(dto.getEntityId());
        entity.setPerformedBy(dto.getPerformedBy());
        entity.setTimestamp(dto.getTimestamp());
        entity.setDetails(dto.getDetails());

        log.info("AuditoriaService-mapToEntity::Acción completada sin errores.");
        return entity;
    }

    // Crea una nueva Auditoria.
    @Override
    public AuditoriaDTO create(AuditoriaDTO request) throws Exception {
        try {
            //intentar
            log.info("AuditoriaService-create::Persistir en la Base de datos la auditoria");
            AuditoriaDTO dto = new AuditoriaDTO();
            dto.setAction(request.getAction());
            dto.setEntity(request.getEntity());
            dto.setEntityId(request.getEntityId());
            dto.setPerformedBy(request.getPerformedBy());
            dto.setTimestamp(request.getTimestamp());
            dto.setDetails(request.getDetails());

            Auditoria entity = mapToEntity(dto);
            //Auditoria entity = mapToEntity(request);
            Auditoria savedEntity = repository.save(entity);

            log.info("AuditoriaService-create::Acción completada sin errores");
            return mapToDTO(savedEntity);
            //throw new RuntimeException("Error simulado para probar el manejo de excepciones.");
        } catch (Exception e) {
            //capturar, la raridad
            log.error("AuditoriaService-create-Exception::Error en el Service al intentar persistir la Auditoria", e);
            throw new Exception("Error al intentar guardar en la base de datos el nuevo registro de Auditoria. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

    @Override
    public AuditoriaDTO registrarAuditoria(Action action,
                                           String entityName,
                                           Long entityId,
                                           String performedBy,
                                           String details) throws Exception {
        try {
            log.info("AuditoriaService-registrarAuditoria::Registrando acción [{}] para [{}] con ID [{}]",
                    action, entityName, entityId);
            AuditoriaDTO auditoriaDTO = new AuditoriaDTO();
            auditoriaDTO.setAction(action);
            auditoriaDTO.setEntity(entityName);
            auditoriaDTO.setEntityId(entityId);
            auditoriaDTO.setPerformedBy(performedBy);
            auditoriaDTO.setTimestamp(LocalDateTime.now());
            auditoriaDTO.setDetails(details);

            // Reutilizar el método create para persistir la auditoría
            //create(auditoriaDTO);
            log.info("AuditoriaService-registrarAuditoria::Auditoría registrada correctamente para [{}]", entityName);
            //return auditoriaDTO;
            return create(auditoriaDTO);
        } catch (Exception e) {
            log.error("AuditoriaService-registrarAuditoria::Error registrando auditoría para [{}]: {}", entityName, e.getMessage(), e);
            throw new Exception("Error registrando auditoría. Por favor, inténtelo de nuevo más tarde o consulte con el Administrador del Sistema.");
        }
    }

}
