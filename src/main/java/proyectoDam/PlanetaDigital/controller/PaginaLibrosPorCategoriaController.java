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
public class PaginaLibrosPorCategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/PagLibrosPorCategoria")
    public String librosPorCategoria(@RequestParam Integer categoriaCod, Model model) {
        // Obtener la lista de categorías
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

        // Obtener los libros de la categoría seleccionada
        List<Libro> librosPorCategoria = libroRepository.findByCategoria_CategoriaCodOrderByLibrotituloAsc(categoriaCod);
        model.addAttribute("libros", librosPorCategoria);

        // Obtener el nombre de la categoría para mostrar en la vista
        String categoriaNombre = categorias.stream()
                .filter(c -> c.getCategoriaCod() == categoriaCod)
                .findFirst()
                .map(Categoria::getCatNombre)
                .orElse("Categoría no encontrada");

        model.addAttribute("categoriaNombre", categoriaNombre);

        return "paginaLibrosPorCategoria";
    }
}
