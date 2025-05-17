package py.com.housesolutions.ubicaciones;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import py.com.housesolutions.ubicaciones.repos.PaisRepository;
import py.com.housesolutions.ubicaciones.service.implementation.PaisServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class PaisServiceImplTest {
    @InjectMocks
    private PaisServiceImpl service;
    @Mock
    private PaisRepository repository;
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this); // Inicializa los mocks
    }

    /*@Test
    public void testCreatePais() throws Exception {
        PaisDTO dto = new PaisDTO();
        dto.setName("Argentina");
        Pais savedPais = new Pais();
        savedPais.setId(3L);

        when(repository.save(any(Pais.class))).thenReturn(savedPais);

        PaisDTO result = service.create(dto);

        assertNotNull(result); // Verifica que el resultado no sea nulo
        assertEquals(result.getId(), 1L); // Compara el ID esperado
        verify(repository, times(1)).save(any(Pais.class)); // Verifica que se haya llamado a 'save'
    }*/

}
