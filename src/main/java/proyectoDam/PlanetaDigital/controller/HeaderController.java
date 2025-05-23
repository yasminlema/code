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
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod"); // saca el usuariocod de la sesion
        // si en la sesion encuentra un usuariocod
        if (usuarioCod != null) {
            return usuarioRepository.findById(usuarioCod) // busca ese usuariocod en la BD
                    .map(u -> u.getUsuNombre()) // si lo encuentra devuelve su nombre
                    .orElse("Usuario conectado"); // si no lo encuentra devuelve usuario conectado
        }
        return "Usuario conectado";
    }

    // este metodo recupera el tipocuenta de la sesion y despues lo usamos en la vista para mostrar u ocultar cosas segun el tipo de cuenta del usuario
    @ModelAttribute("tipocuenta")
    public Integer populateTipoCuenta(HttpSession session) {
        return (Integer) session.getAttribute("tipocuenta");
    }
}
