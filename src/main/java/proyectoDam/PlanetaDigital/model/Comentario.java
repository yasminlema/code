package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comentarioCod;

    @ManyToOne
    @JoinColumn(name = "usuarioCod")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "libroCod")
    private Libro libro;

    @Column(name = "comentLibro", columnDefinition = "TEXT")
    private String comentLibro;

    @Column(name = "comentFecha")
    private LocalDateTime comentFecha;

    // Getters y Setters
    public int getComentarioCod() {
        return comentarioCod;
    }

    public void setComentarioCod(int comentarioCod) {
        this.comentarioCod = comentarioCod;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getComentLibro() {
        return comentLibro;
    }

    public void setComentLibro(String comentLibro) {
        this.comentLibro = comentLibro;
    }

    public LocalDateTime getComentFecha() {
        return comentFecha;
    }

    public void setComentFecha(LocalDateTime comentFecha) {
        this.comentFecha = comentFecha;
    }
}
