package py.com.housesolutions.ubicaciones.service;

import py.com.housesolutions.ubicaciones.domain.Moneda;
import py.com.housesolutions.ubicaciones.domain.Pais;
import py.com.housesolutions.ubicaciones.domain.PaisMoneda;
import py.com.housesolutions.ubicaciones.model.PaisMonedaCreateDTO;
import py.com.housesolutions.ubicaciones.model.PaisMonedaDTO;
import py.com.housesolutions.ubicaciones.model.PaisMonedaResponseDTO;
import py.com.housesolutions.ubicaciones.model.PaisMonedaUpdateDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaisMonedaService {
    /*
    boolean existsByPaisAndMoneda(Pais pais, Moneda moneda);
    boolean existsByPaisIdAndMonedaId(Long paisId, Long monedaId);
    Optional<PaisMoneda> findByPaisAndMoneda(Pais pais, Moneda moneda);
    Optional<PaisMoneda> findByPaisIdAndMonedaId(Long paisId, Long monedaId);
    List<PaisMoneda> findByPaisId(Long paisId);
    PaisMoneda create(Pais pais, Moneda moneda, Boolean esOficial, Boolean esPrimaria, LocalDate validoDesde, LocalDate validoHasta);
    void deleteByPaisAndMoneda(Pais pais, Moneda moneda);
    void deleteByPaisIdAndMonedaId(Long paisId, Long monedaId);
     */

    PaisMonedaDTO mapToDTO(PaisMoneda entity);
    PaisMonedaResponseDTO mapToResponseDTO(PaisMoneda entity);
    PaisMoneda mapToEntity(final PaisMonedaDTO dto);
    List<PaisMonedaResponseDTO> findAll() throws Exception;
    PaisMonedaResponseDTO get(Long id) throws Exception;
    PaisMonedaDTO getAll(Long id) throws Exception;
    PaisMonedaResponseDTO getByPaisIdAndEsPrimaria(Long paisId, Boolean esPrimaria) throws Exception;
    PaisMonedaResponseDTO create(PaisMonedaCreateDTO request) throws Exception;
    PaisMonedaResponseDTO update(Long id, PaisMonedaUpdateDTO dto) throws Exception;
    //PaisMonedaResponseDTO registrarPrimerPaisMoneda(Long paisId, Long monedaId) throws Exception;

    PaisMonedaResponseDTO findByPaisIdAndMonedaId(Long paisId, Long monedaId) throws Exception;
}
