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
    
    public ClaseGym buscarClase(int id){
        String sql = "SELECT id_clse, diciplina, id_entrenador, horario, capacidad, estado FROM clasegym WHERE ID_Socio = ? AND Estado = 1";
        ClaseGym c = null;
        EntrenadorData ed = new EntrenadorData();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                c = new ClaseGym();
                c.setId(id);
                c.setDiciplina(rs.getString(2));
                c.setEntrenador(ed.buscarEntrenadorPorId(rs.getInt(3)));
                c.setHorario(rs.getString(4));
                c.setCapacidad(rs.getInt(5));
                c.setEstado(rs.getBoolean(6));
            } else {
                JOptionPane.showMessageDialog(null, "No existe una clase con ese ID.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la clase: " + ex.getMessage());
        }
        return c;
    }
    
    public void actualizarClase(ClaseGym c){
        String sql = "UPDATE clasegym SET id_entrenador = ?, diciplina = ?, horario = ?, capacidad = ?, estado = ? WHERE id_clase = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getEntrenador().getIdEntrenador());
            ps.setString(2, c.getDiciplina());
            ps.setString(3, c.getHorario());
            ps.setInt(4, c.getCapacidad());
            ps.setBoolean(5, c.isEstado());
            ps.setInt(7, c.getId());
            
            ps.executeUpdate(sql);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al actualizar la clase: " + ex.getMessage());
        }
    }
    
    public void borrarClase(int id){
        String sql = "DELETE FROM clasegym WHERE id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ps.executeUpdate(sql);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al actualizar la clase: " + ex.getMessage());
        }
    }
    
    public void darDeBajaClase(ClaseGym c){
        c.setEstado(false);
        actualizarClase(c);
    }
    
    public void darDeAltaClase(ClaseGym c){
        c.setEstado(true);
        actualizarClase(c);
    }
}
