package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proyectoDam.PlanetaDigital.model.Categoria;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.repository.CategoriaRepository;
import proyectoDam.PlanetaDigital.repository.LibroRepository;

import java.util.List;

@Controller
public class PaginaPrincipalSesionIniciadaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LibroRepository libroRepository;

    //metodo para mostrar la pagina principal de un usuario logeado, con una muestra de libros y las categorias disponibles
    @GetMapping("/paginaPrincipalSesionIniciada")
    public String home(@RequestParam(required = false) Integer categoriaCod, Model model) {
        // busca en la BD y guarda en el model todas las categorias de la base de datos, para mostrarlas en la vista
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

            // busca los libros que tienen la condicion de destacados, los gusrda en el model y los muestra en la vista
            List<Libro> destacados = libroRepository.findDestacados();
            System.out.println("Destacados: " + destacados);
            model.addAttribute("destacados", destacados);


        return "paginaPrincipalSesionIniciada";
    }
}

