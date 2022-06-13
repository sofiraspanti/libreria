
package nueva.libreria.proyecto.repositorios;

import nueva.libreria.proyecto.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {
    
    
    
}
