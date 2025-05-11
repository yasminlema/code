package proyectoDam.PlanetaDigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyectoDam.PlanetaDigital.model.Favorito;
import proyectoDam.PlanetaDigital.model.Libro;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {

    boolean existsByUsuarioCodAndLibroCod(Integer usuarioCod, Integer libroCod);

    Optional<Favorito> findByUsuarioCodAndLibroCod(Integer usuarioCod, Integer libroCod);

    void deleteByUsuarioCodAndLibroCod(Integer usuarioCod, Integer libroCod);

    @Query("SELECT l FROM Libro l WHERE l.libroCod IN (SELECT f.libroCod FROM Favorito f WHERE f.usuarioCod = :usuarioCod)")
    List<Libro> findLibrosFavoritosByUsuarioCod(@Param("usuarioCod") Integer usuarioCod);

}
