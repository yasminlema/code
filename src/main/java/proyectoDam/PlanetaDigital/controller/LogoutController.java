package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @GetMapping("/salir")
    public String logout(HttpSession session) {
        // Invalidar la sesión para destruir todos los atributos
        session.invalidate();
        return "redirect:/login"; // Redirigir al login o página de inicio
    }
}
