package nueva.libreria.proyecto.servicios;

import java.io.IOException;
import java.util.Optional;
import nueva.libreria.proyecto.entidades.Foto;
import nueva.libreria.proyecto.errores.ErrorServicio;
import nueva.libreria.proyecto.repositorios.FotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    public Foto guardarFoto(MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }
        return null;
    }
    
    public Foto actualizarFoto(String IDfoto, MultipartFile archivo)throws ErrorServicio{
        
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                
                if(IDfoto != null){
                    Optional<Foto> respuesta = fotoRepositorio.findById(IDfoto);
                    if(respuesta.isPresent())
                        foto = respuesta.get();
                } 
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }
        return null;
        
    }

}
