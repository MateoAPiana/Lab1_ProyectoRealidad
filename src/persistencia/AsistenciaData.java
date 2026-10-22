/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
}