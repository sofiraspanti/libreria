package nueva.libreria.proyecto.controladores;

import java.util.List;
import nueva.libreria.proyecto.entidades.Usuario;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping
    public String inicioUsuario(){
        return "usuario.html";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario() {
        return "form-usuario";
    }
    
    @PostMapping("/nuevo")
    public String nuevoUsuario(ModelMap modelo, @RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String mail, @RequestParam String clave) throws ErrorServicio{
        
        try{
            usuarioServicio.crearUsuario(dni, nombre, apellido, telefono, mail, clave);
            modelo.put("exito", "El usuario se cargó correctamente.");
            return "form-usuario";
        } catch (ErrorServicio e){
            modelo.put("error", "No se pudo cargar el usuario");
            return "form-usuario";  
        }
 
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarUsuario(@PathVariable String id, ModelMap modelo ){
        modelo.put("usuario", usuarioServicio.getOne(id));
        return "modif-usuario";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificarUsuario(ModelMap modelo, @PathVariable String id,@RequestParam Long dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String mail, @RequestParam String clave){
        try{
            usuarioServicio.modificarUsuario(id, dni, nombre, apellido, telefono, mail, clave);
            modelo.put("exito","El usuario se modifcó correctamente.");
            return "usuario";
        }catch (ErrorServicio e){
            modelo.put("error", "Falta completar datos.");
        }
        return "lista-usuario";
    }
    
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Usuario> usuario = usuarioServicio.listaUsuarios();
        modelo.addAttribute("usuario", usuario);
        return "lista-usuario";
    }
    
    @GetMapping("/alta/{id}")
    public String altaUsuario(@PathVariable String id) throws Exception{
        try{
            usuarioServicio.alta(id);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "lista-usuario";
        }
        return "redirect:/usuario/lista";
    }
    
    @GetMapping("/baja/{id}")
    public String bajaUsuario(@PathVariable String id) throws Exception{
        try{
            usuarioServicio.baja(id);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return "lista-usuario";
        }
        return "redirect:/usuario/lista";
    }
    
    
    
    
    

}
