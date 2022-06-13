
package nueva.libreria.proyecto.repositorios;

import nueva.libreria.proyecto.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, String>{
    
    
    
    
}
