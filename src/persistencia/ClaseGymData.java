package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            ps.setString(1, c.getDiciplina());
            ps.setInt(2, c.getEntrenador().getIdEntrenador());
            ps.setString(3, c.getHorario());
            ps.setInt(4, c.getCapacidad());
            ps.setBoolean(5, c.isEstado());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "¡Clase añadida con éxito!");
            }
            ps.close();
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Socios: " + ex.getMessage());
        }
    }
}
