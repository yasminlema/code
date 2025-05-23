package proyectoDam.PlanetaDigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyectoDam.PlanetaDigital.model.Valoracion;

import java.util.Optional;

public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {
    Optional<Valoracion> findByLibro_LibroCodAndUsuario_UsuarioCod(int libroCod, int usuarioCod);
}
