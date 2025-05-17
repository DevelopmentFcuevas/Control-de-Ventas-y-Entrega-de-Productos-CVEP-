package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Ciudad;
import py.com.housesolutions.ubicaciones.model.*;
import py.com.housesolutions.ubicaciones.model.CiudadCreateDTO;
import py.com.housesolutions.ubicaciones.model.CiudadDTO;
import py.com.housesolutions.ubicaciones.model.CiudadResponseDTO;
import py.com.housesolutions.ubicaciones.model.CiudadUpdateDTO;

import java.util.List;

public interface CiudadService {
    CiudadDTO mapToDTO(Ciudad entity);
    CiudadResponseDTO mapToResponseDTO(Ciudad entity);
    Ciudad mapToEntity(CiudadDTO dto);
    List<CiudadResponseDTO> findAll() throws Exception;
    CiudadResponseDTO get(Long id) throws Exception;
    CiudadDTO getAll(Long id) throws Exception;
    CiudadResponseDTO create(CiudadCreateDTO dto) throws Exception;
    CiudadResponseDTO update(Long id, CiudadUpdateDTO dto) throws Exception;
    void delete(final Long id) throws Exception;
}
