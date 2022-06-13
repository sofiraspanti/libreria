package nueva.libreria.proyecto.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nueva.libreria.proyecto.entidades.Autor;
import nueva.libreria.proyecto.entidades.Editorial;

import nueva.libreria.proyecto.entidades.Libro;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.servicios.AutorServicio;
import nueva.libreria.proyecto.servicios.EditorialServicio;
import nueva.libreria.proyecto.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/nuevo")
    public String formulario(ModelMap modelo) {
        try {
            List<Autor> autores = autorServicio.listarTodos();
            modelo.addAttribute("autores", autores);

            List<Editorial> editoriales = editorialServicio.listarTodos();
            modelo.addAttribute("editoriales", editoriales);

            modelo.addAttribute("libro", new Libro());
            return "form-libro";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "form-libro";

        }

    }

    @PostMapping("/nuevo")
    public String nuevoLibro(ModelMap modelo, @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares,
            @RequestParam(required = false) String idAutor, @RequestParam(required = false) String idEditorial,
            RedirectAttributes attr) {
        try {
            libroServicio.crearLibro(titulo, anio, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "Libro cargado correctamente.");
            return "form-libro";

        } catch (ErrorServicio ex) {
            modelo.put("error", "Falta ingresar datos.");
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.addAttribute("autores", autorServicio.listarTodos());
            modelo.addAttribute("editoriales", editorialServicio.listarTodos());
            return "form-libro";
        }

    }

    @GetMapping("/modificar/{id}")
    public String modificarLibro(@PathVariable String id, ModelMap modelo) {
        List<Autor> autores = autorServicio.listarTodos();
        modelo.addAttribute("autores", autores);

        List<Editorial> editoriales = editorialServicio.listarTodos();
        modelo.addAttribute("editoriales", editoriales);
        modelo.put("libro", libroServicio.getOne(id));
        return "modif-libro";
    }

    @PostMapping("/modificar/{id}")
    public String modificarLibro(ModelMap modelo, @PathVariable String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String idAutor, @RequestParam String idEditorial) {
        try {
            libroServicio.modificarLibro(id, titulo, anio, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "Modificado correctamente.");
            return "libro";

        } catch (ErrorServicio ex) {
            modelo.put("error", "Los campos no pueden estar vacios.");
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "modif-libro";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarTodos();
        modelo.addAttribute("libros", libros);
        return "lista-libro";
    }

    @GetMapping("/baja/{id}")
    public String bajaLibro(@PathVariable String id) {
        try {
            libroServicio.baja(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "lista-libro";
        }
        return "redirect:/libro/lista";
    }

    @GetMapping("/alta/{id}")
    public String altaLibro(@PathVariable String id) {
        try {
            libroServicio.alta(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "lista-libro";
        }
        return "redirect:/libro/lista";
    }

}
