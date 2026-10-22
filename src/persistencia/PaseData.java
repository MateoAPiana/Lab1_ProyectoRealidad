package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
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
    
    public Pase buscarPase(int id) {
        String sql = "SELECT id_socio, cantidad_entradas, fecha_inicio, fecha_fin, costo, estado FROM pase WHERE id_pase = ? AND Estado = 1";
        Pase pase = null;
        SocioData sd = new SocioData();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                pase = new Pase();
                pase.setId(id);
                pase.setSocio(sd.buscarSocioPorId(rs.getInt(1)));
                pase.setCant_entradas(rs.getInt(2));
                String fecha_inicio = rs.getString(3);
                int iYear = Integer.parseInt(fecha_inicio.substring(0, 4));
                int iMounth = Integer.parseInt(fecha_inicio.substring(5, 7));
                int iDay = Integer.parseInt(fecha_inicio.substring(8));
                pase.setFecha_inicio(LocalDate.of(iYear, iMounth, iDay));
                String fecha_fin = rs.getString(4);
                int fYear = Integer.parseInt(fecha_fin.substring(0, 4));
                int fMounth = Integer.parseInt(fecha_fin.substring(5, 7));
                int fDay = Integer.parseInt(fecha_fin.substring(8));
                pase.setFecha_fin(LocalDate.of(fYear, fMounth, fDay));
                pase.setCosto(rs.getFloat(5));
                pase.setEstado(rs.getBoolean(6));
            } else {
                JOptionPane.showMessageDialog(null, "No existe un socio con ese ID.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el socio: " + ex.getMessage());
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error al buscar el socio: " + ex.getMessage());
        }
        return pase;
    }
    
    public void actualizarPase(Pase p){
        String sql = "UPDATE pase SET id_socio = ?, cantidad_entradas = ?, fecha_inicio = ?, fecha_fin = ?, costo = ?, estado=? WHERE id_pase = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, p.getSocio().getIdSocio());
            ps.setInt(2, p.getCant_entradas());
            ps.setString(3, p.getFecha_inicio().toString());
            ps.setString(4, p.getFecha_fin().toString());
            ps.setFloat(5, p.getCosto());
            ps.setBoolean(6, p.isEstado());
            ps.setInt(7, p.getId());
            
            ps.executeUpdate(sql);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al actualizar el pase: " + ex.getMessage());
        }
    }
    
    public void borrarPase(int id){
        String sql = "DELETE FROM pase WHERE id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ps.executeUpdate(sql);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al borrar el pase: " + ex.getMessage());
        }
    }
    
    public void darDeBajaPase(Pase c){
        c.setEstado(false);
        actualizarPase(c);
    }
    
    public void darDeAltaPase(Pase c){
        c.setEstado(true);
        actualizarPase(c);
    }
}
