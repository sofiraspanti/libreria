
package nueva.libreria.proyecto.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;
import nueva.libreria.proyecto.errores.ErrorServicio;
//import nueva.libreria.proyecto.servicios.AutorServicio;
import nueva.libreria.proyecto.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping ("/")
public class PortalControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    
    @GetMapping("/")
    public String inicio() {
        return "index";
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GetMapping("/libro")
    public String libro() {
        return "libro.html";
        
    }
    
    @GetMapping("/autor")
    public String autor() {
        return "autor.html";
     
    }
    
    
    
    
    
    @GetMapping("/editorial")
    public String editorial() {
        return "editorial.html";
        
    }
    
    @PostMapping("/guardarEditorial")
    public String guardar(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.crearEditorial(nombre);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        modelo.put("descripcion", "Editorial cargada correctamente.");
        return "editorial.html";
        
    }
    
}
