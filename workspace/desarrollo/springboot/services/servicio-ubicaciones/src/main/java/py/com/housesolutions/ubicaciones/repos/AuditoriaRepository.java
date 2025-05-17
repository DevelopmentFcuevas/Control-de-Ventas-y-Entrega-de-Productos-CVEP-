package py.com.housesolutions.ubicaciones.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.housesolutions.ubicaciones.domain.Auditoria;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
}
