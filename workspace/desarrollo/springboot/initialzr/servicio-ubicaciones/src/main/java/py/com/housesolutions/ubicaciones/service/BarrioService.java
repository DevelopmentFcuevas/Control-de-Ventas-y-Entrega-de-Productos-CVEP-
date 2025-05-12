package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Barrio;
import py.com.housesolutions.ubicaciones.model.BarrioCreateDTO;
import py.com.housesolutions.ubicaciones.model.BarrioDTO;
import py.com.housesolutions.ubicaciones.model.BarrioResponseDTO;
import py.com.housesolutions.ubicaciones.model.BarrioUpdateDTO;

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
}
