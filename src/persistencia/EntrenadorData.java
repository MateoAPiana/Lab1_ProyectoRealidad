package persistencia;

import modelo.Entrenador;
import java.sql.*;
import javax.swing.JOptionPane;

public class EntrenadorData {

    private Connection con = null;

    public EntrenadorData() {
        MiConexion conexion = new MiConexion();
        con = conexion.buscarConexion();
    }

    public void guardarEntrenador(Entrenador entrenador) {
        String sql = "INSERT INTO Entrenador (DNI, Nombre, Apellido, Especialidad, Estado) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entrenador.getDni());
            ps.setString(2, entrenador.getNombre());
            ps.setString(3, entrenador.getApellido());
            ps.setString(4, entrenador.getEspecialidad());
            ps.setBoolean(5, entrenador.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entrenador.setIdEntrenador(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "¡Entrenador añadido con éxito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Entrenador: " + ex.getMessage());
        }
    }

    public Entrenador buscarEntrenadorPorId(int id) {
        String sql = "SELECT DNI, Nombre, Apellido, Especialidad, Estado FROM Entrenador WHERE ID_Entrenador = ? AND Estado = 1";
        Entrenador entrenador = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entrenador = new Entrenador();
                entrenador.setIdEntrenador(id);
                entrenador.setDni(rs.getString("DNI"));
                entrenador.setNombre(rs.getString("Nombre"));
                entrenador.setApellido(rs.getString("Apellido"));
                entrenador.setEspecialidad(rs.getString("Especialidad"));
                entrenador.setEstado(rs.getBoolean("Estado"));
            } else {
                JOptionPane.showMessageDialog(null, "No existe un entrenador con ese ID.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el entrenador: " + ex.getMessage());
        }
        return entrenador;
    }
    public void modificarEntrenador(Entrenador entrenador) {
    String sql = "UPDATE Entrenador SET DNI = ?, Nombre = ?, Apellido = ?, Especialidad = ?, Estado = ? WHERE ID_Entrenador = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, entrenador.getDni());
        ps.setString(2, entrenador.getNombre());
        ps.setString(3, entrenador.getApellido());
        ps.setString(4, entrenador.getEspecialidad());
        ps.setBoolean(5, entrenador.isEstado());
        ps.setInt(6, entrenador.getIdEntrenador());
        
        int exito = ps.executeUpdate();
        if (exito == 1) {
            JOptionPane.showMessageDialog(null, "¡Entrenador modificado exitosamente!");
        }
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al modificar el entrenador: " + ex.getMessage());
    }
}

public void eliminarEntrenador(int id) {
    String sql = "UPDATE Entrenador SET Estado = 0 WHERE ID_Entrenador = ?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        
        int exito = ps.executeUpdate();
        if (exito == 1) {
            JOptionPane.showMessageDialog(null, "¡Entrenador dado de baja correctamente!");
        }
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al eliminar el entrenador: " + ex.getMessage());
    }
}
}
