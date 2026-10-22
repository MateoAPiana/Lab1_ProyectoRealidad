package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import modelo.Socio;
import modelo.Pase;

public class PaseData {
    private Connection con = null;
    
    public void PaseData(){
        MiConexion conexion = new MiConexion("jdbc:mysql://localhost:3306/aborigym?useSSL=false&serverTimezone=UTC", "root", "");
        con = conexion.buscarConexion();
    }
    
    public void guardarPase(Pase pase) {
        String sql = "INSERT INTO pase (id_socio, cantidad_entradas, fecha_inicio, fecha_fin, costo, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, pase.getSocio().getIdSocio());
            ps.setInt(2, pase.getCant_entradas());
            ps.setString(3, pase.getFecha_inicio().toString());
            ps.setString(4, pase.getFecha_fin().toString());
            ps.setFloat(5, pase.getCosto());
            ps.setBoolean(6, pase.isEstado());
            
            ps.executeUpdate(sql);
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                pase.setId(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "¡Pase añadido con éxito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Pase: " + ex.getMessage());
        }
    }
}
