package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoriaCod;

    private String catNombre;

    public int getCategoriaCod() {
        return categoriaCod;
    }

    public void setCategoriaCod(int categoriaCod) {
        this.categoriaCod = categoriaCod;
    }

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }
}

