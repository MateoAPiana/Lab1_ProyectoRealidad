package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import modelo.ClaseGym;

public class ClaseGymData {
    private Connection con = null;
    
    public void ClaseGymData(){
        MiConexion conexion = new MiConexion("jdbc:mysql://localhost:3308/aborigym?useSSL=false&serverTimezone=UTC", "root", "");
        con = conexion.buscarConexion();
    }
    
    public void guardarClase(ClaseGym c){
        String sql = "INSERT INTO clasegym (id_clase, diciplina, id_entrenador, horario, capacidad, estado) VALUES (NULL, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Socios: " + ex.getMessage());
        }
    }
}
