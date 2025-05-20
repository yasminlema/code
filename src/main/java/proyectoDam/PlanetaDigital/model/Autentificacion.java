package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "autentificacion")
public class Autentificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autentificacionCod")
    private int autentificacionCod;

    @Column(name = "auttipocuenta")
    private int auttipocuenta;

    @Column(name = "usuarioCod")
    private int usuarioCod;

    @Column(name = "autUsuario")
    private String autUsuario;

    @Column(name = "autPass")
    private String autPass;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiracion")
    private LocalDateTime resetTokenExpiracion;

    public String getResetToken() {return resetToken;}

    public void setResetToken(String resetToken) {this.resetToken = resetToken;}

    public LocalDateTime getResetTokenExpiracion() {return resetTokenExpiracion;}

    public void setResetTokenExpiracion(LocalDateTime resetTokenExpiracion) {this.resetTokenExpiracion = resetTokenExpiracion;}

    public int getAutentificacionCod() {
        return autentificacionCod;
    }

    public void setAutentificacionCod(int autentificacionCod) {
        this.autentificacionCod = autentificacionCod;
    }

    public int getAuttipocuenta() {
        return auttipocuenta;
    }

    public void setAuttipocuenta(int auttipocuenta) {
        this.auttipocuenta = auttipocuenta;
    }

    public int getUsuarioCod() {
        return usuarioCod;
    }

    public void setUsuarioCod(int usuarioCod) {
        this.usuarioCod = usuarioCod;
    }

    public String getAutUsuario() {
        return autUsuario;
    }

    public void setAutUsuario(String autUsuario) {
        this.autUsuario = autUsuario;
    }

    public String getAutPass() {
        return autPass;
    }

    public void setAutPass(String autPass) {
        this.autPass = autPass;
    }
}