
package nueva.libreria.proyecto.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nueva.libreria.proyecto.entidades.Editorial;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
   
    
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/nuevo")
    public String formulario() {
        return "form-editorial";
    }

    @PostMapping("/nuevo")
    public String nuevaEditorial(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "Editorial cargada correctamente.");
            return "form-editorial";
            
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "form-editorial";
        }
        
        //return "autor";
    }

    @GetMapping("/modificar/{id}")
    public String modificarEditorial(@PathVariable String id, ModelMap modelo) {
        modelo.put("editorial",editorialServicio.getOne(id));
        return "modif-editorial";
    }

    @PostMapping("/modificar/{id}")
    public String modificarEditorial(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) {
        try {
            editorialServicio.modificarEditorial(id, nombre);
            modelo.put("exito", "Modificado correctamente.");
            return "editorial";

        } catch (ErrorServicio ex) {
            modelo.put("error", "El nombre no puede estar vacío.");
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "modif-editorial";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Editorial> editorial = editorialServicio.listarTodos();
        modelo.addAttribute("editorial", editorial);
        return "lista-editorial";
    }

    @GetMapping("/baja/{id}")
    public String bajaEditorial(@PathVariable String id) {
        try {
            editorialServicio.baja(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "lista-editorial";
        }
        return "redirect:/editorial/lista";
    }
    
    @GetMapping("/alta/{id}")
    public String altaEditorial(@PathVariable String id) {
        try {
            editorialServicio.alta(id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "lista-editorial";
        }
        return "redirect:/editorial/lista";
    }
}
    

