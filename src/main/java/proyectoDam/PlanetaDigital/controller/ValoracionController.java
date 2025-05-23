package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.model.Valoracion;
import proyectoDam.PlanetaDigital.repository.LibroRepository;
import proyectoDam.PlanetaDigital.repository.UsuarioRepository;
import proyectoDam.PlanetaDigital.repository.ValoracionRepository;

import java.util.Optional;

@Controller
public class ValoracionController {

    @Autowired
    private ValoracionRepository valoracionRepo;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // metodo que controla la valoracion de los libros
    @PostMapping("/valorarLibro")
    public String valorarLibro(@RequestParam int libroCod, HttpSession session, @RequestParam int valoracion) {
        // recoge el usuariocod de la sesion
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        // si no encuentra el usuariocod, redirige al login
        if (usuarioCod == null) {
            return "redirect:/login";
        }

        // busca el libro y usuario en la base de datos
        Libro libro = libroRepository.findById(libroCod).orElse(null);
        Usuario usuario = usuarioRepository.findById(usuarioCod).orElse(null);
        // si alguno no existe o la valoracion se sale del rango redirige con error
        if (libro == null || usuario == null || valoracion < 1 || valoracion > 5) {
            return "redirect:/detalleLibro/" + libroCod + "?error";
        }

        // si la valoracion existe se actualiza la valoracion
        // si no existe se crea una nueva y se gusrda en la BD
        Optional<Valoracion> existente = valoracionRepo.findByLibro_LibroCodAndUsuario_UsuarioCod(libroCod, usuarioCod);
        Valoracion valoracionObj = existente.orElse(new Valoracion());
        valoracionObj.setLibro(libro);
        valoracionObj.setUsuario(usuario);
        valoracionObj.setValoracion(valoracion);

        valoracionRepo.save(valoracionObj);
        return "redirect:/detalleLibro/" + libroCod;
    }

}
