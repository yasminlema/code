package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooterController {

    @GetMapping("/footer")
    public String footer(HttpSession session, Model model) {
        return "footer";
    }
}