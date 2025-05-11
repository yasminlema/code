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

    @PostMapping("/valorarLibro")
    public String valorarLibro(@RequestParam int libroCod, HttpSession session, @RequestParam int valoracion) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        if (usuarioCod == null) {
            return "redirect:/login";  // Si no hay sesión activa, redirigir al login
        }

        Libro libro = libroRepository.findById(libroCod).orElse(null);
        Usuario usuario = usuarioRepository.findById(usuarioCod).orElse(null);
        if (libro == null || usuario == null || valoracion < 1 || valoracion > 5) {
            return "redirect:/detalleLibro/" + libroCod + "?error";
        }

        Optional<Valoracion> existente = valoracionRepo.findByLibro_LibroCodAndUsuario_UsuarioCod(libroCod, usuarioCod);
        Valoracion valoracionObj = existente.orElse(new Valoracion());
        valoracionObj.setLibro(libro);
        valoracionObj.setUsuario(usuario);
        valoracionObj.setValoracion(valoracion);

        valoracionRepo.save(valoracionObj);
        return "redirect:/detalleLibro/" + libroCod;
    }

}
