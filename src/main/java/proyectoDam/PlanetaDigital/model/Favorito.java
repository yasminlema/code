package proyectoDam.PlanetaDigital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros_favoritos")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoritoCod;

    private Integer usuarioCod;

    private Integer libroCod;

    public Integer getFavoritoCod() {
        return favoritoCod;
    }

    public void setFavoritoCod(Integer favoritoCod) {
        this.favoritoCod = favoritoCod;
    }

    public Integer getUsuarioCod() {
        return usuarioCod;
    }

    public void setUsuarioCod(Integer usuarioCod) {
        this.usuarioCod = usuarioCod;
    }

    public Integer getLibroCod() {
        return libroCod;
    }

    public void setLibroCod(Integer libroCod) {
        this.libroCod = libroCod;
    }
}
