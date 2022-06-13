package nueva.libreria.proyecto.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import nueva.libreria.proyecto.entidades.Autor;
import nueva.libreria.proyecto.entidades.Editorial;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarEditorial(String IDeditorial, String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(IDeditorial);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se ha encontrado la editorial según ID.");
        }

    }
    
    @Transactional
   public Editorial baja(String id) throws Exception {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            return editorial;
            //editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro la editorial.");
        }
    }

   @Transactional
    public Editorial alta(String id) throws Exception {

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            return editorial;
            //editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro la editorial.");
        }
    }

    @Transactional
    public List<Editorial> listarTodos() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public Editorial getOne(String id) {
        return editorialRepositorio.getOne(id);
    }

}
