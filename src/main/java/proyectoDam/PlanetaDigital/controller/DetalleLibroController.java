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

        Double promedio = valoracionRepository.obtenerPromedioValoracion(id);
        if (promedio == null) promedio = 0.0;

        // Comprobar si el libro es favorito
        boolean esFavorito = false;
        if (usuarioCod != null) {
            esFavorito = favoritoRepository.existsByUsuarioCodAndLibroCod(usuarioCod, id);
        }
        model.addAttribute("esFavorito", esFavorito);

        if (usuarioCod != null) {
            valoracionRepository.findByLibro_LibroCodAndUsuario_UsuarioCod(id, usuarioCod)
                    .ifPresent(valoracion -> model.addAttribute("valoracionUsuario", valoracion.getValoracion()));
        } else {
            model.addAttribute("valoracionUsuario", 0); // No ha valorado
        }

        return "detalleLibro";
    }
}
