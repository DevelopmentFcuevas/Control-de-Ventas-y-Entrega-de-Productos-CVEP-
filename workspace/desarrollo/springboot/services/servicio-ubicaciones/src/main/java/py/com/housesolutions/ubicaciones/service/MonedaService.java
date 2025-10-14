package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Moneda;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.*;

import java.time.LocalDate;
import java.util.List;

public interface MonedaService {
    MonedaDTO mapToDTO(final Moneda entity);
    MonedaResponseDTO mapToResponseDTO(Moneda entity);
    Moneda mapToEntity(final MonedaDTO dto);
    List<MonedaResponseDTO> findAll() throws Exception;
    MonedaResponseDTO get(Long id) throws Exception;
    MonedaResponseDTO getByName(String name) throws Exception;
    MonedaDTO getAll(Long id) throws Exception;
    MonedaResponseDTO create(MonedaCreateDTO request) throws Exception;
    MonedaResponseDTO update(Long id, MonedaUpdateDTO dto) throws Exception;
    void delete(final Long id) throws Exception;
    long countByEstado(Estado estado) throws Exception;
    long countByFechaCreacion(LocalDate fecha) throws Exception;
}
