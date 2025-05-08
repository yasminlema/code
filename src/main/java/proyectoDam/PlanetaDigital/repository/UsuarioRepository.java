package proyectoDam.PlanetaDigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyectoDam.PlanetaDigital.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}