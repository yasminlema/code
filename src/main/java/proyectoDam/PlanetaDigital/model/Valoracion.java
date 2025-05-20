package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros_valoraciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"libroCod", "usuarioCod"})
})
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer valoracionCod;

    private Integer valoracion;

    @ManyToOne
    @JoinColumn(name = "libroCod")
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "usuarioCod")
    private Usuario usuario;

    public Integer getValoracionCod() {
        return valoracionCod;
    }

    public void setValoracionCod(Integer valoracionCod) {
        this.valoracionCod = valoracionCod;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
