package od.tienda_libros.vista;

import od.tienda_libros.modelo.Libro;
import od.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class LibroForm extends JFrame {

    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField idTexto;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton limpiarFormularioButton;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma();
        agregarButton.addActionListener(e -> agregarLibro());
        modificarButton.addActionListener(e -> modificarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });
        limpiarFormularioButton.addActionListener(e -> limpiarFormulario());
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

    private void agregarLibro(){
        //Leer los valores del formulario
        if (libroTexto.getText().equals("")) {
            mostrarMensaje("Proporciona el nombre del libro");
            libroTexto.requestFocusInWindow();
            return;
        }
        var nombreLibro = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());
        //Crear el objeto libro
        var libro = new Libro();
        libro.setNombreLibro(nombreLibro);
        libro.setAutor(autor);
        libro.setPrecio(precio);
        libro.setExistencias(existencias);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se agrego el libro...");
        limpiarFormulario();
        listarLibros();
    }
    private void modificarLibro(){
        if (idTexto.getText().equals("")) {
            mostrarMensaje("Selecciona el libro a modificar");
            libroTexto.requestFocusInWindow();
            return;
        }
        var idLibro = Integer.parseInt(idTexto.getText());
        var nombreLibro = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());
        //Crear el objeto libro
        var libro = new Libro();
        libro.setIdLibro(idLibro);
        libro.setNombreLibro(nombreLibro);
        libro.setAutor(autor);
        libro.setPrecio(precio);
        libro.setExistencias(existencias);
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se modificó el libro...");
        limpiarFormulario();
        listarLibros();
    }
    private void eliminarLibro() {
        if (idTexto.getText().equals("")) {
            mostrarMensaje("Selecciona el libro a eliminar");
            libroTexto.requestFocusInWindow();
            return;
        }
        var idLibro = Integer.parseInt(idTexto.getText());
        Libro libro = new Libro(idLibro, null, null, null, null);
        this.libroServicio.eliminarLibro(libro);
        mostrarMensaje("Se elimino el libro...");
        limpiarFormulario();
        listarLibros();
    }
    private void cargarLibroSeleccionado(){
        //Los indices de las columnas de nuestra tabla, inician en 0
        var renglon = tablaLibros.getSelectedRow();
        if(renglon != -1){
            //Regresa -1 si no se selecciono nningun registro
            this.agregarButton.setVisible(false);
            limpiarFormularioButton.setVisible(true);
            String idLibro = tablaLibros.getModel().getValueAt(renglon, 0).toString();
            idTexto.setText(idLibro);
            String nombreLibro = tablaLibros.getModel().getValueAt(renglon, 1).toString();
            libroTexto.setText(nombreLibro);
            String autor = tablaLibros.getModel().getValueAt(renglon, 2).toString();
            autorTexto.setText(autor);
            String precio = tablaLibros.getModel().getValueAt(renglon, 3).toString();
            precioTexto.setText(precio);
            String existencias = tablaLibros.getModel().getValueAt(renglon, 4).toString();
            existenciasTexto.setText(existencias);
        }
    }
    private void limpiarFormulario(){
        idTexto.setText("");
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
        this.agregarButton.setVisible(true);
    }
    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Creamos el elemento idTexto oculto
        idTexto = new JTextField("");
        idTexto.setVisible(false);
        //this.limpiarFormularioButton.setVisible(false);
        this.tablaModeloLibros = new DefaultTableModel(0, 5){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
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
