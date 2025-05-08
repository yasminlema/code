package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuarioCod;

    private String usuNombre;
    private String usuApellidos;
    private String usuDni;
    private String usuCorreo;
    private Integer usuTelefono;
    private String usuDireccion;

    // Getters y Setters
    public int getUsuarioCod() {
        return usuarioCod;
    }

    public void setUsuarioCod(int usuarioCod) {
        this.usuarioCod = usuarioCod;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuApellidos() {
        return usuApellidos;
    }

    public void setUsuApellidos(String usuApellidos) {
        this.usuApellidos = usuApellidos;
    }

    public String getUsuDni() {
        return usuDni;
    }

    public void setUsuDni(String usuDni) {
        this.usuDni = usuDni;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public Integer getUsuTelefono() {
        return usuTelefono;
    }

    public void setUsuTelefono(Integer usuTelefono) {
        this.usuTelefono = usuTelefono;
    }

    public String getUsuDireccion() {
        return usuDireccion;
    }

    public void setUsuDireccion(String usuDireccion) {
        this.usuDireccion = usuDireccion;
    }
}