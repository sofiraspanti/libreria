package nueva.libreria.proyecto.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nueva.libreria.proyecto.entidades.Autor;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/nuevo")
    public String formulario() {
        return "form-autor";
    }

    @PostMapping("/nuevo")
    public String nuevoAutor(ModelMap modelo, @RequestParam String nombre) {
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "Autor cargado correctamente.");
            return "form-autor";

        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "form-autor";
        }

        //return "autor";
    }

    @GetMapping("/modificar/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap modelo) {
        modelo.put("autor", autorServicio.getOne(id));
        return "modif-autor";
    }

    @PostMapping("/modificar/{id}")
    public String modificarAutor(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {
        try {
            autorServicio.modificarAutor(id, nombre);
            modelo.put("exito", "Modificado correctamente.");
            return "autor";

        } catch (ErrorServicio ex) {
            modelo.put("error", "El nombre no puede estar vacío.");
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "modif-autor";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Autor> autor = autorServicio.listarTodos();
        modelo.addAttribute("autor", autor);
        return "lista-autor";
    }

    @GetMapping("/baja/{id}")
    public String bajaAutor(@PathVariable String id) {
        try {
            autorServicio.baja(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "lista-autor";
        }
        return "redirect:/autor/lista";
    }
    
    @GetMapping("/alta/{id}")
    public String altaAutor(@PathVariable String id) {
        try {
            autorServicio.alta(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "lista-autor";
        }
        return "redirect:/autor/lista";
    }

}
