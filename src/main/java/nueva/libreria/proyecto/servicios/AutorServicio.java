package nueva.libreria.proyecto.servicios;

import java.util.List;
import java.util.Optional;
import nueva.libreria.proyecto.entidades.Autor;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void crearAutor(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);

        autorRepositorio.save(autor);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void modificarAutor(String idAutor, String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }

        Optional<Autor> respuesta = autorRepositorio.findById(idAutor);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encuentra autor según ID.");
        }
    }

    @Transactional
    public Autor baja(String id) throws Exception {

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            return autor;
            //return autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor.");
        }
    }

    @Transactional
    public Autor alta(String id) throws Exception {

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);
            return autor;
            //autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor.");
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Autor getOne(String id) {
        return autorRepositorio.getOne(id);
    }

}
