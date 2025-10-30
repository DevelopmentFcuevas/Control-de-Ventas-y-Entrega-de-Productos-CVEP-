package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Auditoria;
import py.com.housesolutions.ubicaciones.model.Action;
import py.com.housesolutions.ubicaciones.model.AuditoriaDTO;

public interface AuditoriaService {
    /*
    PaisDTO mapToDTO(final Pais entity);
    PaisResponseDTO mapToResponseDTO(Pais entity);
    Pais mapToEntity(final PaisDTO dto);
    PaisResponseDTO create(PaisCreateDTO request) throws Exception;
    */
    AuditoriaDTO mapToDTO(final Auditoria entity);
    Auditoria mapToEntity(final AuditoriaDTO dto);
    AuditoriaDTO create(AuditoriaDTO request) throws Exception;
    AuditoriaDTO registrarAuditoria(Action action, String entityName, Long entityId, String performedBy, String details) throws Exception;
}
