package persistencia;

import modelo.Asistencia;
import modelo.Socio;
import modelo.ClaseGym;
import modelo.Pase;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AsistenciaData {

    private Connection con = null;

    private SocioData socioData;

    private ClaseGymData clasegymData;
    
    private PaseData paseData;

    public AsistenciaData() {
        MiConexion conexion = new MiConexion();
        con = conexion.buscarConexion();
        socioData = new SocioData();
        clasegymData = new ClaseGymData();
        paseData = new PaseData();
    }

    public boolean registrarAsistencia(Asistencia asistencia) {
        String sql = "INSERT INTO Asistencia (ID_Socio, ID_Clase, Fecha_Asistencia) VALUES (?, ?, ?)";
        try {
            
            Pase p = paseData.buscarPasePorSocio(asistencia.getSocio().getIdSocio());
            
            if(p == null){
                JOptionPane.showMessageDialog(null, "Este socio no posee un pase con cupos");
                return false;
            }
            
            boolean estaLlena = clasegymData.tieneEspacio(asistencia.getClase());
            
            if(estaLlena){
                JOptionPane.showMessageDialog(null, "Esta clase ya está llena");
                return false;
            }
            
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, asistencia.getSocio().getIdSocio());
            ps.setInt(2, asistencia.getClase().getId());
            ps.setDate(3, Date.valueOf(asistencia.getFechaAsistencia()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                asistencia.setIdAsistencia(rs.getInt(1));
                p.setCant_entradas(p.getCant_entradas() - 1);
                if(p.getCant_entradas() <= 0) paseData.darDeBajaPase(p.getId());
                paseData.actualizarPase(p);
                JOptionPane.showMessageDialog(null, "¡Asistencia registrada con éxito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar la asistencia: " + ex.getMessage());
        }
        return true;
    }

    public Asistencia buscarAsistenciaPorId(int id) {
        String sql = "SELECT ID_Socio, ID_Clase, Fecha_Asistencia FROM Asistencia WHERE ID_Asistencia = ?";
        Asistencia asistencia = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                asistencia = new Asistencia();
                asistencia.setIdAsistencia(id);
                int idSocio = rs.getInt("ID_Socio");
                int idClase = rs.getInt("ID_Clase");
                Socio socio = socioData.buscarSocioPorId(idSocio);
                ClaseGymData claseData = new ClaseGymData();
                ClaseGym clase = claseData.buscarClase(idClase);
                asistencia.setSocio(socio);
                asistencia.setClase(clase);
                Date sqlDate = rs.getDate("Fecha_Asistencia");
                if (sqlDate != null) {
                    asistencia.setFechaAsistencia(sqlDate.toLocalDate());
                }
            } else {
                JOptionPane.showMessageDialog(null, "No existe un registro de asistencia con ese ID.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la asistencia: " + ex.getMessage());
        }
        return asistencia;
    }
    
    public List<Asistencia> listarAsistencias() {
        String sql = "SELECT id_asistencia ,ID_Socio, ID_Clase, Fecha_Asistencia FROM Asistencia";
        List<Asistencia> asistencias = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(rs.getInt("id_asistencia"));
                int idSocio = rs.getInt("ID_Socio");
                int idClase = rs.getInt("ID_Clase");
                Socio socio = socioData.buscarSocioPorId(idSocio);
                ClaseGymData claseData = new ClaseGymData();
                ClaseGym clase = claseData.buscarClase(idClase);
                asistencia.setSocio(socio);
                asistencia.setClase(clase);
                Date sqlDate = rs.getDate("Fecha_Asistencia");
                if (sqlDate != null) {
                    asistencia.setFechaAsistencia(sqlDate.toLocalDate());
                }
                asistencias.add(asistencia);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar la asistencia: " + ex.getMessage());
        }
        return asistencias;
    }

    public void actualizarAsistencia(Asistencia asistencia) {
        String sql = "UPDATE Asistencia SET ID_Socio = ?, ID_Clase = ?, Fecha_Asistencia = ? WHERE ID_Asistencia = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, asistencia.getSocio().getIdSocio());
            ps.setInt(2, asistencia.getClase().getId());
            ps.setDate(3, Date.valueOf(asistencia.getFechaAsistencia()));
            ps.setInt(4, asistencia.getIdAsistencia());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "¡Asistencia actualizada con éxito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la asistencia: " + ex.getMessage());
        }
    }
    
    public void borrarAsistencia(int id) {
        String sql = "DELETE FROM Asistencia WHERE ID_Asistencia = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "¡Asistencia eliminada con éxito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al borrar la asistencia: " + ex.getMessage());
        }
    }
}
