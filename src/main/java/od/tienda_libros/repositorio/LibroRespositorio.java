package od.tienda_libros.repositorio;

import od.tienda_libros.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRespositorio extends JpaRepository<Libro, Integer> {

}
