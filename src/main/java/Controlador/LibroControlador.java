/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author Sarita
 */
public class LibroControlador {
    
    private Libro libro;
   Conexion parametros = new Conexion();
    Connection conectar = (Connection) parametros.conectar();
    PreparedStatement ejecutar;
    ResultSet res;

    public Connection getConectar() {
        return conectar;
    }

    public void setConectar(Connection conectar) {
        this.conectar = conectar;
    }

    

   public ArrayList<Object[]> datosLibrosOrdenadosPorTitulo() {
        ArrayList<Object[]> listaObject = new ArrayList<>();

        try {
            String sql = "CALL OrdenarLibrosPorTitulo();";
            ejecutar = conectar.prepareCall(sql);
            res = ejecutar.executeQuery();
            int cont = 1;
            while (res.next()) {
                Object[] obLibro = new Object[10];
                obLibro[0] = cont;
                obLibro[1] = res.getObject("titulo");
                obLibro[2] = res.getObject("autor_nombre");
                obLibro[3] = res.getObject("autor_apellido");
                obLibro[4] = res.getObject("ISBN");
                obLibro[5] = res.getObject("paginas");
                obLibro[6] = res.getObject("edicion");
                obLibro[7] = res.getObject("editorial");
                obLibro[8] = res.getObject("ciudad");
                obLibro[9] = res.getObject("pais");

                listaObject.add(obLibro);
                cont++;
            }
            ejecutar.close();
        } catch (SQLException e) {
            System.out.println("ERROR SQL al cargar libros ordenados por título: " + e.getMessage());
        }
        return listaObject;
    }

    public void agregarLibro(Libro libro) {
    try {
        String sql = "CALL InsertarLibro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        ejecutar = conectar.prepareStatement(sql);
        ejecutar.setString(1, libro.getTitulo());
        ejecutar.setString(2, libro.getAutor().getNombre());
        ejecutar.setString(3, libro.getAutor().getApellido());
        ejecutar.setString(4, libro.getISBN());
        ejecutar.setInt(5, libro.getPaginas());
        ejecutar.setInt(6, libro.getEdicion());
        ejecutar.setString(7, libro.getEditorial());
        ejecutar.setString(8, libro.getCiudad());
        ejecutar.setString(9, libro.getPais());
        ejecutar.setDate(10, new java.sql.Date(libro.getFechaEdicion().getTime()));

        ejecutar.executeUpdate();
        ejecutar.close();
        JOptionPane.showMessageDialog(null, "Libro creado con éxito");                
    } catch (SQLException e) {
        System.out.println("Error al agregar libro: " + e.getMessage());
    }
}
    
}

////////////////////////////////////////////////////////////////////////////////////////////////

  

