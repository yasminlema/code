package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Autentificacion;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.repository.AutentificacionRepository;
import proyectoDam.PlanetaDigital.repository.UsuarioRepository;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutentificacionRepository autentificacionRepository;

    @GetMapping("/usuarios")
    public String getAll(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/list";
    }

    @PostMapping("/usuarios")
    public String create(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        if (usuarioCod == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findById(usuarioCod).orElse(null);
        Autentificacion autentificacion = autentificacionRepository.findByUsuarioCod(usuarioCod);

        if (usuario == null || autentificacion == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("aut", autentificacion);
        return "perfil";
    }

    @PostMapping("/perfil/editar")
    public String procesarEdicion(Usuario usuarioEditado,
                                  @RequestParam String nuevoUsuario,
                                  HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        if (usuarioCod == null) return "redirect:/login";

        Usuario usuario = usuarioRepository.findById(usuarioCod).orElse(null);
        Autentificacion aut = autentificacionRepository.findByAutUsuario((String) session.getAttribute("usuarioNombre"));

        if (usuario != null) {
            usuario.setUsuNombre(usuarioEditado.getUsuNombre());
            usuario.setUsuApellidos(usuarioEditado.getUsuApellidos());
            usuario.setUsuCorreo(usuarioEditado.getUsuCorreo());
            usuario.setUsuDireccion(usuarioEditado.getUsuDireccion());
            usuario.setUsuTelefono(usuarioEditado.getUsuTelefono());
            usuario.setUsuDni(usuarioEditado.getUsuDni());
            usuarioRepository.save(usuario);
        }

        if (aut != null) {
            aut.setAutUsuario(nuevoUsuario);
            autentificacionRepository.save(aut);
            session.setAttribute("usuarioNombre", nuevoUsuario);
        }

        return "redirect:/perfil";
    }


}