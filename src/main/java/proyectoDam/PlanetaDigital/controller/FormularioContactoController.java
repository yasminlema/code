package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormularioContactoController {
    // metodo para mostrar el formulario de contacto y gusrda datos de la sesion en el model
    @GetMapping("/formularioContacto")
    public String formularioContacto(HttpSession session, Model model) {
        Integer tipocuenta = (Integer) session.getAttribute("tipocuenta");
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");

        model.addAttribute("tipocuenta", tipocuenta);
        model.addAttribute("usuarioCod", usuarioCod);

        return "formularioContacto";
    }
}
