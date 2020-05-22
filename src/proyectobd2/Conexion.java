package proyectobd2;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Tatto Monrroy
 */
public class Conexion {
       public Connection conexion;
    public Statement sentencia;
    public ResultSet resultado;
    public PreparedStatement ps;
//    private static Connection cnx = null;
//    
//public static Connection obtener() throws SQLException, ClassNotFoundException {
//      if (cnx == null) {
//         try {
//            Class.forName("com.mysql.jdbc.Driver");
//            cnx = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "");
//         } catch (SQLException ex) {
//            throw new SQLException(ex);
//         } catch (ClassNotFoundException ex) {
//            throw new ClassCastException(ex.getMessage());
//         }
//      }
//      return cnx;
//   }
//   public static void cerrar() throws SQLException {
//      if (cnx != null) {
//         cnx.close();
//      }
//   }
public void ConectarBasedeDatos(String bd){
 try {
 final String Controlador = "com.mysql.jdbc.Driver";
 Class.forName( Controlador );
 final String url_bd = "jdbc:mysql://1ocalhost:3306/";
 final String url_bd2 = "jdbc:mysql://localhost:3306/"+bd;
 if(bd.equals("")){
 conexion =  (Connection) DriverManager.getConnection(url_bd,"root","pass123");
 }else{
 conexion = (Connection) DriverManager.getConnection(url_bd2,"root","pass123");
 }
 sentencia = (Statement) conexion.createStatement();
 } catch (ClassNotFoundException | SQLException ex) {
 JOptionPane.showMessageDialog(null,ex.getMessage(), "Excepcion", JOptionPane.ERROR_MESSAGE);
 }
 }
 public void DesconectarBasedeDatos() {
 try {
 if (conexion != null ) {
 if(sentencia != null) {
 sentencia.close();
 }
 conexion.close();
 }
 }
 catch (SQLException ex) {
 JOptionPane.showMessageDialog(null,ex.getMessage(), "Excepcion", JOptionPane.ERROR_MESSAGE);
 System.exit(1);
 }
 }
 public Connection getConnection(){
 return conexion;
 }
 
public ArrayList<String> listar_bases(){
     ArrayList<String> lista = new ArrayList<String>();
     String q = "SHOW DATABASES";
      try{
        resultado = sentencia.executeQuery(q);
        System.out.println("Correcto");    
      }catch(SQLException ex){
          System.out.println("No Correcto"); 
      }
      try {
          while(resultado.next()){
              lista.add(resultado.getString("Database"));
          }
     } catch (Exception e) {
     }
      return lista;
    }
 
  public String insert(String Query) {
       String texto = "";
     try {
         sentencia = (Statement) conexion.createStatement();
          sentencia.executeUpdate(Query); 
//         while(resultado.next()){
//             texto = texto + resultado.getString("NOMBRE") + " | " + resultado.getString("PASS") + "\n";
//         }
            
     } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Error de la sentencia" + e.getMessage());
     }
     return texto;
 }
  
  public String selectfromquery(String Query) {
       String texto = "";
     try {
         sentencia = (Statement) conexion.createStatement();
         resultado = sentencia.executeQuery(Query); 
         ResultSetMetaData resultSetMetaData = resultado.getMetaData();
        final int columnCount = resultSetMetaData.getColumnCount();
         while(resultado.next()){
             
//         texto = texto + resultado.getString("NOMBRE") + " | " + resultado.getString("PASS") + "\n";   
           Object[] values = new Object[columnCount];
    for (int i = 1; i <= columnCount; i++) {
        values[i - 1] = resultado.getObject(i);
    }
    texto = texto + Arrays.toString(values) + "\n";
         }
     } catch (Exception e) {
     }
     return texto;
 }
}
