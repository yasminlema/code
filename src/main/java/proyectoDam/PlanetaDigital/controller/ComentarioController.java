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

    // metodo para agregar un comentario
    @PostMapping("/agregarComentario")
    public String agregarComentario(@RequestParam("comentario") String comentarioTexto,
                                    @RequestParam("libroCod") int libroCod,
                                    HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod"); // recupera el usuariocod desde la sesion

        // si no encuentra la sesion te redirige al login
        if (usuarioCod == null) {
            return "redirect:/login";
        }

        // crea un nuevo objeto usuario y le asigna el usuariocod sacado de la sesion
        Usuario usuario = new Usuario();
        usuario.setUsuarioCod(usuarioCod);

        Libro libro = libroRepository.findById(libroCod).orElse(null);
        if (libro == null) {
            return "redirect:/paginaPrincipalSesionIniciada";
        }

        // crea un nuevo objeto Comentario y le asigna el usuario, el libro, el texto y la fecha
        Comentario nuevoComentario = new Comentario();
        nuevoComentario.setUsuario(usuario);
        nuevoComentario.setLibro(libro);
        nuevoComentario.setComentLibro(comentarioTexto);
        nuevoComentario.setComentFecha(java.time.LocalDateTime.now());

        comentarioRepository.save(nuevoComentario); // guarda el comentario en la base de datos

        return "redirect:/detalleLibro/" + libroCod;
    }

    // metodo para editar un comentario
    @PostMapping("/editarComentario")
    public String editarComentario(@RequestParam("comentarioCod") int comentarioCod,
                                   @RequestParam("nuevoComentario") String nuevoComentario,
                                   HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod"); // recupera el usuariocod desde la sesion
        Comentario comentario = comentarioRepository.findById(comentarioCod).orElse(null); // recupera el comentario por el comentariocod

        // verifica que el comentario pertenezca al usuario que tiene la sesion iniciada
        if (comentario != null && comentario.getUsuario().getUsuarioCod() == usuarioCod) {
            comentario.setComentLibro(nuevoComentario); // actualiza el texto del comentario
            comentarioRepository.save(comentario); // y gusrda los cambios en la BD
        }
        return "redirect:/detalleLibro/" + comentario.getLibro().getLibroCod();
    }

    // metodo para eliminar el comentario
    @GetMapping("/controlador/eliminarComentario")
    public String eliminarComentario(@RequestParam("comentarioCod") int comentarioCod, HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod"); // recupera el usuariocod desde la sesion
        Comentario comentario = comentarioRepository.findById(comentarioCod).orElse(null); // recupera el comentario por el comentariocod

        // verifica que el comentario pertenezca al usuario que tiene la sesion iniciada
        if (comentario != null && comentario.getUsuario().getUsuarioCod() == usuarioCod) {
            int libroCod = comentario.getLibro().getLibroCod();
            comentarioRepository.delete(comentario); // elimina el comentario de la base de datos
            return "redirect:/detalleLibro/" + libroCod;
        }

        // en el caso de que el usuario no coincida con el usuario que tiene la sesion iniciada , redirige a la pagina de login
        return "redirect:/paginaPrincipalSesionIniciada"; 
    }
}
