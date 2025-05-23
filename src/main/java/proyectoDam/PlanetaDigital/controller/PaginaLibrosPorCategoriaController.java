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

    // metodo para mostrar en la pagina todos los libros de una categoria en concreto que elija el usuario
    @GetMapping("/PagLibrosPorCategoria")
    public String librosPorCategoria(@RequestParam Integer categoriaCod, Model model) {
        List<Categoria> categorias = categoriaRepository.findAll(); // carga todas las categorias de la BD
        model.addAttribute("categorias", categorias); // y las guarda en el model para despues mostrarlas en la vista

        List<Libro> librosPorCategoria = libroRepository.findByCategoria_CategoriaCodOrderByLibrotituloAsc(categoriaCod); // busca todos los libros de la categoria elegida por el usuario
        model.addAttribute("libros", librosPorCategoria); // y los guarda en el model

        // busca el nombre de la categoria por el codigo de la categoria, si la encuentra la gusrda en el model para despues mostrarla
        // sino devuelve un mensaje
        String categoriaNombre = categorias.stream()
                .filter(c -> c.getCategoriaCod() == categoriaCod)
                .findFirst()
                .map(Categoria::getCatNombre)
                .orElse("Categoría no encontrada");

        model.addAttribute("categoriaNombre", categoriaNombre);

        return "paginaLibrosPorCategoria";
    }

    //metodo para listar todos los libros de la base de datos
    @GetMapping("/CatalogoLibros")
    public String todosLosLibros(Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

        List<Libro> todosLosLibros = libroRepository.findAll();
        model.addAttribute("libros", todosLosLibros);

        model.addAttribute("categoriaNombre", "Todos los libros");

        return "paginaLibrosPorCategoria";
    }
}
