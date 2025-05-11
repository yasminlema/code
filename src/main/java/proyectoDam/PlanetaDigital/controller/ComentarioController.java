package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Comentario;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.repository.ComentarioRepository;
import proyectoDam.PlanetaDigital.repository.LibroRepository;

@Controller
public class ComentarioController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @PostMapping("/agregarComentario")
    public String agregarComentario(@RequestParam("comentario") String comentarioTexto,
                                    @RequestParam("libroCod") int libroCod,
                                    HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");

        if (usuarioCod == null) {
            return "redirect:/login"; // o donde manejes la autenticación
        }

        Usuario usuario = new Usuario(); // Solo necesitas el ID si usas EntityManager o save
        usuario.setUsuarioCod(usuarioCod);

        Libro libro = libroRepository.findById(libroCod).orElse(null);
        if (libro == null) {
            return "redirect:/paginaPrincipalSesionIniciada";
        }

        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setUsuario(usuario);
        nuevoComentario.setLibro(libro);
        nuevoComentario.setComentLibro(comentarioTexto);
        nuevoComentario.setComentFecha(java.time.LocalDateTime.now());

        comentarioRepository.save(nuevoComentario);

        return "redirect:/detalleLibro/" + libroCod;
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

    @GetMapping("/controlador/eliminarComentario")
    public String eliminarComentario(@RequestParam("comentarioCod") int comentarioCod, HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        Comentario comentario = comentarioRepository.findById(comentarioCod).orElse(null);

        if (comentario != null && comentario.getUsuario().getUsuarioCod() == usuarioCod) {
            int libroCod = comentario.getLibro().getLibroCod(); // Guarda antes de eliminar
            comentarioRepository.delete(comentario);
            return "redirect:/detalleLibro/" + libroCod;
        }

        return "redirect:/paginaPrincipalSesionIniciada";
    }
}
