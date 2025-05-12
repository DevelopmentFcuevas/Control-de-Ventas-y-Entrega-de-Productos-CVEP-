package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.model.*;

import java.time.LocalDate;
import java.util.List;

public interface PaisService {
    PaisDTO mapToDTO(final Pais entity);
    PaisResponseDTO mapToResponseDTO(Pais entity);
    Pais mapToEntity(final PaisDTO dto);
    List<PaisResponseDTO> findAll() throws Exception;
    PaisResponseDTO get(Long id) throws Exception;
    PaisResponseDTO getByName(String name) throws Exception;
    PaisDTO getAll(Long id) throws Exception;
    PaisResponseDTO create(PaisCreateDTO dto) throws Exception;
    PaisResponseDTO update(Long id, PaisUpdateDTO dto) throws Exception;
    void delete(final Long id) throws Exception;
    long countByEstado(Estado estado) throws Exception;
    long countByFechaCreacion(LocalDate fecha) throws Exception;
}
