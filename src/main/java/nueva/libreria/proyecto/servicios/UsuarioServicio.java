package nueva.libreria.proyecto.servicios;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import nueva.libreria.proyecto.entidades.Usuario;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(Long dni, String nombre, String apellido, String telefono, String mail, String clave) throws ErrorServicio {
        validar(dni, nombre, apellido, telefono, mail, clave);

        Usuario usuario = new Usuario();
        usuario.setDocumento(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);
        usuario.setMail(mail);
        usuario.setClave(clave);
        usuario.setAlta(true);

        usuarioRepositorio.save(usuario);

    }

    @Transactional
    public void modificarUsuario(String idUsuario, Long dni, String nombre, String apellido, String telefono, String mail, String clave) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        validar(dni, nombre, apellido, telefono, mail, clave);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setDocumento(dni);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setTelefono(telefono);
            usuario.setMail(mail);
            usuario.setClave(clave);

            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontró el usuario.");
        }

    }

    @Transactional
    public void baja(String idUsuario) throws Exception {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(false);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario.");
        }
    }

    @Transactional
    public void alta(String idUsuario) throws Exception {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(true);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario.");
        }
    }

    @Transactional
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);

    }

    @Transactional
    public List<Usuario> listaUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public void validar(Long dni, String nombre, String apellido, String telefono, String mail, String clave) throws ErrorServicio {

        if (dni == null) {
            throw new ErrorServicio("El DNI no puede estar vacío.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacío.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacío.");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErrorServicio("El teléfono no puede estar vacío.");
        }
        if (mail == null || !mail.contains("@")) {
            throw new ErrorServicio("El mail no es válido o está vacío.");

        }
        if(clave == null || clave.length() < 8){
            throw new ErrorServicio ("La clave está vacía o tiene menos de 8 caracteres");
        }

    }
}
