package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/salir")
    public String logout(HttpSession session) {
        session.invalidate(); // se encarga de eliminar la sesion actual del usuario junto a las variables que se gusrdan en la sesion
        return "redirect:/login";
    }
}
