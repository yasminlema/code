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

    @GetMapping("/buscarLibros")
    public String buscarLibros(@RequestParam("buscar") String buscar, Model model) {
        List<Libro> resultados = libroRepository.buscarPorTitulo(buscar);
        model.addAttribute("resultados", resultados);
        model.addAttribute("buscar", buscar);
        return "resultadosBusqueda";
    }

}
