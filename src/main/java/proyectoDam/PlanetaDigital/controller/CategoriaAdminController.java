package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proyectoDam.PlanetaDigital.model.Categoria;
import proyectoDam.PlanetaDigital.repository.CategoriaRepository;

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaAdminController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categorias";
    }

    @PostMapping("/agregar")
    public String agregarCategoria(@RequestParam String catNombre) {
        Categoria nueva = new Categoria();
        nueva.setCatNombre(catNombre);
        categoriaRepository.save(nueva);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable("id") Integer id) {
        categoriaRepository.deleteById(id);
        return "redirect:/admin/categorias";
    }
}
