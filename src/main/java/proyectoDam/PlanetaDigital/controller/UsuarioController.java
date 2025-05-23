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

    // metodo que muestra la informacion del usuario logeado
    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");// recoge el usuariocod de la sesion
        // verifica que la sesion este iniciada, sino redirige al login
        if (usuarioCod == null) {
            return "redirect:/login";
        }

        //busca el usuario y la autentificacion por el usuariocod
        Usuario usuario = usuarioRepository.findById(usuarioCod).orElse(null);
        Autentificacion autentificacion = autentificacionRepository.findByUsuarioCod(usuarioCod);

        // si no encuentra el usuario o la autentificacion redirige al login
        if (usuario == null || autentificacion == null) {
            return "redirect:/login";
        }

        // gusrda el usuario y la autentificacion en el model para despues mostrarlo en la vista
        model.addAttribute("usuario", usuario);
        model.addAttribute("aut", autentificacion);
        return "perfil";
    }

    // metodo para editar cualquier campo del perfil del usuario y que se actualice en la base de datos
    @PostMapping("/perfil/editar")
    public String procesarEdicion(Usuario usuarioEditado,
                                  @RequestParam String nuevoUsuario,
                                  HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod"); // recoge el usuariocod de la sesion
        if (usuarioCod == null) return "redirect:/login"; // si no hay usuariocod redirige al login

        Usuario usuario = usuarioRepository.findById(usuarioCod).orElse(null); // busca el usuario por el usuariocod en la BD
        Autentificacion aut = autentificacionRepository.findByAutUsuario((String) session.getAttribute("usuarioNombre")); // recoge de la sesion el usuarioNombre y busca en la BD un usuario con ese autusuario

        // si el existe actualiza en la BD los nuevos datos
        if (usuario != null) {
            usuario.setUsuNombre(usuarioEditado.getUsuNombre());
            usuario.setUsuApellidos(usuarioEditado.getUsuApellidos());
            usuario.setUsuCorreo(usuarioEditado.getUsuCorreo());
            usuario.setUsuDireccion(usuarioEditado.getUsuDireccion());
            usuario.setUsuTelefono(usuarioEditado.getUsuTelefono());
            usuario.setUsuDni(usuarioEditado.getUsuDni());
            usuarioRepository.save(usuario);
        }

        // y lo mismo con la autentificacion
        if (aut != null) {
            aut.setAutUsuario(nuevoUsuario);
            autentificacionRepository.save(aut);
            session.setAttribute("usuarioNombre", nuevoUsuario);
        }

        return "redirect:/perfil";
    }
}
