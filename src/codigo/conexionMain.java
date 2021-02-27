/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo Martin
 */
public class conexionMain {
   

 Statement sta;   
Connection conexion = null;

    public boolean GestorConexion() { //cambio el constructor por un método que retorna un int para poder conectar con la base de datos mediante un boton

        //abre la conexion entre la aplicacion y la base de datos
        try {
            String url = "jdbc:oracle:thin:@localhost:1521/Videoclub";
            String user = "root";
            String password = "root";
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection(url, user, password);
            if (conexion != null) {
                System.out.println("Conectado a la BBDD");
                return true;
            } else {
                System.out.println("No se ha podido conectar a la Base de Datos");
                return false;
            }
         
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
            // Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    public int cerrarConexion() {
        try {
            conexion.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    public void deleteFilm(String nombrePelicula) {
        try {
            sta = conexion.createStatement();
            sta.executeUpdate("Delete from peliculas where titulo='" + nombrePelicula + "'");
            sta.close();
        } catch (Exception e) {
            System.err.println("No se pudo borrar la pelicula");
        }
    }
 public void insertarPelicula(String id,String titulo, String genero, int director, int actor) {
        //inserta un album nuevo a la tabla album
        try {
            sta = conexion.createStatement();
            sta.executeUpdate("INSERT INTO peliculas SELECT '"+ id + "','" + titulo + "' , '" + genero + "' , REF(d) , REF(a)"
                    + " from directores d, actores a"
                    + " WHERE d.id_director='" + director + "'"+"AND a.id_actores = '" + actor + "'");
            sta.close();
//            System.out.println("INSERT INTO peliculas VALUE ( '"+ id + "','" + titulo + "' , '" + genero + "' ,(SELECT REF(d) from directores d WHERE d.nombre='" + director + "'),(SELECT REF(a) from actores a WHERE a.nombre='" + actor + "')");
            //System.out.println("INSERT INTO peliculas VALUE ('" + titulo + "' , '" + genero + "' , '" + release + "');");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
    public ArrayList<String> consulta_peliculas() {
         // este metodo lo usaremos para que se muestre todas las canciones en 
        //un comboBox, para ellos usaremos una arrayList y una query para mostrar 
        //todas las canciones
        ArrayList<String> lista = new ArrayList<String>();
        try {
            sta = conexion.createStatement();
            String query = "SELECT p.titulo from peliculas p";
            ResultSet rs = sta.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                lista.add((rs.getString("titulo")));
            }
//            System.out.println(query);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
//             System.out.println(pelis);
        }
        return lista;
    }
    
     public ArrayList<String> consulta_actores() {
         // este metodo lo usaremos para que se muestre todas las canciones en 
        //un comboBox, para ellos usaremos una arrayList y una query para mostrar 
        //todas las canciones
        ArrayList<String> listaActores = new ArrayList<String>();
        try {
            sta = conexion.createStatement();
            String query = "SELECT a.nombre from actores a";
            ResultSet rs = sta.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                listaActores.add((rs.getString("nombre")));
            }
//            System.out.println(query);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
//             System.out.println(pelis);
        }
        return listaActores;
    }
     public ArrayList<String> consulta_directores() {
         // este metodo lo usaremos para que se muestre todas las canciones en 
        //un comboBox, para ellos usaremos una arrayList y una query para mostrar 
        //todas las canciones
        ArrayList<String> listaDirectores = new ArrayList<String>();
        try {
            sta = conexion.createStatement();
            String query = "SELECT nombre from directores";
            ResultSet rs = sta.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                listaDirectores.add((rs.getString("nombre")));
            }
//            System.out.println(query);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
//             System.out.println(pelis);
        }
        return listaDirectores;
    }
       
     
       public void actualizaPelis(String nameFilm, String newFilm,String newGenero) {
      //este metodo sirve para modificar una cancion, basicamente se declaran los strings necesarios y se realiza la 
      //query adecuada para realizar los cambios en la cancion
        String query = "UPDATE peliculas SET ";
        try {
            sta = conexion.createStatement();
            query += "titulo = '" + newFilm + "'" + ','+ "genero='" + newGenero +"' WHERE titulo ='" + nameFilm + "'";
                    
            System.out.println(query);
            sta.executeUpdate(query);
            sta.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());      
        }
    }


 public void deleteActor(String nombreActor) {
        try {
            sta = conexion.createStatement();
            sta.executeUpdate("Delete from actores where nombre='" + nombreActor + "'");
            sta.close();
        } catch (Exception e) {
            System.err.println("No se pudo borrar la pelicula");
        }
    }
  public void deleteDirector(String nombreDirector) {
        try {
            sta = conexion.createStatement();
            sta.executeUpdate("Delete from directores where nombre='" + nombreDirector + "'");
            sta.close();
        } catch (Exception e) {
            System.err.println("No se pudo borrar la pelicula");
        }
    }

///////////////////////////////////////////////////////////////////////////////////
  
  
  
  
public void insertarDirector(String id,String nombre, String apellido1, String apellido2, String l_nacimiento ) {
        //inserta un album nuevo a la tabla album
        try {
            sta = conexion.createStatement();
            sta.executeUpdate( "INSERT INTO directores VALUES('"+ id + "','"+ nombre + "','" + apellido1 + "','" + apellido2 + "','" + l_nacimiento + "')");
            sta.close();
            //System.out.println("INSERT INTO peliculas VALUE ('" + titulo + "' , '" + genero + "' , '" + release + "');");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
    public void insertarActor(String id,String nombre, String apellido1, String apellido2, String l_nacimiento ) {
        //inserta un album nuevo a la tabla album
        try {
            sta = conexion.createStatement();
            sta.executeUpdate( "INSERT INTO actores VALUES('"+ id + "','"+ nombre + "','" + apellido1 + "','" + apellido2 + "','" + l_nacimiento + "')");
            sta.close();
            //System.out.println("INSERT INTO peliculas VALUE ('" + titulo + "' , '" + genero + "' , '" + release + "');");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

      public void actualizaDirectores(String nameDirector, String _newDirector,String newApellido1,String newApellido2,String newCiudad) {
      //este metodo sirve para modificar una cancion, basicamente se declaran los strings necesarios y se realiza la 
      //query adecuada para realizar los cambios en la cancion
        String query = "UPDATE directores SET";
        try {
            sta = conexion.createStatement();
            query += " nombre = '" + _newDirector + "'" + ','+ "Apellido1='" + newApellido1 + "'" + ','+ "Apellido2='" + newApellido2 + "'"   + ',' + "Ciudad='" + newCiudad + "'WHERE nombre like '" + nameDirector + "'";
            System.out.println(query);
            sta.executeUpdate(query);
            sta.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());      
        }
    }
  public void actualizaActores(String nameActor, String _newActor,String newApellido1,String newApellido2,String newCiudad) {
      //este metodo sirve para modificar una cancion, basicamente se declaran los strings necesarios y se realiza la 
      //query adecuada para realizar los cambios en la cancion
        String query = "UPDATE actores SET";
        try {
            sta = conexion.createStatement();
            query += " nombre = '" + _newActor + "'" + ','+ "Apellido1='" + newApellido1 + "'" + ','+ "Apellido2='" + newApellido2 + "'" + ',' + "Ciudad='" + newCiudad + "'WHERE nombre like '" + nameActor + "'";
            System.out.println(query);
            sta.executeUpdate(query);
            sta.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());      
        }
    }

 public String muestraPelis(String _titulo) {
        //Este metodo lo usaremos para mostrar las canciones, declaramos un string vacio
        //se realiza la query para mostrar todos los elementos de los albumes y 
        //se le suma al string vacio los resultados de la query 
        String peliculas = ""; 
       
        String query = "select p.*from peliculas p WHERE titulo='" + _titulo + "'"; 
        
        try {
            sta = conexion.createStatement();
           
            ResultSet rs = sta.executeQuery(query);
                 
            int i = 0;
            while (rs.next()) {
                peliculas += ("titulo: " + rs.getString("titulo") + "\n" + "Genero: " + rs.getString("genero") + "\n" + "Director: " + rs.getString("director") + "\n"+ "Actor principal: " + rs.getString("actor") + "\n");
                peliculas += "---------------------------------------------\n";
//                  System.out.println(query);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage().toString());
            System.err.println("No se encontraron los albumes");
        }
        return peliculas;
    }
  
  public String consulta_actores(String nombre){
      String query = "SELECT * FROM actores WHERE nombre = ?";
      String actores = "";
      try{
          PreparedStatement pst = conexion.prepareStatement(query);
          pst.setString(1, nombre);
          
          ResultSet rs = pst.executeQuery();
          
          while(rs.next()){
               actores += "nombre: " + rs.getString("nombre") + "\n" + "Apellido1: " + rs.getString("apellido1") + "\n" + "Apellido2: " + rs.getString("apellido2") + "\n"+ "ciudad: " + rs.getString("ciudad") + "\n";
                actores += "---------------------------------------------\n";
          }
          rs.close();
          pst.close();
      }
      catch(Exception e){
          System.out.println("Error: " + e.toString());
      }
        return actores;
  }
   public String consulta_directores(String nombre){
      String query = "SELECT * FROM directores WHERE nombre = ?";
      String director = "";
      try{
          PreparedStatement pst = conexion.prepareStatement(query);
          pst.setString(1, nombre);
          
          ResultSet rs = pst.executeQuery();
          
          while(rs.next()){
               director += "nombre: " + rs.getString("nombre") + "\n" + "Apellido1: " + rs.getString("apellido1") + "\n" + "Apellido2: " + rs.getString("apellido2") + "\n"+ "ciudad: " + rs.getString("ciudad") + "\n";
                director += "---------------------------------------------\n";
          }
          rs.close();
          pst.close();
      }
      catch(Exception e){
          System.out.println("Error: " + e.toString());
      }
        return director;
  }

}
