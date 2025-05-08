package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Autentificacion;
import proyectoDam.PlanetaDigital.repository.AutentificacionRepository;

@Controller
public class AutentificacionController {

    @Autowired
    private AutentificacionRepository autentificacionRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/controlador/login")
    public String login(@RequestParam String autUsuario,
                        @RequestParam String autPass,
                        Model model,
                        HttpSession session) {

        Autentificacion autentificacion = autentificacionRepository.findByAutUsuario(autUsuario);

        if (autentificacion != null && autentificacion.getAutPass().equals(autPass)) {
            // Guardar datos en la sesión
            session.setAttribute("tipocuenta", autentificacion.getAuttipocuenta());
            session.setAttribute("usuarioCod", autentificacion.getUsuarioCod());
            session.setAttribute("usuarioNombre", autentificacion.getAutUsuario()); // nombre para usar en el header

            return "redirect:/paginaPrincipalSesionIniciada";
        } else {
            model.addAttribute("alertError", "Usuario o contraseña incorrectos");
            return "login";
        }

    }
}
