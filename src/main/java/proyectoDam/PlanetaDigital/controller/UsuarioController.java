package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import proyectoDam.PlanetaDigital.model.Usuario;
import proyectoDam.PlanetaDigital.repository.UsuarioRepository;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public String getAll(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/list"; // Cambia esto por la vista que desees
    }

    @PostMapping("/usuarios")
    public String create(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "redirect:/usuarios"; // Redirige a la lista después de guardar
    }
}