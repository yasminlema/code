package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Categoria;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.repository.CategoriaRepository;
import proyectoDam.PlanetaDigital.repository.LibroRepository;

import java.util.List;

@Controller
public class PaginaPrincipalSesionIniciadaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/paginaPrincipalSesionIniciada")
    public String home(@RequestParam(required = false) Integer categoriaCod, Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

        if (categoriaCod != null) {
            // Buscar libros por categoría
            List<Libro> librosPorCategoria = libroRepository.findByCategoria_CategoriaCod(categoriaCod);
            model.addAttribute("libros", librosPorCategoria);
            model.addAttribute("categoriaNombre", categorias.stream()
                    .filter(c -> c.getCategoriaCod() == categoriaCod)
                    .findFirst().map(Categoria::getCatNombre).orElse(""));
        } else {
            // Obtener libros destacados (librocat = 10)
            List<Libro> destacados = libroRepository.findDestacados();
            System.out.println("Destacados: " + destacados); // Imprime la lista de destacados
            model.addAttribute("destacados", destacados);

            // Obtener libros recientes (librocat = 20)
            List<Libro> recientes = libroRepository.findRecientes();
            System.out.println("Recientes: " + recientes); // Imprime la lista de recientes
            model.addAttribute("recientes", recientes);
        }

        return "paginaPrincipalSesionIniciada";
    }
}

