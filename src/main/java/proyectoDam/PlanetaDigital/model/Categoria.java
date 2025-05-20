package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoriaCod;

    private String catNombre;

    public Integer getCategoriaCod() {return categoriaCod;}

    public void setCategoriaCod(Integer categoriaCod) {this.categoriaCod = categoriaCod;}

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }
}

