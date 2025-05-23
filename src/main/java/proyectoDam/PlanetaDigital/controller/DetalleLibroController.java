package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import proyectoDam.PlanetaDigital.model.Comentario;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.repository.ComentarioRepository;
import proyectoDam.PlanetaDigital.repository.FavoritoRepository;
import proyectoDam.PlanetaDigital.repository.LibroRepository;
import proyectoDam.PlanetaDigital.repository.ValoracionRepository;

import java.util.List;

@Controller
public class DetalleLibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    // metodo para mostrar el libro y su informacion
    @GetMapping("/detalleLibro/{id}")
    public String detalleLibro(@PathVariable("id") int id, Model model, HttpSession session) {
        // busca el libro con ese id pasado por la URL, si no existe te redirige a la pagina principal
        Libro libro = libroRepository.findById(id).orElse(null);
        if (libro == null) {
            return "redirect:/paginaPrincipalSesionIniciada";
        }

        // añade el libro al model para despues mostrar los datos de ese libro
        model.addAttribute("libro", libro);
        model.addAttribute("libroCod", libro.getLibroCod());

        // obtiene los comentarios del libro (del más reciente al más antiguo) y los envía al modelo
        List<Comentario> comentarios = comentarioRepository.findByLibro_LibroCodOrderByComentFechaDesc(id);
        model.addAttribute("comentarios", comentarios);

        // obtiene el usuariocod de la sesion y lo guarda en el model
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        model.addAttribute("usuarioCod", usuarioCod);

        
        Double promedio = valoracionRepository.obtenerPromedioValoracion(id);
        if (promedio == null) promedio = 0.0;

        // comprueba si el usuario tiene ese libro marcado como favorito
        boolean esFavorito = false;
        if (usuarioCod != null) {
            esFavorito = favoritoRepository.existsByUsuarioCodAndLibroCod(usuarioCod, id);
        }
        model.addAttribute("esFavorito", esFavorito);

        // si ya valoro el usuario el libro le muestra su valoracion
        if (usuarioCod != null) {
            valoracionRepository.findByLibro_LibroCodAndUsuario_UsuarioCod(id, usuarioCod)
                    .ifPresent(valoracion -> model.addAttribute("valoracionUsuario", valoracion.getValoracion()));
        } else {
            model.addAttribute("valoracionUsuario", 0);
        }

        return "detalleLibro";
    }
}
