package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SobreMiController {

    @GetMapping("/sobreMi")
    public String sobreMi(HttpSession session, Model model) {
        Integer tipocuenta = (Integer) session.getAttribute("tipocuenta");
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");

        model.addAttribute("tipocuenta", tipocuenta);
        model.addAttribute("usuarioCod", usuarioCod);

        return "sobreMi";
    }
}
