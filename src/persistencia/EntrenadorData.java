package persistencia;

import modelo.Entrenador;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;

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
        String sql = "SELECT DNI, Nombre, Apellido, Especialidad, Estado FROM Entrenador WHERE ID_Entrenador = ?";
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
        String sql = "UPDATE Entrenador SET DNI = ?, Nombre = ?, Apellido = ?, Especialidad = ? WHERE ID_Entrenador = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, entrenador.getDni());
            ps.setString(2, entrenador.getNombre());
            ps.setString(3, entrenador.getApellido());
            ps.setString(4, entrenador.getEspecialidad());
            ps.setInt(5, entrenador.getIdEntrenador());

            int exito = ps.executeUpdate();
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Entrenador modificado exitosamente");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar el entrenador: " + ex.getMessage());
        }
    }

    public void bajaLogicaEntrenador(int id) {
        String sql = "UPDATE entrenador SET Estado = 0 WHERE ID_entrenador = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Entrenador dado de baja exitosamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al dar de baja al entrenador: " + ex.getMessage());
        }
    }
    
    public void altaLogicaEntrenador(int id) {
        String sql = "UPDATE entrenador SET Estado = 1 WHERE ID_entrenador = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Entrenador dado de alta exitosamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al dar de alta al entrenador: " + ex.getMessage());
        }
    }

    public void eliminarEntrenador(int id) {
        String sql = "UPDATE Entrenador SET Estado = 0 WHERE ID_Entrenador = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int exito = ps.executeUpdate();
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Entrenador dado de baja correctamente");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el entrenador: " + ex.getMessage());
        }
    }

    public List<Entrenador> listarEntrenador(boolean mostrar) {
        String sql = "SELECT id_entrenador, DNI, Nombre, Apellido, Especialidad, Estado FROM Entrenador WHERE estado = 1";
        if (mostrar) {
            sql = "SELECT id_entrenador, DNI, Nombre, Apellido, Especialidad, Estado FROM Entrenador";
        }
        List<Entrenador> list_entrenador = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Entrenador entrenador = null;
                entrenador = new Entrenador();
                entrenador.setIdEntrenador(rs.getInt("id_entrenador"));
                entrenador.setDni(rs.getString("DNI"));
                entrenador.setNombre(rs.getString("Nombre"));
                entrenador.setApellido(rs.getString("Apellido"));
                entrenador.setEspecialidad(rs.getString("Especialidad"));
                entrenador.setEstado(rs.getBoolean("Estado"));
                list_entrenador.add(entrenador);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el entrenador: " + ex.getMessage());
        }
        return list_entrenador;
    }
    
    public boolean tieneClasesPendientes(int id){
        String sql = "SELECT COUNT(*) FROM Entrenador e, clasegym c WHERE e.id_entrenador = c.id_entrenador AND e.id_entrenador = ? AND c.estado = 1;";
        boolean res = true;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                res = rs.getInt(1) > 0;
            } else {
                JOptionPane.showMessageDialog(null, "No existe un entrenador con ese ID.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el entrenador: " + ex.getMessage());
        }
        return res;
    }
}
