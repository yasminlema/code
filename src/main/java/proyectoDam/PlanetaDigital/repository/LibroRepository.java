package proyectoDam.PlanetaDigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyectoDam.PlanetaDigital.model.Libro;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    List<Libro> findAllByOrderByLibrotituloAsc();

    List<Libro> findByCategoria_CategoriaCodOrderByLibrotituloAsc(int categoriaCod);

    @Query("SELECT l FROM Libro l WHERE l.librocat = 10 ORDER BY l.libroCod DESC")
    List<Libro> findDestacados();

    @Query("SELECT l FROM Libro l WHERE LOWER(l.librotitulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Libro> buscarPorTitulo(@Param("titulo") String titulo);
}
