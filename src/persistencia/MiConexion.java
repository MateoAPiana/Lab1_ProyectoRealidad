package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MiConexion{
    
    private static Connection conexion = null;  
    
    public MiConexion(){  }
    
    public Connection buscarConexion(){
        if (conexion==null) {  
            try {
                    Class.forName("com.mysql.jdbc.Driver");  // 1
                //  Class.forName("org.mariadb.jdbc.Driver");  // 1
                conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/aborigym?useSSL=false&serverTimezone=UTC", "root", "");  
            } catch (SQLException | ClassNotFoundException ex) {  
                System.out.println("No se puede conectar o no se puede cargar el driver. Error: "+ ex.getMessage());
            }
        }
        return conexion;
    }
}
