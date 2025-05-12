package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Departamento;
import py.com.housesolutions.ubicaciones.model.DepartamentoCreateDTO;
import py.com.housesolutions.ubicaciones.model.DepartamentoDTO;
import py.com.housesolutions.ubicaciones.model.DepartamentoResponseDTO;
import py.com.housesolutions.ubicaciones.model.DepartamentoUpdateDTO;

import java.util.List;

public interface DepartamentoService {
    DepartamentoDTO mapToDTO(Departamento entity);
    DepartamentoResponseDTO mapToResponseDTO(Departamento entity);
    Departamento mapToEntity(DepartamentoDTO dto);
    List<DepartamentoResponseDTO> findAll() throws Exception;
    DepartamentoResponseDTO get(Long id) throws Exception;
    DepartamentoDTO getAll(Long id) throws Exception;
    DepartamentoResponseDTO create(DepartamentoCreateDTO dto) throws Exception;
    DepartamentoResponseDTO update(Long id, DepartamentoUpdateDTO dto) throws Exception;
    void delete(final Long id) throws Exception;
}
