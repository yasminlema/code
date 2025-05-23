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

    // metodo para enviar el formulario de contacto por correo al responsable
    public void enviarContacto(FormularioContactoDTO dto) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo("planetadigital2207@gmail.com");
        mensaje.setSubject("Nuevo mensaje de contacto: " + dto.getAsunto());
        mensaje.setText(
                "Nombre: " + dto.getNombre() + "\n" +
                "Correo: " + dto.getCorreo() + "\n" +
                "Mensaje:\n" + dto.getMensaje()
        );
        mailSender.send(mensaje);
    }

    // metodo para enviar por correo al usuario el enlace de recuperacion de la contraseña con su respectivo token
    public void enviarRecuperacion(String destinoCorreo, String token) {
        String url = "http://localhost:8080/restablecer-contrasena?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinoCorreo);
        mensaje.setSubject("Recuperación de contraseña - Planeta Digital");
        mensaje.setText("Hola, has solicitado restablecer tu contraseña.\n\n" +
                "Haz clic en el siguiente enlace para establecer una nueva:\n" +
                url + "\n\n" +
                "Este enlace expirará en 30 minutos.\n\n" +
                "Si no solicitaste esto, ignora este mensaje.");

        mailSender.send(mensaje);
    }

}
