package nueva.libreria.proyecto.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import nueva.libreria.proyecto.entidades.Autor;
import nueva.libreria.proyecto.entidades.Editorial;
import nueva.libreria.proyecto.entidades.Foto;
import nueva.libreria.proyecto.entidades.Libro;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.repositorios.AutorRepositorio;
import nueva.libreria.proyecto.repositorios.EditorialRepositorio;
import nueva.libreria.proyecto.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LibroServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio; //hacen la conexion con la bbdd
    @Autowired
    private FotoServicio fotoServicio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(String titulo, Integer anio, Integer ejemplares, String IDautor, String IDeditorial) throws ErrorServicio {

        Autor autor = autorRepositorio.findById(IDautor).get(); //busca el autor por id y lo agrega , para eso 1ro se llama al repositorii
        Editorial editorial = editorialRepositorio.findById(IDeditorial).get();

        validar(titulo, anio, ejemplares);

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional
    public void modificarLibro(String IDlibro, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio {
       
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        
        validar(titulo, anio, ejemplares);

        Optional<Libro> respuesta = libroRepositorio.findById(IDlibro); //busca en la bbss si existe un libro con ese id
        if (respuesta.isPresent()) {  //indica que ese id SI esta en la bbss
            Libro libro = respuesta.get();  //trae el libro encontrado
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro); //llamamos al repositor p/guardar los cambios

        } else {
            throw new ErrorServicio("No existe un libro con el ID indicado.");

        }

    }

    @Transactional
    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    @Transactional
    public Libro getOne(String id) {
        return libroRepositorio.getOne(id);
    }

    @Transactional
    public void baja(String idLibro) throws Exception {

        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontro el libro.");
        }
    }

    @Transactional
    public void alta(String idLibro) throws Exception {

        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontro el libro.");
        }
    }

    public void validar(String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El título no puede estar vacío.");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede estar vacío.");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("La cantidad de ejemplares no puede estar vacía.");
        }
    }
}
