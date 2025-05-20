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
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

        List<Libro> librosPorCategoria = libroRepository.findByCategoria_CategoriaCodOrderByLibrotituloAsc(categoriaCod);
        model.addAttribute("libros", librosPorCategoria);

        String categoriaNombre = categorias.stream()
                .filter(c -> c.getCategoriaCod() == categoriaCod)
                .findFirst()
                .map(Categoria::getCatNombre)
                .orElse("Categoría no encontrada");

        model.addAttribute("categoriaNombre", categoriaNombre);

        return "paginaLibrosPorCategoria";
    }
}
