package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Autentificacion;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.repository.AutentificacionRepository;
import proyectoDam.PlanetaDigital.repository.UsuarioRepository;
import proyectoDam.PlanetaDigital.service.CorreoService;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class AutentificacionController {

    @Autowired
    private AutentificacionRepository autentificacionRepository;

    @Autowired
    private CorreoService correoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

        if (autentificacion != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(autPass, autentificacion.getAutPass())) {
                session.setAttribute("tipocuenta", autentificacion.getAuttipocuenta());
                session.setAttribute("usuarioCod", autentificacion.getUsuarioCod());
                session.setAttribute("usuarioNombre", autentificacion.getAutUsuario());

                return "redirect:/paginaPrincipalSesionIniciada";
            }
        }
        model.addAttribute("alertError", "Usuario o contraseña incorrectos");
        return "login";
    }

    @GetMapping("/olvide-contrasena")
    public String mostrarFormularioRecuperacion() {
        return "recuperar_contrasena";
    }

    @PostMapping("/olvide-contrasena")
    public String procesarRecuperacion(@RequestParam String autUsuario, Model model) {
        Autentificacion aut = autentificacionRepository.findByAutUsuario(autUsuario);

        if (aut == null) {
            model.addAttribute("error", "No se encontró el usuario.");
            return "recuperar_contrasena";
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(30);

        aut.setResetToken(token);
        aut.setResetTokenExpiracion(expiracion);
        autentificacionRepository.save(aut);

        Usuario usuario = usuarioRepository.findById(aut.getUsuarioCod()).orElse(null);
        if (usuario != null) {
            correoService.enviarRecuperacion(usuario.getUsuCorreo(), token);
        }

        model.addAttribute("mensaje", "Se ha enviado un correo con instrucciones.");
        return "recuperar_contrasena";
    }

    @GetMapping("/restablecer-contrasena")
    public String mostrarFormularioCambio(@RequestParam String token, Model model) {
        Autentificacion aut = autentificacionRepository.findByResetToken(token);

        if (aut == null || aut.getResetTokenExpiracion().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "El enlace es inválido o ha expirado.");
            return "login";
        }

        model.addAttribute("token", token);
        return "cambiar_contrasena";
    }

    @PostMapping("/restablecer-contrasena")
    public String procesarCambio(@RequestParam String token,
                                 @RequestParam String nuevaContrasena,
                                 Model model) {
        Autentificacion aut = autentificacionRepository.findByResetToken(token);

        if (aut == null || aut.getResetTokenExpiracion().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "login";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        aut.setAutPass(encoder.encode(nuevaContrasena));
        aut.setResetToken(null);
        aut.setResetTokenExpiracion(null);
        autentificacionRepository.save(aut);

        model.addAttribute("alertSuccess", "Contraseña actualizada correctamente.");
        return "login";
    }

}
