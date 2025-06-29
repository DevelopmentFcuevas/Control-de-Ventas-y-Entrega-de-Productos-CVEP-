package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Departamento;
import py.com.housesolutions.ubicaciones.model.*;

import java.time.LocalDate;
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
    long countByEstado(Estado estado) throws Exception;
    long countByFechaCreacion(LocalDate fecha) throws Exception;
}
