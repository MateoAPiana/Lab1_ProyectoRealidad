package persistencia;

import modelo.Asistencia;
import modelo.Socio;
import modelo.ClaseGym;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class AsistenciaData {

    private Connection con = null;

    private SocioData socioData;

    private ClaseGymData clasegymData;

    public AsistenciaData() {
        MiConexion conexion = new MiConexion("jdbc:mysql://localhost:3306/aborigym?useSSL=false&serverTimezone=UTC", "root", "");
        con = conexion.buscarConexion();
        socioData = new SocioData();
        clasegymData = new ClaseGymData();
    }

    public void registrarAsistencia(Asistencia asistencia) {
        String sql = "INSERT INTO Asistencias (ID_Socio, ID_Clase, Fecha_Asistencia) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, asistencia.getSocio().getIdSocio());
            ps.setInt(2, asistencia.getClase().getId());
            ps.setDate(3, Date.valueOf(asistencia.getFechaAsistencia()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                asistencia.setIdAsistencia(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "¡Asistencia registrada con éxito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar la asistencia: " + ex.getMessage());
        }
    }

    public Asistencia buscarAsistenciaPorId(int id) {
        String sql = "SELECT ID_Socio, ID_Clase, Fecha_Asistencia FROM Asistencias WHERE ID_Asistencia = ?";
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

    public void actualizarAsistencia(Asistencia asistencia) {
        String sql = "UPDATE Asistencias SET ID_Socio = ?, ID_Clase = ?, Fecha_Asistencia = ? WHERE ID_Asistencia = ?";
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
        String sql = "DELETE FROM Asistencias WHERE ID_Asistencia = ?";
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
