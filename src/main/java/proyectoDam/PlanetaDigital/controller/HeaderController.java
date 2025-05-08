package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import proyectoDam.PlanetaDigital.repository.UsuarioRepository;

@ControllerAdvice
public class HeaderController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @ModelAttribute("usuarioNombre")
    public String populateUsuarioNombre(HttpSession session) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        if (usuarioCod != null) {
            return usuarioRepository.findById(usuarioCod)
                    .map(u -> u.getUsuNombre())
                    .orElse("Usuario conectado");
        }
        return "Usuario conectado";
    }

    @ModelAttribute("tipocuenta")
    public Integer populateTipoCuenta(HttpSession session) {
        return (Integer) session.getAttribute("tipocuenta");
    }
}
