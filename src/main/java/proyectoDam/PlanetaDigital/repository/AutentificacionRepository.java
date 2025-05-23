package proyectoDam.PlanetaDigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyectoDam.PlanetaDigital.model.Autentificacion;

public interface AutentificacionRepository extends JpaRepository<Autentificacion, Integer> {
    Autentificacion findByAutUsuario(String autUsuario);
    Autentificacion findByUsuarioCod(int usuarioCod);
    Autentificacion findByResetToken(String token);

}