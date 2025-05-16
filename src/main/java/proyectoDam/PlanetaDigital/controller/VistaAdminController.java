package proyectoDam.PlanetaDigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proyectoDam.PlanetaDigital.model.Categoria;
import proyectoDam.PlanetaDigital.model.Libro;
import proyectoDam.PlanetaDigital.repository.CategoriaRepository;
import proyectoDam.PlanetaDigital.repository.LibroRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class VistaAdminController {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/admin")
    public String mostrarLibros(@RequestParam(name = "categoriaFiltro", required = false) Integer categoriaCod, Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<Libro> libros = (categoriaCod != null)
                ? libroRepository.findByCategoria_CategoriaCodOrderByLibrotituloAsc(categoriaCod)
                : libroRepository.findAllByOrderByLibrotituloAsc();

        model.addAttribute("categorias", categorias);
        model.addAttribute("libros", libros);
        model.addAttribute("categoriaFiltro", categoriaCod);

        return "admin";
    }

    @PostMapping("/actualizarLibro")
    public String actualizarLibro(
            @ModelAttribute Libro libro,
            @RequestParam(value = "nuevaImagen", required = false) MultipartFile nuevaImagen,
            @RequestParam(value = "nuevoPdf", required = false) MultipartFile nuevoPdf
    ) {
        try {
            String rutaBase = System.getProperty("user.dir") + "/subidas/";
            Libro libroExistente = libroRepository.findById(libro.getLibroCod()).orElse(null);

            if (libroExistente == null) {
                return "redirect:/admin?errorLibroNoEncontrado";
            }

            String nombreBase = libro.getLibrotitulo().trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9_]", "");

            // === IMAGEN ===
            String imagenNombre = nombreBase + ".jpg";
            File imagenDestino = new File(rutaBase + "imagenes/libros/", imagenNombre);
            if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
                // Borrar archivo anterior incluso si el nombre coincide
                if (imagenDestino.exists()) imagenDestino.delete();
                nuevaImagen.transferTo(imagenDestino);
                libro.setLibroimagen(imagenNombre);
            } else {
                libro.setLibroimagen(libro.getLibroimagen());
            }

            // === PDF ===
            String pdfNombre = nombreBase + ".pdf";
            File pdfDestino = new File(rutaBase + "pdf/libros/", pdfNombre);
            if (nuevoPdf != null && !nuevoPdf.isEmpty()) {
                if (pdfDestino.exists()) pdfDestino.delete();
                nuevoPdf.transferTo(pdfDestino);
                libro.setLibroPdf(pdfNombre);
            } else {
                libro.setLibroPdf(libro.getLibroPdf());
            }

            if (libro.getCategoria() == null || libro.getCategoria().getCategoriaCod() == null) {
                libro.setCategoria(libroExistente.getCategoria());
            }

            libroRepository.save(libro);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }

    @PostMapping("/guardarLibro")
    public String guardarLibro(
            @RequestParam("librotitulo") String titulo,
            @RequestParam("libroautor") String autor,
            @RequestParam("libroDescripcion") String descripcion,
            @RequestParam(name = "librocat", required = false) Integer librocat,
            @RequestParam("categoriaCod") int categoriaCod,
            @RequestParam("libroimagen") MultipartFile imagen,
            @RequestParam("libroPdf") MultipartFile pdf
    ) {
        try {
            if (imagen.isEmpty() || pdf.isEmpty()) {
                System.out.println("Archivo faltante");
                return "redirect:/admin?errorArchivo";
            }

            // Ruta base externa al proyecto
            String rutaBase = System.getProperty("user.dir") + "/subidas/";

            File carpetaImagenes = new File(rutaBase + "imagenes/libros/");
            File carpetaPdf = new File(rutaBase + "pdf/libros/");

            carpetaImagenes.mkdirs(); // Crea si no existen
            carpetaPdf.mkdirs();

// Normalizar nombre del archivo desde el título
            String nombreBase = titulo.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9_]", "");

// Construir nombres de archivo únicos
            String imagenNombre = nombreBase + ".jpg";
            String pdfNombre = nombreBase + ".pdf";

// Guardar archivos
            File imagenDestino = new File(carpetaImagenes, imagenNombre);
            imagen.transferTo(imagenDestino);

            File pdfDestino = new File(carpetaPdf, pdfNombre);
            pdf.transferTo(pdfDestino);

// Crear objeto libro
            Libro libro = new Libro();
            libro.setLibrotitulo(titulo);
            libro.setLibroautor(autor);
            libro.setLibroDescripcion(descripcion);
            libro.setLibrocat(librocat);
            libro.setLibroimagen(imagenNombre);
            libro.setLibroPdf(pdfNombre);
            libro.setCategoria(categoriaRepository.findById(categoriaCod).orElse(null));

            libroRepository.save(libro);

            System.out.println("Libro guardado: " + titulo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin";
    }

    @GetMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable("id") Integer id) {
        try {
            Libro libro = libroRepository.findById(id).orElse(null);
            if (libro != null) {
                String rutaBase = System.getProperty("user.dir") + "/subidas/";

                // Eliminar imagen si existe
                if (libro.getLibroimagen() != null) {
                    File imagenArchivo = new File(rutaBase + "imagenes/libros/" + libro.getLibroimagen());
                    if (imagenArchivo.exists()) {
                        imagenArchivo.delete();
                    }
                }

                // Eliminar PDF si existe
                if (libro.getLibroPdf() != null) {
                    File pdfArchivo = new File(rutaBase + "pdf/libros/" + libro.getLibroPdf());
                    if (pdfArchivo.exists()) {
                        pdfArchivo.delete();
                    }
                }

                // Eliminar de la base de datos
                libroRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Puedes loguear con un logger si deseas
        }

        return "redirect:/admin";
    }

}
