package proyectoDam.PlanetaDigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import proyectoDam.PlanetaDigital.dto.FormularioContactoDTO;

@Service
public class CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public void enviarContacto(FormularioContactoDTO dto) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo("planetadigital2207@gmail.com"); // <-- cambia esto a tu correo real
        mensaje.setSubject("Nuevo mensaje de contacto: " + dto.getAsunto());
        mensaje.setText(
                "Nombre: " + dto.getNombre() + "\n" +
                "Correo: " + dto.getCorreo() + "\n" +
                "Mensaje:\n" + dto.getMensaje()
        );
        mailSender.send(mensaje);
    }
}
