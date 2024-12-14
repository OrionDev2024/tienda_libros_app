package od.tienda_libros.vista;

import od.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class LibroForm extends JFrame {

    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma();
        agregarButton.addActionListener(e -> {

        });
        modificarButton.addActionListener(e -> {

        });
        eliminarButton.addActionListener(e -> {

        });
    }

    private void iniciarForma(){
        setContentPane(panel );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth() / 2);
        int y = (tamanioPantalla.height - getHeight() / 2);
        setLocation(x,y);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloLibros = new DefaultTableModel(0, 5);
        String[] cabeceros = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        tablaModeloLibros.setColumnIdentifiers(cabeceros);
        //instanciamos el objeto JTable
        this.tablaLibros = new JTable(tablaModeloLibros);
        listarLibros();
    }

    private  void listarLibros(){
        //Limpiar la tabla
        tablaModeloLibros.setRowCount(0);
        //Obtener los libros
        var libros = libroServicio.listarLibros();
        libros.forEach((libro) -> {
            Object[] renglonLibro = {
                    libro.getIdLibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            this.tablaModeloLibros.addRow(renglonLibro);
        });
    }
}
