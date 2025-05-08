package proyectoDam.PlanetaDigital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proyectoDam.PlanetaDigital.model.Libro;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    // Método para encontrar libros por librocat
    List<Libro> findByLibrocat(int librocat);

    List<Libro> findByCategoria_CategoriaCod(int categoriaCod);

    // Método para encontrar libros destacados (librocat = 10)
    @Query("SELECT l FROM Libro l WHERE l.librocat = 10 ORDER BY l.libroCod DESC")
    List<Libro> findDestacados();

    // Método para encontrar libros recientes (librocat = 20)
    @Query("SELECT l FROM Libro l WHERE l.librocat = 20 ORDER BY l.libroCod DESC")
    List<Libro> findRecientes();
}
