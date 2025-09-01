package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Barrio;
import py.com.housesolutions.ubicaciones.model.*;

import java.time.LocalDate;
import java.util.List;

public interface BarrioService {
    BarrioDTO mapToDTO(Barrio entity);
    BarrioResponseDTO mapToResponseDTO(Barrio entity);
    Barrio mapToEntity(BarrioDTO dto);
    List<BarrioResponseDTO> findAll() throws Exception;
    BarrioResponseDTO get(Long id) throws Exception;
    BarrioResponseDTO create(BarrioCreateDTO request) throws Exception;
    BarrioResponseDTO update(Long id, BarrioUpdateDTO request ) throws Exception;
    void delete(Long id) throws Exception;
    long countByEstado(Estado estado) throws Exception;
    long countByFechaCreacion(LocalDate fecha) throws Exception;
}
