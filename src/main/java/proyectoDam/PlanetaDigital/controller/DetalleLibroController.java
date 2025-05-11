package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Comentario;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.repository.ComentarioRepository;
import proyectoDam.PlanetaDigital.repository.LibroRepository;

import java.util.List;

@Controller
public class DetalleLibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

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



}
