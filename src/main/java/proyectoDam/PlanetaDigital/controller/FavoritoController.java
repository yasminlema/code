package proyectoDam.PlanetaDigital.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proyectoDam.PlanetaDigital.model.Favorito;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.repository.FavoritoRepository;

import org.springframework.transaction.annotation.Transactional;
import proyectoDam.PlanetaDigital.repository.LibroRepository;

import java.util.List;

@Controller
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @PostMapping("/corazonFavorito")
    @Transactional // hace que todo lo que pasa en esta funcion se trate como una sola unidad de trabajo, por lo tanto si en algun momento del proceso falla algo este no guarda/cambia nada en la base de datos
    // funcion que se ejecuta cada vez que pulsamos en el corazon de la pagina
    public String toggleFavorito(@RequestParam Integer usuarioCod,
                                 @RequestParam Integer libroCod) {

        // comprueba si el usuario ya tiene el libro como favorito
        boolean existe = favoritoRepository.existsByUsuarioCodAndLibroCod(usuarioCod, libroCod);

        // si ya lo tiene como favirito lo elimina, sino lo guarda como favorito
        if (existe) {
            favoritoRepository.deleteByUsuarioCodAndLibroCod(usuarioCod, libroCod);
        } else {
            Favorito favorito = new Favorito();
            favorito.setUsuarioCod(usuarioCod);
            favorito.setLibroCod(libroCod);
            favoritoRepository.save(favorito);
        }

        return "redirect:/detalleLibro/" + libroCod;
    }

    // funcion para mostrar todos los libros favoritos en la pagina
    @GetMapping("/misFavoritos")
    public String verFavoritos(HttpSession session, Model model) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod"); // recoge el usuariocod de la sesion
        // si no hay usuariocod, es decir la sesion no esta iniciada redirige el login
        if (usuarioCod == null) {
            return "redirect:/login";
        }

        // obtiene la lista de libros favoritos
        List<Libro> librosFavoritos = favoritoRepository.findLibrosFavoritosByUsuarioCod(usuarioCod);
        model.addAttribute("favoritos", librosFavoritos); // los añade al model para mostrarlos
        return "misFavoritos";
    }
}
