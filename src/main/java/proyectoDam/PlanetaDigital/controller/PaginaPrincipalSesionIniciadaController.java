package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Categoria;
import proyectoDam.PlanetaDigital.model.Comentario;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.repository.CategoriaRepository;
import proyectoDam.PlanetaDigital.repository.ComentarioRepository;
import proyectoDam.PlanetaDigital.repository.LibroRepository;

import java.util.List;

@Controller
public class PaginaPrincipalSesionIniciadaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

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

    @GetMapping("/PagLibrosPorCategoria")
    public String librosPorCategoria(@RequestParam Integer categoriaCod, Model model) {
        // Obtener la lista de categorías
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

        // Obtener los libros de la categoría seleccionada
        List<Libro> librosPorCategoria = libroRepository.findByCategoria_CategoriaCod(categoriaCod);
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

    @GetMapping("/detalleLibro/{id}")
    public String detalleLibro(@PathVariable("id") int id, Model model, HttpSession session) {
        Libro libro = libroRepository.findById(id).orElse(null);
        if (libro == null) {
            return "redirect:/paginaPrincipalSesionIniciada";
        }

        model.addAttribute("libro", libro);
        model.addAttribute("libroCod", libro.getLibroCod());

        List<Comentario> comentarios = comentarioRepository.findByLibro_LibroCodOrderByComentFechaDesc(id);
        model.addAttribute("comentarios", comentarios);

        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        model.addAttribute("usuarioCod", usuarioCod);

        return "detalleLibro";
    }

    @PostMapping("/editarComentario")
    public String editarComentario(@RequestParam("comentarioCod") int comentarioCod,
                                   @RequestParam("nuevoComentario") String nuevoComentario,
                                   HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        Comentario comentario = comentarioRepository.findById(comentarioCod).orElse(null);

        // Verifica que el comentario existe y que el usuario actual es el propietario del comentario
        if (comentario != null && comentario.getUsuario().getUsuarioCod() == usuarioCod) {
            comentario.setComentLibro(nuevoComentario);
            comentarioRepository.save(comentario);
        }
        return "redirect:/detalleLibro/" + comentario.getLibro().getLibroCod();
    }


}

