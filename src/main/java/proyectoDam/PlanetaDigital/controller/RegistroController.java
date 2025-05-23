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

    // muestra el formulario de registro
    @GetMapping("/registrarse")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    //metodo para gusrdar los datos del formulario de registro en la BD
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute RegistroDTO registro) {
        // se crea un objeto usuario, se llenan los campos con los datos recogidos por el dto
        Usuario usuario = new Usuario();
        usuario.setUsuNombre(registro.getUsuNombre());
        usuario.setUsuApellidos(registro.getUsuApellidos());
        usuario.setUsuDni(registro.getUsuDni());
        usuario.setUsuCorreo(registro.getUsuCorreo());
        usuario.setUsuTelefono(registro.getUsuTelefono());
        usuario.setUsuDireccion(registro.getUsuDireccion());

        // y se gusrdan en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // se cifra la contraseña escoguda por el usuario
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(registro.getAutPass());

        // se crea un objeto autentificacion y se llenan los campos con los datos recogidos por el dto
        Autentificacion aut = new Autentificacion();
        aut.setAutUsuario(registro.getAutUsuario());
        aut.setAutPass(hashedPassword);
        aut.setAuttipocuenta(20); // establecemos el tipocuenta por defecto en 20
        aut.setUsuarioCod(usuarioGuardado.getUsuarioCod()); // le asociamos a esa autentificacion el usuariocod del usuario creado

        autentificacionRepository.save(aut); // se guardan los datos en la base de datos

        return "redirect:/login";
    }

}
