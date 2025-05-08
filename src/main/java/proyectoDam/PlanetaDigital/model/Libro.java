package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int libroCod;

    private String libroimagen;
    private String librotitulo;
    private String libroautor;
    private String libroDescripcion;

    @Column(name = "librocat") // Añadimos la anotación para el campo librocat
    private Integer librocat;

    @ManyToOne
    @JoinColumn(name = "categoriaCod")
    private Categoria categoria;

    public int getLibroCod() {
        return libroCod;
    }

    public void setLibroCod(int libroCod) {
        this.libroCod = libroCod;
    }

    public String getLibroimagen() {
        return libroimagen;
    }

    public void setLibroimagen(String libroimagen) {
        this.libroimagen = libroimagen;
    }

    public String getLibrotitulo() {
        return librotitulo;
    }

    public void setLibrotitulo(String librotitulo) {
        this.librotitulo = librotitulo;
    }

    public String getLibroautor() {
        return libroautor;
    }

    public void setLibroautor(String libroautor) {
        this.libroautor = libroautor;
    }

    public String getLibroDescripcion() {
        return libroDescripcion;
    }

    public void setLibroDescripcion(String libroDescripcion) {
        this.libroDescripcion = libroDescripcion;
    }

    public int getLibrocat() {
        return librocat;
    }

    public void setLibrocat(int librocat) {
        this.librocat = librocat;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
