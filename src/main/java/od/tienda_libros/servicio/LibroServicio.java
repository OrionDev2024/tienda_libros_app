package od.tienda_libros.servicio;

import od.tienda_libros.modelo.Libro;
import od.tienda_libros.repositorio.LibroRespositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServicio implements ILibroServicio{

    @Autowired
    private LibroRespositorio libroRespositorio;
    @Override
    public List<Libro> listarLibros() {
        return libroRespositorio.findAll();
    }

    @Override
    public Libro buscarLibroPorId(Integer idLibro) {
        Libro libro = libroRespositorio.findById(idLibro).orElse(null);
        return libro;
    }

    @Override
    public void guardarLibro(Libro libro) {
        libroRespositorio.save(libro);
    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroRespositorio.delete(libro);
    }
}
