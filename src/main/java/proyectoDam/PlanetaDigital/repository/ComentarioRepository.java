package proyectoDam.PlanetaDigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyectoDam.PlanetaDigital.model.Comentario;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByLibro_LibroCodOrderByComentFechaDesc(int libroCod);
}
