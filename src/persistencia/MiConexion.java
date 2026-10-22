/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MiConexion{
    private String url;
    private String usuario; //
    private String password;
    
    private static Connection conexion = null;  
    
    public MiConexion(String url, String usr, String pass){     
        this.url= url;  
        usuario=usr;
        password=pass;
    }
    public Connection buscarConexion(){
        if (conexion==null) {  
            try {
                    Class.forName("com.mysql.jdbc.Driver");  // 1
                //  Class.forName("org.mariadb.jdbc.Driver");  // 1
                conexion = DriverManager.getConnection(url, usuario, password);  
            } catch (SQLException | ClassNotFoundException ex) {  
                System.out.println("No se puede conectar o no se puede cargar el driver. Error: "+ ex.getMessage());
            }
        }
        return conexion; 
    }
  }
