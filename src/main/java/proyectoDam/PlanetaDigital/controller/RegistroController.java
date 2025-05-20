package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import proyectoDam.PlanetaDigital.dto.RegistroDTO;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.model.Autentificacion;
import proyectoDam.PlanetaDigital.repository.UsuarioRepository;
import proyectoDam.PlanetaDigital.repository.AutentificacionRepository;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutentificacionRepository autentificacionRepository;

    @GetMapping("/registrarse")
    public String mostrarFormularioRegistro() {
        return "registro"; // asegúrate de que el archivo se llame registro.html y esté en templates
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute RegistroDTO registro) {
        Usuario usuario = new Usuario();
        usuario.setUsuNombre(registro.getUsuNombre());
        usuario.setUsuApellidos(registro.getUsuApellidos());
        usuario.setUsuDni(registro.getUsuDni());
        usuario.setUsuCorreo(registro.getUsuCorreo());
        usuario.setUsuTelefono(registro.getUsuTelefono());
        usuario.setUsuDireccion(registro.getUsuDireccion());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(registro.getAutPass());

        Autentificacion aut = new Autentificacion();
        aut.setAutUsuario(registro.getAutUsuario());
        aut.setAutPass(hashedPassword);
        aut.setAuttipocuenta(20);
        aut.setUsuarioCod(usuarioGuardado.getUsuarioCod());

        autentificacionRepository.save(aut);

        return "redirect:/login";
    }

}
