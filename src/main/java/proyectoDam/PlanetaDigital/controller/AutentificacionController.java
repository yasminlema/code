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

    //funcion para mostrar el formulario para iniciar sesion
    @GetMapping("/login")
    public String mostrarFormLogin() {
        return "login";
    }

    // metodo para logear al usuario si pone las credenciales correctas o mostrar un error si no son correctas
    // para verificar las contraseñas utilizamos la dependencia de ByCrypt, que tambn se encarga de encriptar las contraseñas cuando se crea un nuevo usuario
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

    // metodo que te lleva a la pagina de recuperacion de contraseña
    @GetMapping("/olvide-contrasena")
    public String mostrarFormularioRecuperacion() {
        return "recuperar_contrasena";
    }

    // metodo para enviar el correo de recuperacion de la contraseña
    @PostMapping("/olvide-contrasena")
    public String procesarRecuperacion(@RequestParam String autUsuario, Model model) {
        Autentificacion aut = autentificacionRepository.findByAutUsuario(autUsuario);

        // verifica si existe el usuario
        if (aut == null) {
            model.addAttribute("error", "No se encontró el usuario");
            return "recuperar_contrasena";
        }

        // genera un token aleatorio para enviarlo por mail y que el usuario pueda crear una contraseña nueva
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(30); //establece la fecha de expiración del token en 30 min

        // guarda estos datos en la BD
        aut.setResetToken(token);
        aut.setResetTokenExpiracion(expiracion);
        autentificacionRepository.save(aut);

        // envia un correo al usuario con el enlace de recuperación. El correo que usa es el que tiene gusrdado en la BD
        Usuario usuario = usuarioRepository.findById(aut.getUsuarioCod()).orElse(null);
        if (usuario != null) {
            correoService.enviarRecuperacion(usuario.getUsuCorreo(), token);
        }

        model.addAttribute("mensaje", "Se ha enviado un correo con instrucciones");
        return "recuperar_contrasena";
    }

    // metodo para acceder a la pagina de cambio de contraseña con el token enviado por correo
    @GetMapping("/restablecer-contrasena")
    public String mostrarFormularioCambio(@RequestParam String token, Model model) {
        Autentificacion aut = autentificacionRepository.findByResetToken(token);

        // verifica que el token sea válido y no esté expirado
        if (aut == null || aut.getResetTokenExpiracion().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "El enlace no es inválido o ha expirado");
            return "login";
        }

        model.addAttribute("token", token);
        return "cambiar_contrasena";
    }

    // metodo para el cambio de la contraseña una vez todo lo anterior haya salido bn y procede a borrar el token
    @PostMapping("/restablecer-contrasena")
    public String procesarCambio(@RequestParam String token,
                                 @RequestParam String nuevaContrasena,
                                 Model model) {
        Autentificacion aut = autentificacionRepository.findByResetToken(token);

        // verifica que el token sea válido y no esté expirado
        if (aut == null || aut.getResetTokenExpiracion().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Token inválido o expirado");
            return "login";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // cifra la nueva contraseña con la dependencia BCrypt
        aut.setAutPass(encoder.encode(nuevaContrasena)); // actualiza la contraseña del usuario
        aut.setResetToken(null);
        aut.setResetTokenExpiracion(null); // borra el token y su expiración
        autentificacionRepository.save(aut); // guarda en la BD la nueva contraseña

        model.addAttribute("alertSuccess", "Contraseña actualizada correctamente");
        return "login";
    }

}
