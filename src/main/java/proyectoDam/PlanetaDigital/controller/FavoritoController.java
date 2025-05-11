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
    private LibroRepository libroRepository; // <- Añade esta línea

    @PostMapping("/corazonFavorito")
    @Transactional
    public String toggleFavorito(@RequestParam Integer usuarioCod,
                                 @RequestParam Integer libroCod) {

        boolean existe = favoritoRepository.existsByUsuarioCodAndLibroCod(usuarioCod, libroCod);

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

    @GetMapping("/misFavoritos")
    public String verFavoritos(HttpSession session, Model model) {
        Integer usuarioCod = (Integer) session.getAttribute("usuarioCod");
        if (usuarioCod == null) {
            return "redirect:/login"; // Redirige si no está logueado
        }

        List<Libro> librosFavoritos = favoritoRepository.findLibrosFavoritosByUsuarioCod(usuarioCod);
        model.addAttribute("favoritos", librosFavoritos);
        return "misFavoritos";
    }
}
