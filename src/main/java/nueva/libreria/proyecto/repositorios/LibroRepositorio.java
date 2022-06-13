
package nueva.libreria.proyecto.repositorios;

import nueva.libreria.proyecto.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    
    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarLibroID(@Param("id") String id);
    
}