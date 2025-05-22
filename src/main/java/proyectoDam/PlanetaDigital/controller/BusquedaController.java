package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.repository.LibroRepository;

import java.util.List;

@Controller
public class BusquedaController {
    @Autowired
    private LibroRepository libroRepository;

    // funcion para mostrar los libros que coincidan con la busqueda del usuario
    @GetMapping("/buscarLibros")
    public String buscarLibros(@RequestParam("buscar") String buscar, Model model) { // recibe la busqueda por URL
        List<Libro> resultados = libroRepository.buscarPorTitulo(buscar); // busca entre los libros de la base de datos las coincidencias
        model.addAttribute("resultados", resultados); // agrega al model los libros encontrados para despues mostrarlos en la pagina 
        model.addAttribute("buscar", buscar); // agrega al model la busqueda que hizo el usuario
        return "resultadosBusqueda";
    }

}
