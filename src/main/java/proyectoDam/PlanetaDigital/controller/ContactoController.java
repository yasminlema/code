package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proyectoDam.PlanetaDigital.dto.FormularioContactoDTO;
import proyectoDam.PlanetaDigital.service.CorreoService;

@RestController
@RequestMapping("/api/formularioContacto")
@CrossOrigin(origins = "*") // para permitir peticiones desde el frontend
public class ContactoController {

    @Autowired
    private CorreoService correoService;

    // metodo para enviar un correo con la informacion del formulario de contacto
    @PostMapping
    public String enviarMensaje(@RequestBody FormularioContactoDTO dto) {
        correoService.enviarContacto(dto); // recibe los datos del formulario por medio del dto
        return "Mensaje enviado correctamente";
    }
}
