// Transporta los datos del formulario de registro
package proyectoDam.PlanetaDigital.dto;

public class RegistroDTO {
    private String usuNombre;
    private String usuApellidos;
    private String usuDni;
    private String usuCorreo;
    private Integer usuTelefono;
    private String usuDireccion;

    private String autUsuario;
    private String autPass;
    private int auttipocuenta;

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

    public int getAuttipocuenta() {
        return auttipocuenta;
    }

    public void setAuttipocuenta(int auttipocuenta) {
        this.auttipocuenta = auttipocuenta;
    }
}
