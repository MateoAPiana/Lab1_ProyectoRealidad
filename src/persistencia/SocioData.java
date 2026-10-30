package persistencia;

import modelo.Socio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class SocioData {

    private Connection con = null;

    public SocioData() {
        MiConexion conexion = new MiConexion();
        con = conexion.buscarConexion();
    }

    public void guardarSocio(Socio socio) {
        String sql = "INSERT INTO socio (DNI, Nombre, Apellido, Edad, Correo, Telefono, Estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, socio.getDni());
            ps.setString(2, socio.getNombre());
            ps.setString(3, socio.getApellido());
            ps.setInt(4, socio.getEdad());
            ps.setString(5, socio.getCorreo());
            ps.setString(6, socio.getTelefono());
            ps.setBoolean(7, socio.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                socio.setIdSocio(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "¡Socio añadido con éxito!");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Socios: " + ex.getMessage());
        }
    }

    public Socio buscarSocioPorId(int id) {
        String sql = "SELECT DNI, Nombre, Apellido, Edad, Correo, Telefono, Estado FROM socios WHERE ID_Socio = ? AND Estado = 1";
        Socio socio = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                socio = new Socio();
                socio.setIdSocio(id);
                socio.setDni(rs.getString("DNI"));
                socio.setNombre(rs.getString("Nombre"));
                socio.setApellido(rs.getString("Apellido"));
                socio.setEdad(rs.getInt("Edad"));
                socio.setCorreo(rs.getString("Correo"));
                socio.setTelefono(rs.getString("Telefono"));
                socio.setEstado(rs.getBoolean("Estado"));
            } else {
                JOptionPane.showMessageDialog(null, "No existe un socio con ese ID.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el socio: " + ex.getMessage());
        }
        return socio;
    }

    public void bajaLogicaSocio(int id) {
        String sql = "UPDATE socios SET Estado = 0 WHERE ID_Socio = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Socio dado de baja exitosamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al dar de baja al socio: " + ex.getMessage());
        }
    }

    public void modificarSocio(Socio socio) {
        String sql = "UPDATE socio SET dni = ?, apellido = ?, nombre = ?, edad = ?, correo = ?, telefono = ?, estado = ? WHERE id_socio = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, socio.getDni());
            ps.setString(2, socio.getApellido());
            ps.setString(3, socio.getNombre());
            ps.setInt(4, socio.getEdad());
            ps.setString(5, socio.getCorreo());
            ps.setString(6, socio.getTelefono());
            ps.setBoolean(7, socio.isEstado());
            ps.setInt(8, socio.getIdSocio()); 

            int filasCambiadas = ps.executeUpdate();

            if (filasCambiadas == 1) {
                JOptionPane.showMessageDialog(null, "¡Modificación exitosa!");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el socio a modificar.");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar el socio: " + ex.getMessage());
        }
    }

    public void eliminarSocio(int idSocio) {
        String sql = "DELETE FROM socio WHERE id_socio = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idSocio);

            int filasBorradas = ps.executeUpdate();
            if (filasBorradas == 1) {
                JOptionPane.showMessageDialog(null, "¡Socio eliminado correctamente!");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el socio a eliminar.");
            }

            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el socio: " + ex.getMessage());
        }
    }

    public Socio buscarSocioPorDni(int dni) {
        String sql = "SELECT id_socio, dni, apellido, nombre, edad, correo, telefono, estado FROM socio WHERE dni = ?";
        Socio socio = null;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                socio = new Socio();
                socio.setIdSocio(rs.getInt("id_socio"));
                socio.setDni(rs.getString("dni"));
                socio.setApellido(rs.getString("apellido"));
                socio.setNombre(rs.getString("nombre"));
                socio.setEdad(rs.getInt("edad"));
                socio.setCorreo(rs.getString("correo"));
                socio.setTelefono(rs.getString("telefono"));
                socio.setEstado(rs.getBoolean("estado"));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún socio con ese DNI.");
            }

            ps.close();
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el socio: " + ex.getMessage());
        }

        return socio;
    }
    public List<Socio> listarSocios() {
    List<Socio> socios = new ArrayList<>();
    
    // Usamos 'id_socio' y 'estado' tal cual figuran en phpMyAdmin
    String sql = "SELECT id_socio, nombre, apellido, dni, estado FROM socio WHERE estado = 1";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Socio socio = new Socio();
            
            // Mapeo correcto de las columnas de la base de datos
            socio.setIdSocio(rs.getInt("id_socio"));
            socio.setNombre(rs.getString("nombre"));
            socio.setApellido(rs.getString("apellido"));
            socio.setDni(rs.getString("dni")); // O getInt/getString según cómo tengas el DNI en tu clase
            socio.setEstado(rs.getBoolean("estado")); // O setEstado según tu clase Socio

            socios.add(socio);
        }

        ps.close();
        rs.close();

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Socio: " + ex.getMessage());
    }

    return socios;
}
}
